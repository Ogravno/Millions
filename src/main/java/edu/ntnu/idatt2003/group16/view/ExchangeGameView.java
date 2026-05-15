package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.controller.GameController;
import edu.ntnu.idatt2003.group16.model.GameSession;
import edu.ntnu.idatt2003.group16.model.investment.Stock;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

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

  private final Label winnersHeader;
  private final Label losersHeader;

  private final VBox stocksBox;
  private final VBox winnersBox;
  private final VBox losersBox;

  private final HBox root;

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

    this.winnersHeader = new Label("Weekly Winners");
    this.losersHeader = new Label("Weekly Losers");

    this.winnersBox = new VBox(10);
    this.losersBox = new VBox(10);

    VBox winnersLosersBox = new VBox(20);
    winnersLosersBox.getChildren().addAll(
      winnersHeader,
      winnersBox,
      losersHeader,
      losersBox
    );

    this.root = new HBox(20);
    root.setPadding(new Insets(10));
    root.getChildren().addAll(stocksContainer, winnersLosersBox);

    updateView();
  }

  /**
   * Returns the root node for this view.
   *
   * @return the root layout
   */
  public HBox getView() {
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

      Button historicPrices = new Button("Historic Prices");
      historicPrices.setOnAction(event -> {
        StockDialog stockDialog = new StockDialog(stock, gameSession.getExchange());
        stockDialog.showAndGetResult();
      });

      HBox stockBox = new HBox(10);
      stockBox.getChildren().addAll(buyButton, stockLabel, historicPrices);
      stocksBox.getChildren().add(stockBox);
    });
  }

  private void updateWinners() {
    winnersBox.getChildren().clear();

    for (Stock stock : gameSession.getExchange().getGainers(5)) {
      Label stockLabel = new Label(
        stock.getSymbol()
          + " | "
          + stock.getCurrentPrice()
          + " | "
          + stock.getLatestPriceChange()
      );
      winnersBox.getChildren().add(stockLabel);
    }
  }

  private void updateLosers() {
    losersBox.getChildren().clear();

    for (Stock stock : gameSession.getExchange().getLosers(5)) {
      Label stockLabel = new Label(
        stock.getSymbol()
          + " | "
          + stock.getCurrentPrice()
          + " | "
          + stock.getLatestPriceChange()
      );
      losersBox.getChildren().add(stockLabel);
    }
  }
}