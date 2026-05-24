package edu.ntnu.idatt2003.group16.view.components;

import edu.ntnu.idatt2003.group16.model.transaction.Purchase;
import edu.ntnu.idatt2003.group16.model.transaction.Transaction;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TransactionTableRow extends GridPane {
  public TransactionTableRow(Transaction transaction) {
    Label weekLabel = new Label(Integer.toString(transaction.getWeek()));
    weekLabel.getStyleClass().addAll("standard-text");

    Label typeLabel = new Label(transaction instanceof Purchase ? "Purchase" : "Sale");
    typeLabel.getStyleClass().addAll("standard-text");

    Label symbolLabel = new Label(transaction.getShare().getStock().getSymbol());
    symbolLabel.getStyleClass().addAll("standard-text");

    Label stocksLabel = new Label(transaction.getShare().getQuantity().toString());
    stocksLabel.getStyleClass().addAll("standard-text");

    BigDecimal amount = transaction.getCalculator().calculateTotal().setScale(2, RoundingMode.HALF_UP);
    Label amountLabel = new Label("$" + amount.toString());
    amountLabel.getStyleClass().addAll("standard-text");

    ColumnConstraints column1 = new ColumnConstraints();
    column1.setPercentWidth(20);

    ColumnConstraints column2 = new ColumnConstraints();
    column2.setPercentWidth(20);

    ColumnConstraints column3 = new ColumnConstraints();
    column3.setPercentWidth(20);

    ColumnConstraints column4 = new ColumnConstraints();
    column4.setPercentWidth(20);

    ColumnConstraints column5 = new ColumnConstraints();
    column5.setPercentWidth(20);

    this.getColumnConstraints().addAll(column1, column2, column3, column4, column5);

    this.add(weekLabel, 0, 0);
    this.add(typeLabel, 1, 0);
    this.add(symbolLabel, 2, 0);
    this.add(stocksLabel, 3, 0);
    this.add(amountLabel, 4, 0);
  }
}
