package edu.ntnu.idatt2003.group16.view.components;

import edu.ntnu.idatt2003.group16.controller.GameController;
import edu.ntnu.idatt2003.group16.model.GameSession;
import edu.ntnu.idatt2003.group16.model.investment.Share;
import edu.ntnu.idatt2003.group16.observer.GameObserver;
import edu.ntnu.idatt2003.group16.view.HistoricalPricesDialog;
import edu.ntnu.idatt2003.group16.view.SellDialog;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PortfolioShares extends VBox implements GameObserver {
  private GameSession gameSession;
  private GameController gameController;

  private final GridPane sharesHeader;
  private final VBox sharesBox;

  private Boolean ascending = true;
  private Comparator<Share> currentSort =
      Comparator.comparing(share -> share.getStock().getSymbol());

  public PortfolioShares(GameSession gameSession, GameController gameController) {
    this.gameSession = gameSession;
    this.gameController = gameController;

    this.gameSession.addObserver(this);

    this.sharesHeader = new GridPane();
    this.sharesHeader.getStyleClass().add("share-table-header");
    for(int i = 0; i < 6; i++) {
      ColumnConstraints columnConstraints = new ColumnConstraints();
      if (i < 5) {
        columnConstraints.setPercentWidth(16);
      } else {
        columnConstraints.setPercentWidth(20);
      }
      columnConstraints.setHalignment(HPos.LEFT);
      sharesHeader.getColumnConstraints().add(columnConstraints);
    }

    this.sharesBox = new VBox();
    ScrollPane scrollPane = new ScrollPane(sharesBox);
    scrollPane.setFitToWidth(true);

    this.getChildren().addAll(
        sharesHeader,
        scrollPane
    );

    createSharesHeader();
  }

  private void createSharesHeader() {
    Button symbolShareHeaderButton = new Button("Symbol");
    symbolShareHeaderButton.getStyleClass().addAll("standard-text", "bold-text");
    symbolShareHeaderButton.setOnAction(event -> {
      currentSort = Comparator.comparing(share -> share.getStock().getSymbol());
      ascending = !ascending;
      updateShares();
    });

    Button companyShareHeaderButton = new Button("Company Name");
    companyShareHeaderButton.getStyleClass().addAll("standard-text", "bold-text");
    companyShareHeaderButton.setOnAction(event -> {
      currentSort = Comparator.comparing(share -> share.getStock().getCompany());
      ascending = !ascending;
      updateShares();
    });

    Button quantityShareHeaderButton = new Button("Quantity");
    quantityShareHeaderButton.getStyleClass().addAll("standard-text", "bold-text");
    quantityShareHeaderButton.setOnAction(event -> {
      currentSort = Comparator.comparing(Share::getQuantity);
      ascending = !ascending;
      updateShares();
    });

    Button totalValueButton = new Button("Total Value");
    totalValueButton.getStyleClass().addAll("standard-text", "bold-text");
    totalValueButton.setOnAction(event -> {
      currentSort = Comparator.comparing(
          share -> share.getStock().getCurrentPrice().multiply(share.getQuantity())
      );
      ascending = !ascending;
      updateShares();
    });

    Button changeInPriceShareHeaderButton = new Button("Change in Price");
    changeInPriceShareHeaderButton.getStyleClass().addAll("standard-text", "bold-text");
    changeInPriceShareHeaderButton.setOnAction(event -> {
      currentSort = Comparator.comparing(
          share -> share.getStock().getCurrentPrice().subtract(share.getPurchasePrice())
      );
      ascending = !ascending;
      updateShares();
    });

    sharesHeader.add(symbolShareHeaderButton, 0, 0);
    sharesHeader.add(companyShareHeaderButton, 1, 0);
    sharesHeader.add(quantityShareHeaderButton, 2, 0);
    sharesHeader.add(totalValueButton, 3, 0);
    sharesHeader.add(changeInPriceShareHeaderButton, 4, 0);
  }

  private void updateShares() {
    sharesBox.getChildren().clear();

    List<Share> shares = new ArrayList<>(
        gameSession.getPlayer().getPortfolio().getShares()
    );

    if (currentSort != null) {
      shares.sort(ascending ? currentSort : currentSort.reversed());
    }

    for (Share share : shares) {
      GridPane shareBox = new GridPane();
      shareBox.getStyleClass().add("share-table-row");

      for(int i = 0; i < 6; i++) {
        ColumnConstraints columnConstraints = new ColumnConstraints();
        if (i < 5) {
          columnConstraints.setPercentWidth(16);
        } else {
          columnConstraints.setPercentWidth(20);
        }
        columnConstraints.setHalignment(HPos.LEFT);
        shareBox.getColumnConstraints().add(columnConstraints);
      }

      BigDecimal quantity = share.getQuantity();
      BigDecimal purchasePrice = share.getPurchasePrice();
      BigDecimal currentPrice = share.getStock().getCurrentPrice();

      Label symbolLabel = new Label(share.getStock().getSymbol());
      symbolLabel.getStyleClass().add("standard-text");

      Label companyLabel = new Label(share.getStock().getCompany());
      companyLabel.getStyleClass().add("standard-text");

      Label quantityLabel = new Label(quantity + " Share(s)");
      quantityLabel.getStyleClass().add("standard-text");

      Label totalValueLabel = new Label("$" + currentPrice.multiply(quantity).toString());
      totalValueLabel.getStyleClass().add("standard-text");

      Label changeInPriceLabel = new Label("$" + currentPrice.subtract(purchasePrice).toString());
      changeInPriceLabel.getStyleClass().add("standard-text");
      if (currentPrice.subtract(purchasePrice).signum() == 1) {
        changeInPriceLabel.getStyleClass().add("green-text");
      } else if (currentPrice.subtract(purchasePrice).signum() == -1) {
        changeInPriceLabel.getStyleClass().add("red-text");
      }

      Button historicPrices = new Button("Historic Prices");
      historicPrices.getStyleClass().add("share-table-button");
      historicPrices.setOnAction(event -> {
        HistoricalPricesDialog historicalPricesDialog = new HistoricalPricesDialog(share.getStock(), gameSession.getExchange());
        historicalPricesDialog.showAndGetResult();
      });

      Button sellButton = new Button("Sell");
      sellButton.getStyleClass().add("share-table-button");
      sellButton.setOnAction(event -> {
        SellDialog sellDialog = new SellDialog(gameController, share);
        sellDialog.showAndGetResult();
      });

      BorderPane buttons = new BorderPane();
      buttons.setLeft(historicPrices);
      buttons.setRight(sellButton);


      shareBox.add(symbolLabel, 0, 0);
      shareBox.add(companyLabel, 1, 0);
      shareBox.add(quantityLabel, 2, 0);
      shareBox.add(totalValueLabel, 3, 0);
      shareBox.add(changeInPriceLabel, 4, 0);
      shareBox.add(buttons, 5, 0);

      sharesBox.getChildren().add(shareBox);
    }
  }

  @Override
  public void onGameStateChanged() {
    updateShares();
  }
}
