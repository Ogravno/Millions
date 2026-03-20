package edu.ntnu.idatt2003.group16.transaction;

import edu.ntnu.idatt2003.group16.investment.Share;
import edu.ntnu.idatt2003.group16.player.Player;
import edu.ntnu.idatt2003.group16.transaction.calculator.TransactionCalculator;

/**
 * Class representing a transaction.
 *
 * @author Odin Grav
 */
public abstract class Transaction {
  private Share share;
  private int week;
  private TransactionCalculator calculator;
  protected boolean committed;

  /**
   * Constructor for {@link Transaction} class.
   *
   * @param share the share involved in the transaction
   * @param week the week the transaction happens
   * @param calculator the calculator used for the transaction
   * @throws IllegalArgumentException when share or calculator are null
   * @throws IllegalArgumentException when week is not a positive integer.
   */
  protected Transaction(Share share, int week, TransactionCalculator calculator) {
    if (share == null || calculator == null) {
      throw new IllegalArgumentException("share and calculator cannot be null");
    }

    if (week <= 0) {
      throw new IllegalArgumentException("Week must be a positive integer");
    }

    this.share = share;
    this.week = week;
    this.calculator = calculator;
    this.committed = false;
  }

  public Share getShare() {
    return share;
  }

  public int getWeek() {
    return week;
  }

  public TransactionCalculator getCalculator() {
    return calculator;
  }

  public boolean isCommitted() {
    return committed;
  }

  /**
   * Performs the transaction.
   *
   * @param player the player performing the transaction
   */
  public void commit(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("player cannot be null");
    }
  }
}
