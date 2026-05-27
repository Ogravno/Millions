package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.controller.AppController;
import edu.ntnu.idatt2003.group16.model.investment.Share;
import edu.ntnu.idatt2003.group16.model.investment.Stock;
import edu.ntnu.idatt2003.group16.model.transaction.Purchase;
import edu.ntnu.idatt2003.group16.model.transaction.Transaction;
import java.math.RoundingMode;
import java.net.URL;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Dialog for displaying a transaction receipt.
 */
public class ReceiptDialog extends Dialog<Void> {

  /**
   * Creates a receipt dialog for a transaction.
   *
   * @param appController the application controller
   * @param transaction the transaction to display
   */
  public ReceiptDialog(AppController appController, Transaction transaction) {

    setTitle("Receipt");
    setHeaderText("Receipt");
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

    Share share = transaction.getShare();
    Stock stock = share.getStock();

    Label purchaseOrSale =
        new Label(transaction.getClass().getSimpleName());
    Label stockSymbolAndName =
        new Label("Stock: " + stock.getSymbol() + " " + stock.getCompany());
    Label amountOfShares =
        new Label("Shares: " + share.getQuantity().toString());
    Label pricePerShare =
        new Label("Price per share: " + share.getPurchasePrice().toString());
    Label totalPrice =
        new Label("Total price for shares: "
            + transaction.getCalculator().calculateGross().toString());
    Label totalFee =
        new Label("Fee: " + transaction.getCalculator().calculateCommission()
          .setScale(2, RoundingMode.HALF_UP));
    Label tax =
        new Label("Tax: " + transaction.getCalculator().calculateTax()
          .setScale(2, RoundingMode.HALF_UP));
    Label totalPriceWithFeeAndTax =
        new Label("Total price: " + transaction.getCalculator().calculateTotal()
          .setScale(2, RoundingMode.HALF_UP));
    Label week =
        new Label("Week: " + transaction.getWeek());

    VBox content = new VBox(10);
    if (transaction instanceof Purchase) {
      content.getChildren().addAll(
          purchaseOrSale,
          stockSymbolAndName,
          amountOfShares,
          pricePerShare,
          totalPrice,
          totalFee,
          totalPriceWithFeeAndTax,
          week
      );
    } else {
      content.getChildren().addAll(
          purchaseOrSale,
          stockSymbolAndName,
          amountOfShares,
          pricePerShare,
          totalPrice,
          totalFee,
          tax,
          totalPriceWithFeeAndTax,
          week
      );
    }

    getDialogPane().setContent(content);
    getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

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
