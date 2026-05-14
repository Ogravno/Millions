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
    Label sellShareHeaderLabel = new Label("Sell");
    Button symbolShareHeaderButton = new Button("Symbol");
    Button companyShareHeaderButton = new Button("Company Name");
    Button quantityShareHeaderButton = new Button("Quantity");
    Button purchasePriceShareHeaderButton = new Button("Purchase Price");
    Button currentPriceShareHeaderButton = new Button("Current Price");
    Button changeInPriceShareHeaderButton = new Button("Change in Price");
    Button totalValueButton = new Button("Total Value");
    Button totalReturnValueButton = new Button("Total Return Value");

    sharesHeader.getChildren().addAll(
      sellShareHeaderLabel,
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

    for (Share share : gameSession.getPlayer().getPortfolio().getShares()) {
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

      shareBox.getChildren().addAll(
        sellButton,
        symbolLabel,
        companyLabel,
        quantityLabel,
        purchasePriceLabel,
        currentPriceLabel,
        changeInPriceLabel,
        totalValueLabel,
        totalReturnValueLabel
      );

      sharesBox.getChildren().add(shareBox);
    }
  }
}