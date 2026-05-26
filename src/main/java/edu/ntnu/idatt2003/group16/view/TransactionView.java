package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.controller.AppController;
import edu.ntnu.idatt2003.group16.model.GameSession;
import edu.ntnu.idatt2003.group16.model.transaction.Transaction;
import edu.ntnu.idatt2003.group16.observer.GameObserver;
import edu.ntnu.idatt2003.group16.view.components.TransactionTable;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * View for displaying transactions
 */
public class TransactionView implements GameObserver {
  private final AppController appController;
  private final GameSession gameSession;

  private final VBox root;
  private final TransactionTable transactionTable;

  private boolean ascending = true;
  private String currentSortName = "";

  /**
   * Creates the transaction view.
   *
   * @param gameSession the active game session.
   */
  public TransactionView(AppController appController, GameSession gameSession) {
    if (appController == null) {
      throw new IllegalArgumentException("AppController cannot be null");
    }

    if (gameSession == null) {
      throw new IllegalArgumentException("GamesSession cannot be null");
    }

    this.appController = appController;
    this.gameSession = gameSession;

    this.root = new VBox(10);
    this.root.getStyleClass().add("content-container");

    Label headline = new Label("Transactions");
    headline.getStyleClass().add("headline");

    TextField transactionSearchField = new TextField();
    transactionSearchField.setPromptText("Search...");
    transactionSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
      drawTransactions(gameSession.getPlayer().getTransactionArchive().findTransactions(newValue));
    });
    transactionSearchField.getStyleClass().add("searchbar");

    Label sortLabel = new Label("Sort: ");
    sortLabel.getStyleClass().add("standard-text");

    ToggleGroup sortButtons = new ToggleGroup();
    sortButtons.selectedToggleProperty().addListener((obsVal, oldVal, newVal) -> {
      if (newVal == null) {
        oldVal.setSelected(true);
      }
    });

    ToggleButton sortWeek = new ToggleButton("Week");
    sortWeek.setToggleGroup(sortButtons);
    sortWeek.setOnAction(event ->
      sortAndDraw("week", Comparator.comparing(Transaction::getWeek))
    );
    sortWeek.setSelected(true);

    ToggleButton sortType = new ToggleButton("Type");
    sortType.setToggleGroup(sortButtons);
    sortType.setOnAction(event ->
      sortAndDraw("type", Comparator.comparing(
        transaction -> transaction.getClass().getSimpleName()
      ))
    );

    ToggleButton sortSymbol = new ToggleButton("Symbol");
    sortSymbol.setToggleGroup(sortButtons);
    sortSymbol.setOnAction(event ->
      sortAndDraw("symbol", Comparator.comparing(
        transaction -> transaction.getShare().getStock().getSymbol()
      ))
    );

    ToggleButton sortStocks = new ToggleButton("Amount of shares");
    sortStocks.setToggleGroup(sortButtons);
    sortStocks.setOnAction(event ->
      sortAndDraw("shares", Comparator.comparing(
        transaction -> transaction.getShare().getQuantity()
      ))
    );

    ToggleButton sortAmount = new ToggleButton("Stock value");
    sortAmount.setToggleGroup(sortButtons);
    sortAmount.setOnAction(event ->
        sortAndDraw("amount", Comparator.comparing(
          transaction -> transaction.getCalculator().calculateTotal()
        )));

    HBox sortContainer = new HBox();
    sortContainer.getChildren().addAll(
        sortLabel,
        sortWeek,
        sortType,
        sortSymbol,
        sortStocks,
        sortAmount
    );
    sortContainer.getStyleClass().addAll("sort-buttons");

    transactionTable = new TransactionTable(appController);

    VBox transactionContainer = new VBox(
        headline,
        transactionSearchField,
        sortContainer,
        transactionTable
    );
    transactionContainer.getStyleClass().add("tile");

    gameSession.addObserver(this);

    root.getChildren().addAll(
        transactionContainer
    );
  }

  public VBox getView() {
    return root;
  }

  private void drawTransactions(List<Transaction> transactions) {
    transactionTable.setEntries(transactions);
  }

  private void sortAndDraw(String sortName, Comparator<Transaction> comparator) {
    if (currentSortName.equals(sortName)) {
      ascending = !ascending;
    } else {
      currentSortName = sortName;
      ascending = true;
    }

    List<Transaction> sortedTransactions = gameSession.getPlayer()
      .getTransactionArchive()
      .getTransactions()
      .stream()
      .sorted(ascending ? comparator : comparator.reversed())
      .toList();

    drawTransactions(sortedTransactions);
  }

  @Override
  public void onGameStateChanged() {
    transactionTable.setEntries(gameSession.getPlayer().getTransactionArchive().getTransactions());
  }
}
