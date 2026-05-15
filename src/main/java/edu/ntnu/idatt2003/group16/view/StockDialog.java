package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.model.exchange.Exchange;
import edu.ntnu.idatt2003.group16.model.investment.Stock;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.util.Optional;

public class StockDialog extends Dialog<Void> {

  public StockDialog(Stock stock, Exchange exchange) {

    setTitle("Stock");
    setHeaderText(stock.getCompany());
    getDialogPane().setPrefSize(300,225);

    Label currentPrice = new Label( "Current Price: " + stock.getCurrentPrice().toString());
    Label highestPrice = new Label("Highest Price: " + stock.getHighestPrice().toString());
    Label lowestPrice = new Label("Lowest Price: " + stock.getLowestPrice().toString());

    VBox historicalPrices = new VBox(10);

    int i = 1;
    for (BigDecimal stockPrice : stock.getHistoricalPrices()) {
      Label price = new Label("Week: " + i + " Price: " + stockPrice.toString());
      historicalPrices.getChildren().add(price);
      i++;
    }

    VBox content = new VBox(10);
    content.getChildren().addAll(currentPrice, highestPrice, lowestPrice, historicalPrices);

    getDialogPane().setContent(content);
    getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

  }

  public Optional<Void> showAndGetResult() {
    return showAndWait();
  }
}
