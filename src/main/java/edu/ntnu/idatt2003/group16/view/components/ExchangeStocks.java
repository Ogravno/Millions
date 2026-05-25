package edu.ntnu.idatt2003.group16.view.components;

import edu.ntnu.idatt2003.group16.controller.GameController;
import edu.ntnu.idatt2003.group16.model.GameSession;
import edu.ntnu.idatt2003.group16.model.investment.Stock;
import edu.ntnu.idatt2003.group16.observer.GameObserver;
import edu.ntnu.idatt2003.group16.view.BuyDialog;

import java.util.Comparator;
import java.util.List;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * View for displaying stocks for the exchange page.
 *
 * @author Odin Grav
 */
public class ExchangeStocks extends VBox implements GameObserver {
  GameSession gameSession;
  GameController gameController;

  TextField stocksSearchField;
  VBox stocksBox;

  private boolean ascending = true;
  private String currentSortName = "";

  /**
   * Draws the stocks section of the exchange page.
   *
   * @param gameSession the current game session
   * @param gameController the game controller
   */
  public ExchangeStocks(GameSession gameSession, GameController gameController) {
    this.gameSession = gameSession;
    this.gameController = gameController;

    this.gameSession.addObserver(this);

    Label headline = new Label("Purchase shares");
    headline.getStyleClass().add("headline");

    this.stocksSearchField = new TextField();
    this.stocksSearchField.setPromptText("Search...");
    stocksSearchField.getStyleClass().add("searchbar");
    stocksSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
      String searchTerm = stocksSearchField.getText();
      List<Stock> foundStocks = gameSession.getExchange().findStocks(searchTerm);

      drawStocks(foundStocks);
    });

    Label sortLabel = new Label("Sort: ");
    sortLabel.getStyleClass().add("standard-text");

    ToggleGroup sortButtons = new ToggleGroup();
    sortButtons.selectedToggleProperty().addListener((obsVal, oldVal, newVal) -> {
      if (newVal == null) {
        oldVal.setSelected(true);
      }
    });

    ToggleButton sortSymbol = new ToggleButton("Symbol");
    sortSymbol.setToggleGroup(sortButtons);
    sortSymbol.setSelected(true);
    sortSymbol.setOnAction(event ->
      sortAndDraw("Symbol", Comparator.comparing(Stock::getSymbol)));

    ToggleButton sortName = new ToggleButton("Name");
    sortName.setToggleGroup(sortButtons);
    sortName.setOnAction(event ->
      sortAndDraw("Name", Comparator.comparing(Stock::getCompany)));

    ToggleButton sortValue = new ToggleButton("Stock value");
    sortValue.setToggleGroup(sortButtons);
    sortValue.setOnAction(event ->
      sortAndDraw("Current value", Comparator.comparing(Stock::getCurrentPrice)));

    HBox sortContainer = new HBox();
    sortContainer.getChildren().addAll(
        sortLabel,
        sortName,
        sortSymbol,
        sortValue
    );
    sortContainer.getStyleClass().addAll("sort-buttons");

    VBox stockFilters = new VBox(
        stocksSearchField,
        sortContainer
    );
    stockFilters.getStyleClass().add("stock-filters");

    stocksBox = new VBox();
    ScrollPane scrollPane = new ScrollPane(stocksBox);
    scrollPane.setFitToWidth(true);

    this.getChildren().addAll(
        headline,
        stockFilters,
        scrollPane
    );

    drawStocks(gameSession.getExchange().getAllStocks());

    this.getStyleClass().add("tile");
  }

  private void drawStocks(List<Stock> stocks) {
    stocksBox.getChildren().clear();

    stocks.forEach(stock -> {
      ExchangeStockCard stockCard = new ExchangeStockCard(stock);
      stockCard.getStyleClass().add("stock-card");
      stockCard.setCursor(Cursor.HAND);
      stockCard.setOnMouseClicked(event -> {
        BuyDialog buyDialog = new BuyDialog(gameController, stock);
        buyDialog.showAndGetResult();
      });

      stocksBox.getChildren().add(stockCard);
    });
  }

  private void sortAndDraw(String sortName, Comparator<Stock> comparator) {
    if (currentSortName.equals(sortName)) {
      ascending = !ascending;
    } else {
      currentSortName = sortName;
      ascending = true;
    }

    List<Stock> sortedStocks = gameSession.getExchange()
      .getAllStocks()
      .stream()
      .sorted(ascending ? comparator : comparator.reversed())
      .toList();

    drawStocks(sortedStocks);
  }

  @Override
  public void onGameStateChanged() {
    stocksSearchField.clear();
    drawStocks(gameSession.getExchange().getAllStocks());
  }
}
