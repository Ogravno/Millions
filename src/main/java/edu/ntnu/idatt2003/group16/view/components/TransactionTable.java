package edu.ntnu.idatt2003.group16.view.components;

import edu.ntnu.idatt2003.group16.controller.AppController;
import edu.ntnu.idatt2003.group16.model.transaction.Transaction;
import edu.ntnu.idatt2003.group16.view.ReceiptDialog;
import java.util.List;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * View component for displaying transactions in a table.
 */
public class TransactionTable extends VBox {
  private final AppController appController;

  private final VBox rows;

  /**
   * Creates the transaction table.
   *
   * @param appController the application controller
   */
  public TransactionTable(AppController appController) {
    this.appController = appController;

    Label weekLabel = new Label("Week");
    weekLabel.getStyleClass().addAll("standard-text", "bold-text");

    Label typeLabel = new Label("Type");
    typeLabel.getStyleClass().addAll("standard-text", "bold-text");

    Label symbolLabel = new Label("Symbol");
    symbolLabel.getStyleClass().addAll("standard-text", "bold-text");

    Label stocksLabel = new Label("Shares");
    stocksLabel.getStyleClass().addAll("standard-text", "bold-text");

    Label amountLabel = new Label("Amount");
    amountLabel.getStyleClass().addAll("standard-text", "bold-text");

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

    GridPane headers = new GridPane();
    headers.getStyleClass().add("transaction-table-header");
    headers.getColumnConstraints().addAll(column1, column2, column3, column4, column5);

    headers.add(weekLabel, 0, 0);
    headers.add(typeLabel, 1, 0);
    headers.add(symbolLabel, 2, 0);
    headers.add(stocksLabel, 3, 0);
    headers.add(amountLabel, 4, 0);

    this.getChildren().add(headers);

    this.rows = new VBox();
    ScrollPane transactionsScrollPane = new ScrollPane(this.rows);
    transactionsScrollPane.setFitToWidth(true);

    this.getChildren().add(transactionsScrollPane);
  }

  /**
   * Sets the transactions displayed in the table.
   *
   * @param transactions the transactions to display
   */
  public void setEntries(List<Transaction> transactions) {
    rows.getChildren().clear();

    transactions.forEach(transaction -> {
      TransactionTableRow row = new TransactionTableRow(transaction);
      row.getStyleClass().add("transaction-table-row");
      row.setCursor(Cursor.HAND);
      row.setOnMouseClicked(event -> {
        ReceiptDialog receiptDialog = new ReceiptDialog(appController, transaction);
        receiptDialog.showAndGetResult();
      });

      rows.getChildren().add(row);
    });
  }
}
