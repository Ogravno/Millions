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

  private final Label weekLabel;
  private final Label graphPanel;
  private final Label portfolioLabel;
  private final Label sharesLabel;
  private final Label headerLabel;

  // for portfolio
  private final Label money;
  private final Label netWorth;
  private final Label status;

  private final VBox sharesBox;

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
    this.exchangeGameView = new ExchangeGameView(gameSession);

    this.weekLabel = new Label();
    this.graphPanel = new Label("Future graph");
    this.portfolioLabel = new Label("Portfolio");
    this.sharesLabel = new Label("Your Shares");
    this.headerLabel = new Label("Future header");

    this.money = new Label();
    this.netWorth = new Label();
    this.status = new Label();

    this.sharesBox = new VBox(10);

    Button advanceWeekButton = new Button("Advance Week");
    advanceWeekButton.setOnAction(event -> this.gameController.advanceWeek());

    // Main panes
    BorderPane root = new BorderPane();
    HBox header = new HBox(10);
    VBox mainCenter = new VBox(10);

    // Header
    header.getChildren().addAll(headerLabel, weekLabel, advanceWeekButton);

    // MainCenter
    HBox hBoxMainCenter = new HBox(10); // Contains Graph and portfolio

    VBox portfolio = new VBox(10);
    portfolio.getChildren().addAll(portfolioLabel, money, netWorth, status);

    sharesBox.getChildren().addAll(sharesLabel);

    hBoxMainCenter.getChildren().addAll(graphPanel, portfolio);
    mainCenter.getChildren().addAll(hBoxMainCenter, sharesBox);

    // Connect panes
    root.setTop(header);
    root.setCenter(mainCenter);

    // Padding
    root.setPadding(new Insets(20));
    mainCenter.setPadding(new Insets(10));
    header.setPadding(new Insets(10));

    // Spacing
    header.setSpacing(20);
    hBoxMainCenter.setSpacing(20);

    this.scene = new Scene(root, 400, 200);

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

  /**
   * Updates the view with current game data.
   */
  private void updateView() {
    weekLabel.setText("Week: " + gameSession.getExchange().getWeek());
    exchangeGameView.updateView();

    // Portfolio
    money.setText("Your money: " + gameSession.getPlayer().getMoney());
    netWorth.setText("Your net worth: " + gameSession.getPlayer().getNetWorth());
    status.setText("Status: " + gameSession.getPlayer().getStatus());

    // Shares
    sharesBox.getChildren().clear();
    sharesBox.getChildren().add(sharesLabel);

    for (Share share : gameSession.getPlayer().getPortfolio().getShares()) {
      Label shareLabel = new Label(
        share.getStock().getSymbol() + " - " + share.getQuantity()
      );
      sharesBox.getChildren().add(shareLabel);
    }
  }

  @Override
  public void onGameStateChanged() {
    updateView();
  }
}
