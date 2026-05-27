package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.controller.AppController;
import edu.ntnu.idatt2003.group16.controller.GameController;
import edu.ntnu.idatt2003.group16.model.GameSession;
import edu.ntnu.idatt2003.group16.model.dto.GameSessionDto;
import edu.ntnu.idatt2003.group16.model.filemanagement.SaveWriter;
import edu.ntnu.idatt2003.group16.observer.GameObserver;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * Main view for the game.
 */
public class MainGameView implements GameObserver {

  private final GameController gameController;
  private final GameSession gameSession;

  private final PortfolioView portfolioView;
  private final ExchangeGameView exchangeGameView;
  private final TransactionView transactionView;

  private final Label weekLabel;

  private final BorderPane root;

  private final Runnable backToMainMenuAction;

  /**
   * Creates the main game view.
   *
   * @param appController the application controller
   * @param gameController the controller for user actions
   * @param gameSession the active game session
   * @param backToMainMenuAction action used to return to the main menu
   */
  public MainGameView(AppController appController, GameController gameController,
                      GameSession gameSession, Runnable backToMainMenuAction) {
    this.gameController = gameController;
    this.gameSession = gameSession;
    this.backToMainMenuAction = backToMainMenuAction;

    this.portfolioView = new PortfolioView(appController, gameController, gameSession);
    this.exchangeGameView = new ExchangeGameView(appController, gameSession, gameController);
    this.transactionView = new TransactionView(appController, gameSession);

    this.weekLabel = new Label();

    this.root = new BorderPane();

    ToggleGroup navButtons = new ToggleGroup();
    navButtons.selectedToggleProperty().addListener((obsVal, oldVal, newVal) -> {
      if (newVal == null) {
        oldVal.setSelected(true);
      }
    });

    ToggleButton goToMainViewButton = new ToggleButton("Portfolio");
    FontIcon portfolioIcon = new FontIcon("mdi2f-finance");
    goToMainViewButton.setGraphic(portfolioIcon);
    goToMainViewButton.setToggleGroup(navButtons);
    goToMainViewButton.setSelected(true);
    goToMainViewButton.setOnAction(event -> showMainView());

    ToggleButton goToExchangeViewButton = new ToggleButton("Exchange");
    FontIcon exchangeIcon = new FontIcon("mdi2b-bank");
    goToExchangeViewButton.setGraphic(exchangeIcon);
    goToExchangeViewButton.setToggleGroup(navButtons);
    goToExchangeViewButton.setOnAction(event -> showExchangeView());

    ToggleButton goToTransactionsViewButton = new ToggleButton("Transactions");
    FontIcon transactionIcon = new FontIcon("mdi2i-invoice-list-outline");
    goToTransactionsViewButton.setGraphic(transactionIcon);
    goToTransactionsViewButton.setToggleGroup(navButtons);
    goToTransactionsViewButton.setOnAction(event -> showTransactionsView());

    VBox navigation = new VBox(
        goToMainViewButton,
        goToExchangeViewButton,
        goToTransactionsViewButton
    );
    navigation.getStyleClass().add("navigation");

    Label logo = new Label("StockSim");
    logo.getStyleClass().add("sidebar-logo");

    Button advanceWeekButton = new Button("Advance");
    advanceWeekButton.getStyleClass().add("sidebar-button");
    advanceWeekButton.setOnAction(event -> this.gameController.advanceWeek());

    VBox weekCounter = new VBox(
        weekLabel,
        advanceWeekButton
    );
    weekCounter.getStyleClass().add("week-counter");

    FontIcon themSymbol = new FontIcon("mdi2t-theme-light-dark");
    Button themeButton = new Button("", themSymbol);
    themeButton.getStyleClass().addAll("theme-button", "standard-text");
    themeButton.setOnAction(actionEvent -> {
      appController.changeTheme(this.getView().getScene());
    });

    Button saveAndBackButton = new Button("Save and to Main Menu");
    saveAndBackButton.getStyleClass().add("sidebar-button");
    saveAndBackButton.setOnAction(event -> {
      SaveWriter.saveGame(new GameSessionDto(gameController.getGameSession().getGameName(),
          gameController.getGameSession().getPlayer(),
          gameController.getGameSession().getExchange()));
      backToMainMenuAction.run();
    });

    Button endGame = new Button("End game");
    endGame.getStyleClass().add("sidebar-button");
    endGame.setOnAction(event -> {
      EndDialog endDialog = new EndDialog(appController, gameController, backToMainMenuAction);
      endDialog.showAndGetResult();
    });

    VBox bottom = new VBox(
        weekCounter,
        themeButton,
        saveAndBackButton,
        endGame
    );
    bottom.getStyleClass().add("bottom-sidebar");

    BorderPane header = new BorderPane();
    header.getStyleClass().add("header");

    header.setTop(logo);
    header.setCenter(navigation);
    header.setBottom(bottom);

    root.setLeft(header);
    root.setCenter(portfolioView.getView());

    gameSession.addObserver(this);
    updateView();
  }

  /**
   * Returns the root layout for this view.
   *
   * @return the root layout
   */
  public BorderPane getView() {
    return root;
  }

  private void showMainView() {
    root.setCenter(portfolioView.getView());
  }

  private void showExchangeView() {
    root.setCenter(exchangeGameView.getView());
  }

  private void showTransactionsView() {
    root.setCenter(transactionView.getView());
  }

  private void updateView() {
    updateHeader();
  }

  private void updateHeader() {
    weekLabel.setText("Week: " + gameSession.getExchange().getWeek());
  }

  @Override
  public void onGameStateChanged() {
    updateView();
  }
}