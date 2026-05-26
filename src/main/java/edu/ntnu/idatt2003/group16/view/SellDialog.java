package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.controller.AppController;
import edu.ntnu.idatt2003.group16.controller.GameController;
import edu.ntnu.idatt2003.group16.model.investment.Share;
import edu.ntnu.idatt2003.group16.model.transaction.Sale;

import java.math.RoundingMode;
import java.net.URL;
import java.util.Optional;

import edu.ntnu.idatt2003.group16.model.transaction.calculator.SaleCalculator;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Dialog for selling share.
 */
public class SellDialog extends Dialog<Sale> {

  public SellDialog(AppController appController, GameController gameController, Share share) {
    if (appController == null) {
      throw new IllegalArgumentException("AppController cannot be null");
    }
    if (gameController == null) {
      throw new IllegalArgumentException("GameController cannot be null");
    }
    if (share == null) {
      throw new IllegalArgumentException("Share cannot be null");
    }

    setTitle("Sell share");
    setHeaderText("Sell " + share.getStock().getSymbol());

    SaleCalculator calculator = new SaleCalculator(share);

    Label buyingPriceLabel = new Label("Buying price: " + share.getPurchasePrice());
    Label quantity = new Label("Amount: " + share.getQuantity().toString() + " Share(s)");
    Label currentPriceLabel = new Label("Current price: " + share.getStock().getCurrentPrice());
    Label gross = new Label("Total value: " + calculator.formattedGross());
    Label commission = new Label("Commission: " + calculator.formattedCommission());
    Label tax = new Label("Tax: " + calculator.formattedTax());
    Label valueAfterFees = new Label("Value after fees and tax: " + calculator.formattedTotal());

    VBox content = new VBox(10);
    content.getChildren().addAll(
      new Label("Stock: " + share.getStock().getCompany()),
        buyingPriceLabel,
        quantity,
        currentPriceLabel,
        gross,
        commission,
        tax,
        valueAfterFees
    );

    ButtonType sellButtonType = new ButtonType("Sell");

    getDialogPane().setContent(content);
    getDialogPane().getButtonTypes().addAll(sellButtonType, ButtonType.CANCEL);

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

    setResultConverter(button -> {
      if (button == sellButtonType) {
        return gameController.sellShare(share);
      }
      return null;
    });
  }

  /**
   * Opens Dialog and returns the sale if completed.
   *
   * @return The completed sale, if the user sold its share.
   */
  public Optional<Sale> showAndGetResult() {
    return showAndWait();
  }
}
