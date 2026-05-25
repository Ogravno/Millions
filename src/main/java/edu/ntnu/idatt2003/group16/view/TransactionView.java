package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.model.GameSession;
import edu.ntnu.idatt2003.group16.model.transaction.Transaction;
import edu.ntnu.idatt2003.group16.observer.GameObserver;
import edu.ntnu.idatt2003.group16.view.components.TransactionTable;
import java.net.URL;
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
  private final GameSession gameSession;

  private final VBox root;
  private final TransactionTable transactionTable;

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

    this.root = new VBox(10);
    this.root.getStyleClass().add("content-container");

    URL styleSheet = getClass().getResource("/css/transaction-view.css");
    if (styleSheet != null) {
      root.getStylesheets().add(styleSheet.toExternalForm());
    }

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

    ToggleButton sortWeek = new ToggleButton("Symbol");
    sortWeek.setToggleGroup(sortButtons);
    sortWeek.setSelected(true);

    ToggleButton sortType = new ToggleButton("Name");
    sortType.setToggleGroup(sortButtons);

    ToggleButton sortSymbol = new ToggleButton("Stock value");
    sortSymbol.setToggleGroup(sortButtons);

    ToggleButton sortStocks = new ToggleButton("Stock value");
    sortStocks.setToggleGroup(sortButtons);

    ToggleButton sortAmount = new ToggleButton("Stock value");
    sortAmount.setToggleGroup(sortButtons);

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

    transactionTable = new TransactionTable();

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

  @Override
  public void onGameStateChanged() {
    transactionTable.setEntries(gameSession.getPlayer().getTransactionArchive().getTransactions());
  }
}
