package edu.ntnu.idatt2003.group16.controller;

import edu.ntnu.idatt2003.group16.model.GameSession;
import edu.ntnu.idatt2003.group16.model.investment.Share;
import edu.ntnu.idatt2003.group16.model.player.Status;
import edu.ntnu.idatt2003.group16.model.transaction.Purchase;
import edu.ntnu.idatt2003.group16.model.transaction.Sale;
import java.math.BigDecimal;

/**
 * Controller that connects the graphical user interface
 * with the game logic and active game session.
 */
public class GameController {

  private final GameSession gameSession;

  /**
   * Creates a game controller for the active game session.
   *
   * @param gameSession the active game session for the controller
   * @throws IllegalArgumentException if gameSession is null
   */
  public GameController(GameSession gameSession) {
    if (gameSession == null) {
      throw new IllegalArgumentException("GameSession cannot be null.");
    }
    this.gameSession = gameSession;
  }

  /**
   * Advances the game by one week.
   *
   * <p>This updates the stock market and progresses
   * the game state forward.</p>
   */
  public void advanceWeek() {
    gameSession.advanceWeek();
  }

  /**
   * Buys a quantity of a stock from the exchange.
   *
   * @param symbol the stock symbol of the stock to buy
   * @param quantity the quantity of shares to buy
   * @return the completed purchase transaction
   */
  public Purchase buyStock(String symbol, BigDecimal quantity) {
    return gameSession.buyStock(symbol, quantity);
  }

  /**
   * Sells a share owned by the player.
   *
   * @param share the share to sell
   * @return the completed sale transaction
   */
  public Sale sellShare(Share share) {
    return gameSession.sellShare(share);
  }

  public BigDecimal getPlayerMoney() {
    return gameSession.getPlayer().getMoney();
  }

  /**
   * Gets the player's money as a formatted string with 2 decimals.
   *
   * <p>Used for GUI display only.</p>
   *
   * @return the player's money formatted to 2 decimals
   */
  public String getPlayerFormattedMoney() {
    return gameSession.getPlayer().getFormattedMoney();
  }

  public BigDecimal getPlayerStartMoney() {
    return gameSession.getPlayer().getStartingMoney();
  }

  public int getWeek() {
    return gameSession.getExchange().getWeek();
  }

  public Status getStatus() {
    return gameSession.getPlayer().getStatus(gameSession.getExchange().getWeek());
  }

  public BigDecimal getNetWorth() {
    return gameSession.getPlayer().getNetWorth();
  }

  /**
   * Gets the player's net worth as a formatted string with 2 decimals.
   *
   * <p>Used for GUI display only.</p>
   *
   * @return the player's net worth formatted to 2 decimals
   */
  public String getFormattedNetWorth() {
    return gameSession.getPlayer().getFormattedNetWorth();
  }

  public GameSession getGameSession() {
    return gameSession;
  }
}
