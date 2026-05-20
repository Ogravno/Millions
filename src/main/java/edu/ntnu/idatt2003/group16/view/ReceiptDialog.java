package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.model.investment.Share;
import edu.ntnu.idatt2003.group16.model.investment.Stock;
import edu.ntnu.idatt2003.group16.model.transaction.Purchase;
import edu.ntnu.idatt2003.group16.model.transaction.Transaction;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.math.RoundingMode;
import java.util.Optional;

public class ReceiptDialog extends Dialog<Void> {

  public ReceiptDialog(Transaction transaction) {
    Share share = transaction.getShare();
    Stock stock = share.getStock();

    setTitle("Receipt");
    setHeaderText("Receipt");
    getDialogPane().setPrefSize(300,225);


    Label purchaseOrSale = new Label(transaction.getClass().getSimpleName());
    Label stockSymbolAndName = new Label("Stock: " + stock.getSymbol() + " " + stock.getCompany());
    Label amountOfShares = new Label("Shares: " + share.getQuantity().toString());
    Label pricePerShare = new Label("Price per share: " + share.getPurchasePrice().toString());
    Label totalPrice = new Label("Total price for shares: " + transaction.getCalculator().calculateGross().toString());
    Label totalFee = new Label("Fee: " + transaction.getCalculator().calculateCommission().setScale(2, RoundingMode.HALF_UP));
    Label tax = new Label("Tax: " + transaction.getCalculator().calculateTax().setScale(2, RoundingMode.HALF_UP));
    Label totalPriceWithFeeAndTax = new Label("Total price: " + transaction.getCalculator().calculateTotal().setScale(2, RoundingMode.HALF_UP));
    Label week = new Label("Week: " + transaction.getWeek());

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

  public Optional<Void> showAndGetResult() {
    return showAndWait();
  }
}
