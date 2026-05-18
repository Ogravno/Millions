package edu.ntnu.idatt2003.group16.view.components;

import edu.ntnu.idatt2003.group16.controller.GameController;
import edu.ntnu.idatt2003.group16.model.GameSession;
import edu.ntnu.idatt2003.group16.model.investment.Stock;
import edu.ntnu.idatt2003.group16.observer.GameObservable;
import edu.ntnu.idatt2003.group16.observer.GameObserver;
import edu.ntnu.idatt2003.group16.view.BuyDialog;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class ExchangeStocks extends VBox implements GameObserver {
  GameSession gameSession;
  GameController gameController;

  TextField stocksSearchField;
  VBox stocksBox;

  public ExchangeStocks(GameSession gameSession, GameController gameController) {
    this.gameSession = gameSession;
    this.gameController = gameController;

    this.gameSession.addObserver(this);

    this.stocksSearchField = new TextField();
    stocksSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
      onGameStateChanged();
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

    Label stockNameLabel = new Label("Stock Name");
    Label stockValueChangeInPercentLabel = new Label("Change in %");
    Label stockValueLabel = new Label("Stock Value");

    HBox stockHeaders = new HBox(20);
    stockHeaders.getChildren().addAll(
        stockNameLabel,
        stockValueChangeInPercentLabel,
        stockValueLabel
    );

    stocksBox = new VBox(10);
    ScrollPane scrollPane = new ScrollPane(stocksBox);

    this.getChildren().addAll(
        stockFilters,
        stockHeaders,
        scrollPane
    );
  }

  @Override
  public void onGameStateChanged() {
    stocksBox.getChildren().clear();

    List<Stock> stocks;

    if (stocksSearchField.getText().isBlank()) {
      stocks = gameSession.getExchange().getAllStocks();
    } else {
      stocks = gameSession.getExchange().findStocks(stocksSearchField.getText());
    }

    stocks.forEach(stock -> {
      ExchangeStockCard stockCard = new ExchangeStockCard(stock, gameController);

      stocksBox.getChildren().add(stockCard);
    });
  }
}
