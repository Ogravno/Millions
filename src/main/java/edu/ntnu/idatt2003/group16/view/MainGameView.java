package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.controller.GameController;
import edu.ntnu.idatt2003.group16.model.GameSession;
import edu.ntnu.idatt2003.group16.observer.GameObserver;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * Main view for the game.
 */
public class MainGameView implements GameObserver {

  private final GameController gameController;
  private final GameSession gameSession;

  private final HomeView homeView;
  private final ExchangeGameView exchangeGameView;
  private final TransactionView transactionView;

  private final Label weekLabel;

  private final BorderPane root;
  private final HBox header;
  private final Scene scene;

  public MainGameView(GameController gameController, GameSession gameSession) {
    this.gameController = gameController;
    this.gameSession = gameSession;

    this.homeView = new HomeView(gameController, gameSession);
    this.exchangeGameView = new ExchangeGameView(gameSession, gameController);
    this.transactionView = new TransactionView(gameSession);

    this.weekLabel = new Label();

    this.root = new BorderPane();
    this.header = new HBox(10);

    Button advanceWeekButton = new Button("Advance Week");
    advanceWeekButton.setOnAction(event -> this.gameController.advanceWeek());

    Button goToMainViewButton = new Button("Home");
    goToMainViewButton.setOnAction(event -> showMainView());

    Button goToExchangeViewButton = new Button("Exchange");
    goToExchangeViewButton.setOnAction(event -> showExchangeView());

    Button goToTransactionsViewButton = new Button("Transactions");
    goToTransactionsViewButton.setOnAction(event -> showTransactionsView());

    header.getChildren().addAll(
      goToMainViewButton,
      goToExchangeViewButton,
      goToTransactionsViewButton,
      weekLabel,
      advanceWeekButton
    );

    root.setTop(header);
    root.setCenter(homeView.getView());

    root.setPadding(new Insets(20));
    header.setPadding(new Insets(10));
    header.setSpacing(20);

    this.scene = new Scene(root, 600, 400);

    gameSession.addObserver(this);
    updateView();
  }

  public Scene getScene() {
    return scene;
  }

  private void showMainView() {
    root.setCenter(homeView.getView());
  }

  private void showExchangeView() {
    root.setCenter(exchangeGameView.getView());
  }

  private void showTransactionsView() {
    root.setCenter(transactionView.getView());
  }

  private void updateView() {
    updateHeader();
    homeView.updateView();
    exchangeGameView.updateView();
    transactionView.updateView();
  }

  private void updateHeader() {
    weekLabel.setText("Week: " + gameSession.getExchange().getWeek());
  }

  @Override
  public void onGameStateChanged() {
    updateView();
  }
}