package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.controller.GameController;
import edu.ntnu.idatt2003.group16.model.GameSession;
import edu.ntnu.idatt2003.group16.model.investment.Share;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Home view for displaying portfolio and shares.
 */
public class HomeView {

  private final GameController gameController;
  private final GameSession gameSession;

  private final VBox root;
  private final Label graphPanel;
  private final Label portfolioLabel;
  private final Label sharesLabel;

  private final Label money;
  private final Label netWorth;
  private final Label status;

  private final VBox sharesBox;
  private final HBox sharesHeader;

  private Boolean ascending = true;
  private Comparator<Share> currentSort =
    Comparator.comparing(share -> share.getStock().getSymbol());

  public HomeView(GameController gameController, GameSession gameSession) {
    this.gameController = gameController;
    this.gameSession = gameSession;

    this.root = new VBox(10);
    this.graphPanel = new Label("Future graph");
    this.portfolioLabel = new Label("Portfolio");
    this.sharesLabel = new Label("Your Shares");

    this.money = new Label();
    this.netWorth = new Label();
    this.status = new Label();

    this.sharesBox = new VBox(10);
    this.sharesHeader = new HBox(10);

    HBox mainCenterBox = new HBox(10);

    VBox portfolio = new VBox(10);
    portfolio.getChildren().addAll(portfolioLabel, money, netWorth, status);

    mainCenterBox.getChildren().addAll(graphPanel, portfolio);

    createSharesHeader();

    ScrollPane scrollPane = new ScrollPane(sharesBox);

    root.getChildren().addAll(mainCenterBox, scrollPane);
    root.setPadding(new Insets(10));
    mainCenterBox.setSpacing(20);

    updateView();
  }

  public VBox getView() {
    return root;
  }

  public void updateView() {
    updatePortfolio();
    updateShares();
  }

  private void updatePortfolio() {
    money.setText("Your money: " + gameSession.getPlayer().getFormattedMoney());
    netWorth.setText("Your net worth: " + gameSession.getPlayer().getFormattedNetWorth());
    status.setText("Status: " + gameSession.getPlayer().getStatus());
  }

  private void createSharesHeader() {

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

    sharesHeader.getChildren().addAll(
      symbolShareHeaderButton,
      companyShareHeaderButton,
      quantityShareHeaderButton,
      purchasePriceShareHeaderButton,
      currentPriceShareHeaderButton,
      changeInPriceShareHeaderButton,
      totalValueButton,
      totalReturnValueButton
    );
  }

  private void updateShares() {
    sharesBox.getChildren().clear();
    sharesBox.getChildren().addAll(sharesLabel, sharesHeader);

    List<Share> shares = new ArrayList<>(
      gameSession.getPlayer().getPortfolio().getShares()
    );

    if (currentSort != null) {
      shares.sort(ascending ? currentSort : currentSort.reversed());
    }

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
  }
}