package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.controller.GameController;
import edu.ntnu.idatt2003.group16.model.investment.Stock;
import edu.ntnu.idatt2003.group16.model.transaction.Purchase;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
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

    Label priceLabel = new Label("Price: " + stock.getCurrentPrice());
    Label playerBalance = new Label("Balance: " + gameController.getPlayerMoney());
    TextField quantityField = new TextField();
    quantityField.setPromptText("Quantity");

    VBox content = new VBox(10);
    content.getChildren().addAll(
      new Label("Stock: " + stock.getCompany()),
      priceLabel,
      playerBalance,
      quantityField
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
