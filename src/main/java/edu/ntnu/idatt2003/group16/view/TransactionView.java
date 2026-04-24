package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.model.GameSession;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TransactionView {
  private final GameSession gameSession;

  VBox root = new VBox(10); // Main Vbox
  HBox header = new HBox(10); // Header 2
  VBox transactions = new VBox(10); // Transactions list

  // Header 2 labels
  private final Label date;
  private final Label boughtSold;
  private final Label shares;
  private final Label amount;

  // Transactions labels
  private final Label transactionDate;
  private final Label transactionBoughtSold;
  private final Label transactionShare;
  private final Label transactionAmount;

  public TransactionView(GameSession gameSession) {
    if (gameSession == null) {
      throw new IllegalArgumentException("GamesSession cannot be null");
    }
    this.gameSession = gameSession;

    this.date = new Label("Date");
    this.boughtSold = new Label("Bought/Sold");
    this.shares = new Label("Shares");
    this.amount = new Label("Amount");

    this.transactionDate = new Label();
    this.transactionBoughtSold = new Label();
    this.transactionShare = new Label();
    this.transactionAmount = new Label();

    header.getChildren().addAll(
      date,
      boughtSold,
      shares,
      amount
    );

    transactions.getChildren().addAll(
      transactionDate,
      transactionBoughtSold,
      transactionShare,
      transactionAmount
    );


    root.getChildren().addAll(header, transactions);
    updateView();
  }

  public VBox getView() {
    return root;
  }

  public void updateView() {
    updateTransactions();
  }

  public void updateTransactions() {

  }
}
