package edu.ntnu.idatt2003.group16.view.components;

import edu.ntnu.idatt2003.group16.controller.GameController;
import edu.ntnu.idatt2003.group16.model.GameSession;
import edu.ntnu.idatt2003.group16.model.investment.Share;
import edu.ntnu.idatt2003.group16.observer.GameObserver;
import edu.ntnu.idatt2003.group16.view.SellDialog;
import edu.ntnu.idatt2003.group16.view.StockDialog;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PortfolioShares extends VBox implements GameObserver {
  private GameSession gameSession;
  private GameController gameController;

  private TableView<Share> sharesTable;
  private VBox sharesBox;

  private Boolean ascending = true;
  private Comparator<Share> currentSort =
      Comparator.comparing(share -> share.getStock().getSymbol());

  public PortfolioShares(GameSession gameSession, GameController gameController) {
    this.gameSession = gameSession;
    this.gameController = gameController;

    Button symbolShareHeaderButton = new Button("Symbol");
    symbolShareHeaderButton.setOnAction(event -> {
      currentSort = Comparator.comparing(share -> share.getStock().getSymbol());
      ascending = !ascending;
      updateShares();
    });

    Button companyShareHeaderButton = new Button("Company Name");
    companyShareHeaderButton.setOnAction(event -> {
      currentSort = Comparator.comparing(share -> share.getStock().getCompany());
      ascending = !ascending;
      updateShares();
    });

    Button quantityShareHeaderButton = new Button("Quantity");
    quantityShareHeaderButton.setOnAction(event -> {
      currentSort = Comparator.comparing(Share::getQuantity);
      ascending = !ascending;
      updateShares();
    });

    Button purchasePriceShareHeaderButton = new Button("Purchase Price");
    purchasePriceShareHeaderButton.setOnAction(event -> {
      currentSort = Comparator.comparing(Share::getPurchasePrice);
      ascending = !ascending;
      updateShares();
    });

    Button currentPriceShareHeaderButton = new Button("Current Price");
    currentPriceShareHeaderButton.setOnAction(event -> {
      currentSort = Comparator.comparing(share -> share.getStock().getCurrentPrice());
      ascending = !ascending;
      updateShares();
    });

    Button changeInPriceShareHeaderButton = new Button("Change in Price");
    changeInPriceShareHeaderButton.setOnAction(event -> {
      currentSort = Comparator.comparing(
          share -> share.getStock().getCurrentPrice().subtract(share.getPurchasePrice())
      );
      ascending = !ascending;
      updateShares();
    });

    Button totalValueButton = new Button("Total Value");
    ;
    totalValueButton.setOnAction(event -> {
      currentSort = Comparator.comparing(
          share -> share.getStock().getCurrentPrice().multiply(share.getQuantity())
      );
      ascending = !ascending;
      updateShares();
    });

    Button totalReturnValueButton = new Button("Total Return Value");
    totalReturnValueButton.setOnAction(event -> {
      currentSort = Comparator.comparing(share ->
          share.getStock().getCurrentPrice()
              .subtract(share.getPurchasePrice())
              .multiply(share.getQuantity())
      );
      ascending = !ascending;
      updateShares();
    });

    sharesTable = new TableView<Share>();

    TableColumn<Share, String> symbolColumn = new TableColumn<>("Symbol");
    symbolColumn.setCellValueFactory(data ->
        new SimpleStringProperty(data.getValue().getStock().getSymbol()));

    sharesTable.getColumns().add(symbolColumn);

    this.getChildren().add(sharesTable);

    updateShares();

    /* sharesTable.add(symbolShareHeaderButton, 0, 0);
    sharesTable.add(companyShareHeaderButton, 1, 0);
    sharesTable.add(quantityShareHeaderButton, 2, 0);
    sharesTable.add(purchasePriceShareHeaderButton, 3, 0);
    sharesTable.add(currentPriceShareHeaderButton, 4, 0);
    sharesTable.add(changeInPriceShareHeaderButton, 5, 0);
    sharesTable.add(totalValueButton, 6, 0);
    sharesTable.add(totalReturnValueButton, 7, 0); */
  }

  private void updateShares() {
    // sharesBox.getChildren().clear();
    sharesTable.getItems().clear();
    List<Share> shares = new ArrayList<>(
        gameSession.getPlayer().getPortfolio().getShares()
    );

    if (currentSort != null) {
      shares.sort(ascending ? currentSort : currentSort.reversed());
    }

    sharesTable.getItems().addAll(shares);

    /*
    for (Share share : shares) {
      HBox shareBox = new HBox(10);

      BigDecimal quantity = share.getQuantity();
      BigDecimal purchasePrice = share.getPurchasePrice();
      BigDecimal currentPrice = share.getStock().getCurrentPrice();

      Label symbolLabel = new Label(share.getStock().getSymbol());
      Label companyLabel = new Label(share.getStock().getCompany());
      Label quantityLabel = new Label(quantity + " Share(s)");
      Label purchasePriceLabel = new Label(purchasePrice.toString());
      Label currentPriceLabel = new Label(currentPrice.toString());
      Label changeInPriceLabel = new Label(currentPrice.subtract(purchasePrice).toString());
      Label totalValueLabel = new Label(currentPrice.multiply(quantity).toString());
      Label totalReturnValueLabel = new Label(currentPrice.multiply(quantity).subtract(purchasePrice.multiply(quantity)).toString());



      Button sellButton = new Button("Sell");

      sellButton.setOnAction(event -> {
        SellDialog sellDialog = new SellDialog(gameController, share);
        sellDialog.showAndGetResult();
      });

      Button historicPrices = new Button("Historic Prices");
      historicPrices.setOnAction(event -> {
        StockDialog stockDialog = new StockDialog(share.getStock(), gameSession.getExchange());
        stockDialog.showAndGetResult();
      });

      shareBox.getChildren().addAll(
          sellButton,
          symbolLabel,
          companyLabel,
          quantityLabel,
          purchasePriceLabel,
          currentPriceLabel,
          changeInPriceLabel,
          totalValueLabel,
          totalReturnValueLabel,
          historicPrices
      );

      sharesBox.getChildren().add(shareBox);
    }

     */
  }

  @Override
  public void onGameStateChanged() {
    updateShares();
  }
}
