package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.controller.GameController;
import edu.ntnu.idatt2003.group16.model.GameSession;
import edu.ntnu.idatt2003.group16.observer.GameObserver;
import edu.ntnu.idatt2003.group16.view.components.ExchangeStocks;
import edu.ntnu.idatt2003.group16.view.components.Leaderboard;
import java.net.URL;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * View for displaying exchange information.
 *
 * @author Robin Strand Prestmo
 */
public class ExchangeGameView implements GameObserver {

  private final GameSession gameSession;
  private final GameController gameController;

  private final ExchangeStocks stocksContainer;

  private final Leaderboard winnersLeaderboard;
  private final Leaderboard losersLeaderboard;

  private final BorderPane root;

  /**
   * Creates the exchange view.
   *
   * @param gameSession the active game session
   */
  public ExchangeGameView(GameSession gameSession, GameController gameController) {
    if (gameSession == null) {
      throw new IllegalArgumentException("GameSession cannot be null.");
    }
    if (gameController == null) {
      throw new IllegalArgumentException("GameController cannot be null.");
    }

    this.gameSession = gameSession;
    this.gameController = gameController;

    this.root = new BorderPane();
    this.root.getStyleClass().add("content-container");

    URL styleSheet = getClass().getResource("/css/exchange-game-view.css");
    if (styleSheet != null) {
      root.getStylesheets().add(styleSheet.toExternalForm());
    }

    stocksContainer = new ExchangeStocks(gameSession, gameController);

    winnersLeaderboard = new Leaderboard("Winners");
    winnersLeaderboard.getStyleClass().add("tile");

    losersLeaderboard = new Leaderboard("Losers");
    losersLeaderboard.getStyleClass().add("tile");


    VBox leaderboards = new VBox(
        winnersLeaderboard,
        losersLeaderboard
    );

    root.setCenter(stocksContainer);
    root.setRight(leaderboards);

    updateWinners();
    updateLosers();
  }

  /**
   * Returns the root node for this view.
   *
   * @return the root layout
   */
  public BorderPane getView() {
    return root;
  }

  /**
   * Updates the winners table.
   */
  private void updateWinners() {
    winnersLeaderboard.setEntries(gameSession.getExchange().getGainers(5));
  }

  /**
   * Updates the losers table.
   */
  private void updateLosers() {
    losersLeaderboard.setEntries(gameSession.getExchange().getLosers(5));
  }

  @Override
  public void onGameStateChanged() {
    updateWinners();
    updateLosers();
  }
}