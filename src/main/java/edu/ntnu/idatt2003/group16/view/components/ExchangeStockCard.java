package edu.ntnu.idatt2003.group16.view.components;

import edu.ntnu.idatt2003.group16.model.investment.Stock;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
   */
  public ExchangeStockCard(Stock stock) {
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

    this.getChildren().add(layout);
  }
}
