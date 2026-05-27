package edu.ntnu.idatt2003.group16.view.components;

import edu.ntnu.idatt2003.group16.controller.AppController;
import edu.ntnu.idatt2003.group16.controller.GameController;
import edu.ntnu.idatt2003.group16.model.GameSession;
import edu.ntnu.idatt2003.group16.model.investment.Stock;
import java.math.BigDecimal;
import java.math.RoundingMode;

import edu.ntnu.idatt2003.group16.view.BuyDialog;
import edu.ntnu.idatt2003.group16.view.HistoricalPricesDialog;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * View for displaying a stock card.
 *
 * @author Odin Grav
 */
public class ExchangeStockCard extends VBox {
  /**
   * Draws a stock card for the exchange view.
   *
   * @param stock the stock to draw a card for
   * @throws IllegalArgumentException if stock is null
   */
  public ExchangeStockCard(AppController appController, GameController gameController,
                           Stock stock) {
    if (stock == null) {
      throw new IllegalArgumentException("Stock cannot be null");
    }

    Label companyLabel = new Label(stock.getCompany());
    companyLabel.getStyleClass().add("sub-heading");

    Label symbolLabel = new Label(stock.getSymbol());
    symbolLabel.getStyleClass().addAll("sub-heading", "light-text");

    Label priceLabel = new Label("$" + stock.getCurrentPrice().toString());
    priceLabel.getStyleClass().addAll("sub-heading", "bold-text");

    StockCardLayout layout = new StockCardLayout();

    layout.add(companyLabel, 0, 0);
    layout.add(symbolLabel, 1, 0);
    layout.add(priceLabel, 2, 0);

    for (int i = 0; i < 3; i++) {
      BigDecimal changePercentage = stock.getPriceChangePercentage(i + 1)
          .multiply(BigDecimal.valueOf(100))
          .setScale(2, RoundingMode.HALF_UP);

      Label changePercentLabel = new Label(changePercentage.toString() + "%");
      if (changePercentage.signum() == 0) {
        changePercentLabel.getStyleClass().add("standard-text");
      } else if (changePercentage.signum() == 1) {
        changePercentLabel.getStyleClass().add("green-text");
      } else {
        changePercentLabel.getStyleClass().add("red-text");
      }
      changePercentLabel.getStyleClass().add("sub-text");

      Label weekLabel = new Label((i + 1) + " week(s) ago");
      weekLabel.getStyleClass().addAll("sub-text", "light-text");

      layout.add(changePercentLabel, i, 2);
      layout.add(weekLabel, i, 3);
    }

    Button buyButton = new Button("Buy");
    buyButton.getStyleClass().add("stock-card-button");
    buyButton.setOnAction(event -> {
      BuyDialog buyDialog = new BuyDialog(appController, gameController, stock);
      buyDialog.showAndGetResult();
    });

    Button priceHistoryButton = new Button("Price History");
    priceHistoryButton.getStyleClass().add("stock-card-button");
    priceHistoryButton.setOnAction(event -> {
      HistoricalPricesDialog historicalPricesDialog = new HistoricalPricesDialog(appController,
          stock, gameController.getGameSession().getExchange());
      historicalPricesDialog.showAndGetResult();
    });

    layout.add(buyButton, 3, 0);
    layout.add(priceHistoryButton, 3, 2, 1, 2);

    this.getChildren().add(layout);
  }
}