package edu.ntnu.idatt2003.group16.controller;

import edu.ntnu.idatt2003.group16.model.GameSession;
import edu.ntnu.idatt2003.group16.model.transaction.Purchase;

import java.math.BigDecimal;

/**
 * Controller that connects the GUI with the game logic.
 */
public class GameController {

  private final GameSession gameSession;

  public GameController(GameSession gameSession) {
    if (gameSession == null) {
      throw new IllegalArgumentException("GameSession cannot be null.");
    }
    this.gameSession = gameSession;
  }

  /**
   * Advances the game by one week.
   */
  public void advanceWeek() {
    gameSession.advanceWeek();
  }

  public Purchase buyStock(String symbol, BigDecimal quantity) {
    return gameSession.buyStock(symbol, quantity);
  }
}
