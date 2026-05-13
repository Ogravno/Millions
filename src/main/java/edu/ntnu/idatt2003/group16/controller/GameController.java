package edu.ntnu.idatt2003.group16.controller;

import edu.ntnu.idatt2003.group16.model.GameSession;
import edu.ntnu.idatt2003.group16.model.investment.Share;
import edu.ntnu.idatt2003.group16.model.player.Status;
import edu.ntnu.idatt2003.group16.model.transaction.Purchase;
import edu.ntnu.idatt2003.group16.model.transaction.Sale;

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

  public Sale sellShare(Share share) {
    return gameSession.sellShare(share);
  }

  public BigDecimal getPlayerMoney() {
    return gameSession.getPlayer().getMoney();
  }

  public BigDecimal getPlayerStartMoney() {
    return gameSession.getPlayer().getStartingMoney();
  }

  public int getWeek() {
    return gameSession.getExchange().getWeek();
  }

  public Status getStatus() {
    return gameSession.getPlayer().getStatus();
  }
}
