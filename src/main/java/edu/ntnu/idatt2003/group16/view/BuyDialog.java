package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.controller.GameController;
import edu.ntnu.idatt2003.group16.model.investment.Share;
import edu.ntnu.idatt2003.group16.model.investment.Stock;
import edu.ntnu.idatt2003.group16.model.transaction.Purchase;
import edu.ntnu.idatt2003.group16.model.transaction.calculator.PurchaseCalculator;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

/**
 * Dialog for buying stock.
 */
public class BuyDialog extends Dialog<Purchase> {

  public BuyDialog(GameController gameController, Stock stock) {
    if (gameController == null) {
      throw new IllegalArgumentException("GameController cannot be null");
    }
    if (stock == null) {
      throw new IllegalArgumentException("Stock cannot be null");
    }

    setTitle("Buy Stock");
    setHeaderText("Buy " + stock.getSymbol());

    getDialogPane().setPrefSize(300, 225);

    Label priceLabel = new Label("Price: " + stock.getCurrentPrice());
    Label playerBalance = new Label("Balance: " + gameController.getPlayerMoney());
    Label totalPriceLabel = new Label("Total: 0");
    Label grossPriceLabel = new Label();
    Label feePriceLabel = new Label();

    TextField quantityField = new TextField();
    quantityField.setPromptText("Quantity");

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
