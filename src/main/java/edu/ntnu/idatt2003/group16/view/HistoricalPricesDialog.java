package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.model.exchange.Exchange;
import edu.ntnu.idatt2003.group16.model.investment.Stock;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class HistoricalPricesDialog extends Dialog<Void> {

  private static final int itemsPerPage = 10;

  private int currentpage = 0;
  private final List<BigDecimal> historicalPriceList;
  private final VBox historicalPrices = new VBox(10);
  private final Label pageLabel = new Label();

  public HistoricalPricesDialog(Stock stock, Exchange exchange) {
    this.historicalPriceList = stock.getHistoricalPrices();

    setTitle("Stock");
    setHeaderText(stock.getCompany());
    getDialogPane().setPrefSize(300,225);

    Label currentPrice = new Label( "Current Price: " + stock.getCurrentPrice().toString());
    Label highestPrice = new Label("Highest Price: " + stock.getHighestPrice().toString());
    Label lowestPrice = new Label("Lowest Price: " + stock.getLowestPrice().toString());

    Button prevousButton = new Button("Previous");
    Button nextButton = new Button("Next");

    prevousButton.setOnAction(event -> {
      if (currentpage > 0) {
        currentpage--;
        updateHistoricalPrices();
      }
    });

    nextButton.setOnAction(event -> {
      if (currentpage < getTotalPages() -1) {
        currentpage++;
        updateHistoricalPrices();
      }
    });

    HBox pageButtons = new HBox(10, prevousButton, pageLabel, nextButton);

    updateHistoricalPrices();

    VBox content = new VBox(10);
    content.getChildren().addAll(
      currentPrice,
      highestPrice,
      lowestPrice,
      historicalPrices,
      pageButtons);

    getDialogPane().setContent(content);
    getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

  }

  public void updateHistoricalPrices() {
    historicalPrices.getChildren().clear();

    int start = currentpage * itemsPerPage;
    int end = Math.min(start + itemsPerPage, historicalPriceList.size());

    for (int i = start; i < end; i++) {
      int reversedIndex = historicalPriceList.size() - 1 - i;

      BigDecimal stockPrice = historicalPriceList.get(reversedIndex);

      Label price = new Label(
        "Week: " + (reversedIndex + 1)
          + " Price: " + stockPrice
      );

      historicalPrices.getChildren().add(price);
    }
    pageLabel.setText("Page " + (currentpage + 1) + " of " + getTotalPages());
  }

  private int getTotalPages() {
    return (int) Math.ceil((double) historicalPriceList.size() / itemsPerPage);
  }


  public Optional<Void> showAndGetResult() {
    return showAndWait();
  }
}
