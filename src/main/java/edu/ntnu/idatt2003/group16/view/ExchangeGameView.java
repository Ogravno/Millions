package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.controller.GameController;
import edu.ntnu.idatt2003.group16.model.GameSession;
import edu.ntnu.idatt2003.group16.model.investment.Stock;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

/**
 * View for displaying exchange information.
 */
public class ExchangeGameView {

  private final GameSession gameSession;
  private final GameController gameController;

  private final Label stockNameLabel;
  private final Label stockValueChangeInPercentLabel;
  private final Label stockValueLabel;

  private final Label winnersHeader;
  private final Label losersHeader;

  private final VBox stocksBox;
  private final VBox winnersBox;
  private final VBox losersBox;

  private final VBox root;

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

    this.stockNameLabel = new Label("Stock Name");
    this.stockValueChangeInPercentLabel = new Label("Change in %");
    this.stockValueLabel = new Label("Stock Value");

    this.winnersHeader = new Label("Weekly Winners");
    this.losersHeader = new Label("Weekly Losers");

    this.stocksBox = new VBox(10);
    this.winnersBox = new VBox(10);
    this.losersBox = new VBox(10);

    HBox headerRow = new HBox(20);
    headerRow.getChildren().addAll(
      stockNameLabel,
      stockValueChangeInPercentLabel,
      stockValueLabel
    );

    VBox winnersLosersBox = new VBox(20);
    winnersLosersBox.getChildren().addAll(
      winnersHeader,
      winnersBox,
      losersHeader,
      losersBox
    );

    HBox contentRow = new HBox(40);
    contentRow.getChildren().addAll(stocksBox, winnersLosersBox);

    this.root = new VBox(20);
    root.setPadding(new Insets(10));
    root.getChildren().addAll(headerRow, contentRow);

    updateView();
  }

  /**
   * Returns the root node for this view.
   *
   * @return the root layout
   */
  public VBox getView() {
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

    for (Stock stock : gameSession.getExchange().getAllStocks()) {
      Label stockLabel = new Label(
        stock.getSymbol()
          + " - "
          + stock.getCompany()
          + " - "
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
    }
  }

  private void updateWinners() {
    winnersBox.getChildren().clear();

    for (Stock stock : gameSession.getExchange().getGainers(5)) {
      Label stockLabel = new Label(
        stock.getSymbol()
          + " - "
          + stock.getCurrentPrice()
          + " - "
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
          + " - "
          + stock.getCurrentPrice()
          + " - "
          + stock.getLatestPriceChange()
      );
      losersBox.getChildren().add(stockLabel);
    }
  }
}