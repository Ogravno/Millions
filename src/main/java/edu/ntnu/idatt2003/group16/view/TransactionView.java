package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.model.GameSession;
import edu.ntnu.idatt2003.group16.model.transaction.Transaction;
import edu.ntnu.idatt2003.group16.model.transaction.TransactionArchive;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * View for displaying transactions
 */
public class TransactionView {
  private final GameSession gameSession;

  VBox root = new VBox(10);
  HBox header = new HBox(10);
  VBox transactions = new VBox(10);

  private final Label date;
  private final Label boughtSold;
  private final Label shares;
  private final Label amount;
  private final TextField transactionSearchField;


  /**
   * Creates the transaction view.
   *
   * @param gameSession the active game session.
   */
  public TransactionView(GameSession gameSession) {
    if (gameSession == null) {
      throw new IllegalArgumentException("GamesSession cannot be null");
    }
    this.gameSession = gameSession;

    this.date = new Label("Week");
    this.boughtSold = new Label("Type");
    this.shares = new Label("Shares");
    this.amount = new Label("Amount");

    this.transactionSearchField = new TextField();
    transactionSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
      updateTransactions();
    });


    header.getChildren().addAll(
      date,
      boughtSold,
      shares,
      amount
    );

    ScrollPane transactionsScrollPane = new ScrollPane(transactions);

    root.getChildren().addAll(header, transactionSearchField, transactionsScrollPane);
    updateView();
  }

  public VBox getView() {
    return root;
  }

  /**
   * Updates the transactionView
   */
  public void updateView() {
    updateTransactions();
  }

  public void updateTransactions() {
    transactions.getChildren().clear();

    List<Transaction> transactionList;

    if (transactionSearchField.getText().isBlank()) {
      transactionList = gameSession.getPlayer().getTransactionArchive().getTransactions();
    } else {
      transactionList = gameSession.getPlayer().getTransactionArchive().findTransactions(transactionSearchField.getText());
    }

    for (Transaction transaction : transactionList) {

      HBox transactionsInRow = new HBox(10);

      int transactionDate = transaction.getWeek();
      String transactionBoughtSold = transaction.getClass().getSimpleName();
      String transactionShare = transaction.getShare().getStock().getSymbol();
      String transactionAmount = transaction.getShare().getPurchasePrice().toString();

      Button button = new Button(
        transactionDate + " | "
          + transactionBoughtSold + " | "
          + transactionShare + " | "
          + transactionAmount
        );

      button.setOnAction(event -> {
        ReceiptDialog receiptDialog = new ReceiptDialog(transaction);
        receiptDialog.showAndGetResult();
      });


      transactionsInRow.getChildren().addAll(
        button
      );

      transactions.getChildren().add(transactionsInRow);
    }

  }
}
