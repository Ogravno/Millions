package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.controller.GameController;
import edu.ntnu.idatt2003.group16.model.GameSession;
import edu.ntnu.idatt2003.group16.observer.GameObserver;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;

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

  /**
   * Creates the main game view.
   *
   * @param gameController the controller for user actions.
   * @param gameSession the active game session.
   */
  public MainGameView(GameController gameController, GameSession gameSession) {
    this.gameController = gameController;
    this.gameSession = gameSession;

    this.homeView = new HomeView(gameController, gameSession);
    this.exchangeGameView = new ExchangeGameView(gameSession, gameController);
    this.transactionView = new TransactionView(gameSession);

    this.weekLabel = new Label();

    this.root = new BorderPane();

    URL styleSheet = getClass().getResource("/css/main-game-view.css");
    if (styleSheet != null) {
      root.getStylesheets().add(styleSheet.toExternalForm());
    }

    ToggleGroup navButtons = new ToggleGroup();
    navButtons.selectedToggleProperty().addListener((obsVal, oldVal, newVal) -> {
      if (newVal == null)
        oldVal.setSelected(true);
    });

    ToggleButton goToMainViewButton = new ToggleButton("Home");
    goToMainViewButton.setToggleGroup(navButtons);
    goToMainViewButton.setSelected(true);
    goToMainViewButton.setOnAction(event -> showMainView());

    ToggleButton goToExchangeViewButton = new ToggleButton("Exchange");
    goToExchangeViewButton.setToggleGroup(navButtons);
    goToExchangeViewButton.setOnAction(event -> showExchangeView());

    ToggleButton goToTransactionsViewButton = new ToggleButton("Transactions");
    goToTransactionsViewButton.setToggleGroup(navButtons);
    goToTransactionsViewButton.setOnAction(event -> showTransactionsView());

    VBox navigation = new VBox(
        goToMainViewButton,
        goToExchangeViewButton,
        goToTransactionsViewButton
    );
    navigation.getStyleClass().add("navigation");

    Label logo = new Label("StockSim");
    logo.getStyleClass().add("logo");


    Button advanceWeekButton = new Button("Advance");
    advanceWeekButton.setOnAction(event -> this.gameController.advanceWeek());

    HBox weekCounter = new HBox(
        weekLabel,
        advanceWeekButton
    );
    weekCounter.getStyleClass().add("week-counter");

    FontIcon themSymbol = new FontIcon("mdi2b-brightness-2");
    Button themeButton = new Button("", themSymbol);

    VBox bottom = new VBox(
        weekCounter,
        themSymbol
    );
    bottom.getStyleClass().add("bottom-sidebar");

    BorderPane header = new BorderPane();
    header.getStyleClass().add("header");

    header.setTop(logo);
    header.setCenter(navigation);
    header.setBottom(bottom);

    root.setLeft(header);
    root.setCenter(homeView.getView());

    gameSession.addObserver(this);
    updateView();
  }

  /**
   * Returns the scene for this view.
   *
   * @return the scene
   */
  public BorderPane getView() {
    return root;
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