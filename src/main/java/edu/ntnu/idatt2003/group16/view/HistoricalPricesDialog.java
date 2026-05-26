package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.controller.AppController;
import edu.ntnu.idatt2003.group16.model.exchange.Exchange;
import edu.ntnu.idatt2003.group16.model.investment.Stock;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Dialog for displaying historical stock prices.
 */
public class HistoricalPricesDialog extends Dialog<Void> {

  private static final int itemsPerPage = 10;

  private int currentPage = 0;
  private final List<BigDecimal> historicalPriceList;
  private final VBox historicalPrices = new VBox(10);
  private final Label pageLabel = new Label();

  /**
   * Creates a dialog for displaying historical stock prices.
   *
   * @param appController the application controller
   * @param stock the stock to display
   * @param exchange the exchange containing the stock
   */
  public HistoricalPricesDialog(AppController appController, Stock stock, Exchange exchange) {
    this.historicalPriceList = stock.getHistoricalPrices();

    setTitle("Stock");
    setHeaderText(stock.getCompany());
    getDialogPane().setPrefSize(300, 225);

    URL themeStyleSheet;
    if (appController.isDarkTheme()) {
      themeStyleSheet = getClass().getResource("/css/dark-theme.css");
    } else {
      themeStyleSheet = getClass().getResource("/css/light-theme.css");
    }
    if (themeStyleSheet != null) {
      getDialogPane().getStylesheets().add(themeStyleSheet.toExternalForm());
    }

    URL styleSheet = getClass().getResource("/css/dialog.css");
    if (styleSheet != null) {
      getDialogPane().getStylesheets().add(styleSheet.toExternalForm());
    }

    Button prevousButton = new Button("Previous");
    Button nextButton = new Button("Next");

    prevousButton.setOnAction(event -> {
      if (currentPage > 0) {
        currentPage--;
        updateHistoricalPrices();
      }
    });

    nextButton.setOnAction(event -> {
      if (currentPage < getTotalPages() - 1) {
        currentPage++;
        updateHistoricalPrices();
      }
    });

    HBox pageButtons = new HBox(10, prevousButton, pageLabel, nextButton);

    updateHistoricalPrices();

    Label currentPrice = new Label("Current Price: " + stock.getCurrentPrice().toString());
    Label highestPrice = new Label("Highest Price: " + stock.getHighestPrice().toString());
    Label lowestPrice = new Label("Lowest Price: " + stock.getLowestPrice().toString());

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

  /**
   * Updates the displayed historical prices.
   *
   * <p>Prices are displayed in pages with the newest
   * prices shown first.</p>
   */
  public void updateHistoricalPrices() {
    historicalPrices.getChildren().clear();

    int start = currentPage * itemsPerPage;
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
    pageLabel.setText("Page " + (currentPage + 1) + " of " + getTotalPages());
  }

  private int getTotalPages() {
    return (int) Math.ceil((double) historicalPriceList.size() / itemsPerPage);
  }

  /**
   * Opens the dialog and waits for the user response.
   *
   * @return an empty optional when the dialog is closed
   */
  public Optional<Void> showAndGetResult() {
    return showAndWait();
  }
}
