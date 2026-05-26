package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.controller.AppController;
import edu.ntnu.idatt2003.group16.controller.GameController;
import edu.ntnu.idatt2003.group16.model.investment.Share;
import edu.ntnu.idatt2003.group16.model.investment.Stock;
import edu.ntnu.idatt2003.group16.model.transaction.Purchase;
import edu.ntnu.idatt2003.group16.model.transaction.calculator.PurchaseCalculator;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.Optional;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

/**
 * Dialog for buying stock.
 */
public class BuyDialog extends Dialog<Purchase> {

  /**
   * Creates a dialog for buying a selected stock.
   *
   * @param appController the application controller
   * @param gameController the game controller used to complete the purchase
   * @param stock the stock to buy
   * @throws IllegalArgumentException if appController, gameController or stock is null
   */
  public BuyDialog(AppController appController, GameController gameController, Stock stock) {
    if (appController == null) {
      throw new IllegalArgumentException("AppController cannot be null");
    }
    if (gameController == null) {
      throw new IllegalArgumentException("GameController cannot be null");
    }
    if (stock == null) {
      throw new IllegalArgumentException("Stock cannot be null");
    }

    setTitle("Buy Stock");
    setHeaderText("Buy " + stock.getSymbol());
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

    TextField quantityField = new TextField();

    quantityField.setTextFormatter(new TextFormatter<>(change -> {
      if (change.getControlNewText().matches("\\d*")) {
        return change;
      }
      return null;
    }));

    quantityField.setPromptText("Quantity");

    Label priceLabel = new Label("Price: " + stock.getCurrentPrice());
    Label playerBalance = new Label("Balance: " + gameController.getPlayerFormattedMoney());
    Label totalPriceLabel = new Label("Total: 0");
    Label grossPriceLabel = new Label();
    Label feePriceLabel = new Label();
    Label tooLittleFunds = new Label("");
    tooLittleFunds.setStyle("-fx-text-fill: red;");

    quantityField.textProperty().addListener((observer, oldValue, newValue) -> {
      try {
        if (newValue.isBlank()) {
          totalPriceLabel.setText("Total: 0");
          grossPriceLabel.setText("");
          feePriceLabel.setText("");
          return;
        }

        BigDecimal quantity = new BigDecimal(newValue);

        Share tempShare = new Share(
            stock,
            quantity,
            stock.getCurrentPrice()
        );

        PurchaseCalculator calculator = new PurchaseCalculator(tempShare);

        BigDecimal gross = calculator.calculateGross().setScale(2, RoundingMode.HALF_UP);
        BigDecimal commission = calculator.calculateCommission().setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = calculator.calculateTotal().setScale(2, RoundingMode.HALF_UP);

        totalPriceLabel.setText("Total price with fees: " + total);
        grossPriceLabel.setText("Price: " + gross);
        feePriceLabel.setText("Fee: " + commission);

        if (total.compareTo(gameController.getPlayerMoney()) > 0) {
          tooLittleFunds.setText("Too little funds");
        } else {
          tooLittleFunds.setText("");
        }

      } catch (Exception e) {
        totalPriceLabel.setText("Invalid quantity");
      }
    });


    VBox content = new VBox(10);
    content.getChildren().addAll(
      new Label("Stock: " + stock.getCompany()),
        priceLabel,
        playerBalance,
        quantityField,
        tooLittleFunds,
        grossPriceLabel,
        feePriceLabel,
        totalPriceLabel
    );

    ButtonType buyButtonType = new ButtonType("Buy");

    getDialogPane().setContent(content);
    getDialogPane().getButtonTypes().addAll(buyButtonType, ButtonType.CANCEL);

    setResultConverter(button -> {
      if (button == buyButtonType) {
        BigDecimal quantity = new BigDecimal(quantityField.getText());
        return gameController.buyStock(stock.getSymbol(), quantity);
      }
      return null;
    });
  }

  /**
   * Opens Dialog and returns the purchase if completed.
   *
   * @return the completed purchase, if the user bought stock.
   */
  public Optional<Purchase> showAndGetResult() {
    return showAndWait();
  }
}
