package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.model.GameSession;
import edu.ntnu.idatt2003.group16.model.transaction.Transaction;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * View for displaying transactions
 */
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

    this.date = new Label("Date");
    this.boughtSold = new Label("Bought/Sold");
    this.shares = new Label("Shares");
    this.amount = new Label("Amount");


    header.getChildren().addAll(
      date,
      boughtSold,
      shares,
      amount
    );


    root.getChildren().addAll(header, transactions);
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

    for (Transaction transaction : gameSession.getPlayer().getTransactionArchive().getTransactions()) {

      HBox transactionsInRow = new HBox(10);

      Label transactionDate = new Label(String.valueOf(transaction.getWeek()));
      Label transactionBoughtSold = new Label(transaction.getClass().getSimpleName());
      Label transactionShare = new Label(transaction.getShare().getStock().getSymbol());
      Label transactionAmount = new Label(transaction.getShare().getPurchasePrice().toString());

      transactionsInRow.getChildren().addAll(
        transactionDate,
        transactionBoughtSold,
        transactionShare,
        transactionAmount
      );

      transactions.getChildren().add(transactionsInRow);
    }

  }
}
