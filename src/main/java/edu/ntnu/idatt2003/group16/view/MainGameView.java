package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.controller.GameController;
import edu.ntnu.idatt2003.group16.model.GameSession;
import edu.ntnu.idatt2003.group16.model.investment.Share;
import edu.ntnu.idatt2003.group16.observer.GameObserver;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Main view for the game.
 */
public class MainGameView implements GameObserver {

  private final GameController gameController;
  private final GameSession gameSession;
  private final ExchangeGameView exchangeGameView;
  private final TransactionView transactionView;

  private final Label weekLabel;
  private final Label graphPanel;
  private final Label portfolioLabel;
  private final Label sharesLabel;

  // for portfolio
  private final Label money;
  private final Label netWorth;
  private final Label status;

  private final BorderPane root;
  private final HBox header;
  private final VBox mainCenter;
  private final VBox sharesBox;
  private final HBox sharesHeader;

  private final Scene scene;

  /**
   * Creates the main game view.
   *
   * @param gameController the controller for user actions.
   * @param gameSession the active game session.
   */
  public MainGameView(GameController gameController, GameSession gameSession) {
    this.gameController = gameController;
    this.gameSession = gameSession;
    this.exchangeGameView = new ExchangeGameView(gameSession, gameController);
    this.transactionView = new TransactionView(gameSession);

    this.weekLabel = new Label();
    this.graphPanel = new Label("Future graph");
    this.portfolioLabel = new Label("Portfolio");
    this.sharesLabel = new Label("Your Shares");

    this.money = new Label();
    this.netWorth = new Label();
    this.status = new Label();

    this.root = new BorderPane();
    this.header = new HBox(10);
    this.mainCenter = new VBox(10);
    this.sharesBox = new VBox(10);
    this.sharesHeader = new HBox(10);

    Button advanceWeekButton = new Button("Advance Week");
    advanceWeekButton.setOnAction(event -> this.gameController.advanceWeek());

    Button goToMainViewButton = new Button("Home");
    goToMainViewButton.setOnAction(event -> showMainView());

    Button goToExchangeViewButton = new Button("Exchange");
    goToExchangeViewButton.setOnAction(event -> showExchangeView());

    Button goToTransactionsViewButton = new Button("Transactions");
    goToTransactionsViewButton.setOnAction(event -> changeTransactionsView());

    // Header
    header.getChildren().addAll(
        goToMainViewButton,
        goToExchangeViewButton,
        goToTransactionsViewButton,
        weekLabel,
        advanceWeekButton);

    // MainCenter
    HBox mainCenterBox = new HBox(10); // Contains Graph and portfolio

    VBox portfolio = new VBox(10);
    portfolio.getChildren().addAll(portfolioLabel, money, netWorth, status);

    sharesBox.getChildren().addAll(sharesLabel);

    mainCenterBox.getChildren().addAll(graphPanel, portfolio);
    mainCenter.getChildren().addAll(mainCenterBox, sharesBox);

    // SharesHeader
    Label sellShareHeaderLabel = new Label("Sell");
    Button symbolShareHeaderButton = new Button("Symbol");
    Button companyShareHeaderButton = new Button("Company Name");
    Button quantityShareHeaderButton = new Button("Quantity");
    Button purchasePriceShareHeaderButton = new Button("Purchase Price");
    Button currentPriceShareHeaderButton = new Button("Current Price");
    Button changeInPriceShareHeaderButton = new Button("Change in Price");

    sharesHeader.getChildren().addAll(
      sellShareHeaderLabel,
      symbolShareHeaderButton,
      companyShareHeaderButton,
      quantityShareHeaderButton,
      purchasePriceShareHeaderButton,
      currentPriceShareHeaderButton,
      changeInPriceShareHeaderButton
    );

    // Connect panes
    root.setTop(header);
    root.setCenter(mainCenter);

    // Padding
    root.setPadding(new Insets(20));
    mainCenter.setPadding(new Insets(10));
    header.setPadding(new Insets(10));

    // Spacing
    header.setSpacing(20);
    mainCenterBox.setSpacing(20);

    this.scene = new Scene(root, 600, 400);

    gameSession.addObserver(this);
    updateView();
  }

  /**
   * Returns the scene for this view.
   *
   * @return the scene
   */
  public Scene getScene() {
    return scene;
  }

  private void showMainView() {
    root.setCenter(mainCenter);
  }

  private void showExchangeView() {
    root.setCenter(exchangeGameView.getView());
  }

  private void changeTransactionsView() {
    root.setCenter(transactionView.getView());
  }

  /**
   * Updates the view with current game data.
   */
  private void updateView() {
    updateHeader();
    updatePortfolio();
    updateShares();
    updateExchangeView();
    updateTransactionsView();
  }

  private void updateHeader() {
    weekLabel.setText("Week: " + gameSession.getExchange().getWeek());
  }

  private void updatePortfolio() {
    money.setText("Your money: " + gameSession.getPlayer().getMoney());
    netWorth.setText("Your net worth: " + gameSession.getPlayer().getNetWorth());
    status.setText("Status: " + gameSession.getPlayer().getStatus());
  }

  private void updateShares() {
    sharesBox.getChildren().clear();
    sharesBox.getChildren().addAll(sharesLabel, sharesHeader);

    for (Share share : gameSession.getPlayer().getPortfolio().getShares()) {
      Label shareLabel = new Label(
          share.getStock().getSymbol()
            + " | "
            + share.getStock().getCompany()
            + " | "
            + share.getQuantity()
            + " Shares"
            + " | "
            + share.getPurchasePrice()
            + " | "
            + share.getStock().getCurrentPrice()
            + " | "
            + share.getStock().getCurrentPrice().subtract(share.getPurchasePrice())
      );

      Button sellButton = new Button("Sell");

      sellButton.setOnAction(event -> {
        SellDialog sellDialog = new SellDialog(gameController, share);
        sellDialog.showAndGetResult();
      });

      HBox shareBox = new HBox(10);
      shareBox.getChildren().addAll(sellButton, shareLabel);

      sharesBox.getChildren().add(shareBox);
    }
  }

  private void updateExchangeView() {
    exchangeGameView.updateView();
  }

  private void updateTransactionsView() {
    transactionView.updateView();
  }

  @Override
  public void onGameStateChanged() {
    updateView();
  }
}
