package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.controller.GameController;
import edu.ntnu.idatt2003.group16.model.GameSession;
import edu.ntnu.idatt2003.group16.model.investment.Stock;
import edu.ntnu.idatt2003.group16.view.components.Leaderboard;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.List;

/**
 * View for displaying exchange information.
 */
public class ExchangeGameView {

  private final GameSession gameSession;
  private final GameController gameController;

  private final Label stockNameLabel;
  private final Label stockValueChangeInPercentLabel;
  private final Label stockValueLabel;

  private final TextField stocksSearchField;

  private final Leaderboard winnersLeaderboard;
  private final Leaderboard losersLeaderboard;

  private final VBox stocksBox;

  private final BorderPane root;

  /**
   * Creates the exchange view.
   *
   * @param gameSession the active game session
   */
  public ExchangeGameView(GameSession gameSession, GameController gameController) {
    if (gameSession == null) {
      throw new IllegalArgumentException("GameSession cannot be null.");
    }
    if (gameController == null) {
      throw new IllegalArgumentException("GameController cannot be null.");
    }

    this.gameSession = gameSession;
    this.gameController = gameController;

    this.stocksSearchField = new TextField();
    stocksSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
      updateStocks();
    });

    this.root = new BorderPane();

    URL styleSheet = getClass().getResource("/css/exchange-game-view.css");
    if (styleSheet != null) {
      root.getStylesheets().add(styleSheet.toExternalForm());
    }

    Button sortSymbol = new Button("Symbol");
    Button sortName = new Button("Name");
    Button sortValue = new Button("Stock value");

    HBox sortButtons = new HBox();
    sortButtons.getChildren().addAll(
        sortSymbol,
        sortName,
        sortValue
    );

    Label sortLabel = new Label("Sort: ");

    HBox sortContainer = new HBox();
    sortContainer.getChildren().addAll(
        sortLabel,
        sortButtons
    );

    VBox stockFilters = new VBox(
        stocksSearchField,
        sortContainer
    );

    this.stockNameLabel = new Label("Stock Name");
    this.stockValueChangeInPercentLabel = new Label("Change in %");
    this.stockValueLabel = new Label("Stock Value");

    HBox stockHeaders = new HBox(20);
    stockHeaders.getChildren().addAll(
        stockNameLabel,
        stockValueChangeInPercentLabel,
        stockValueLabel
    );

    this.stocksBox = new VBox(10);
    ScrollPane scrollPane = new ScrollPane(stocksBox);

    VBox stocksContainer = new VBox(10);
    stocksContainer.getChildren().addAll(
        stockFilters,
        stockHeaders,
        scrollPane
    );

    winnersLeaderboard = new Leaderboard("Winners");
    winnersLeaderboard.getStyleClass().add("tile");

    losersLeaderboard = new Leaderboard("Losers");
    losersLeaderboard.getStyleClass().add("tile");


    VBox leaderboards = new VBox(
        winnersLeaderboard,
        losersLeaderboard
    );

    root.setCenter(stocksContainer);
    root.setRight(leaderboards);

    updateView();
  }

  /**
   * Returns the root node for this view.
   *
   * @return the root layout
   */
  public BorderPane getView() {
    return root;
  }

  /**
   * Updates the exchange view with current game data.
   */
  public void updateView() {
    updateStocks();
    updateWinners();
    updateLosers();
  }

  private void updateStocks() {
    stocksBox.getChildren().clear();

    List<Stock> stocks;

    if (stocksSearchField.getText().isBlank()) {
      stocks = gameSession.getExchange().getAllStocks();
    } else {
      stocks = gameSession.getExchange().findStocks(stocksSearchField.getText());
    }

    stocks.forEach(stock -> {
      Label stockLabel = new Label(
        stock.getSymbol()
          + " | "
          + stock.getCompany()
          + " | "
          + stock.getCurrentPrice()
      );

      Button buyButton = new Button("Buy");

      buyButton.setOnAction(event -> {
        BuyDialog buyDialog = new BuyDialog(gameController, stock);
        buyDialog.showAndGetResult();
      });

      HBox stockBox = new HBox(10);
      stockBox.getChildren().addAll(buyButton, stockLabel);
      stocksBox.getChildren().add(stockBox);
    });
  }

  private void updateWinners() {
    winnersLeaderboard.setEntries(gameSession.getExchange().getGainers(5));
  }

  private void updateLosers() {
    losersLeaderboard.setEntries(gameSession.getExchange().getLosers(5));
  }
}