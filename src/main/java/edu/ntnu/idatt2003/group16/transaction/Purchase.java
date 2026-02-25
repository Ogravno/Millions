package edu.ntnu.idatt2003.group16.transaction;

import edu.ntnu.idatt2003.group16.investment.Share;
import edu.ntnu.idatt2003.group16.player.Player;
import edu.ntnu.idatt2003.group16.transaction.calculator.PurchaseCalculator;

/**
 * Class representing a purchase.
 *
 * <p>Extends {@link Transaction}</p>
 *
 * @author Odin Grav
 */
public class Purchase extends Transaction {
  /**
   * Constructor for the {@link Purchase} class.
   *
   * @param share the share to be purchased
   * @param week the week of the purchase
   * @throws IllegalArgumentException when share is null
   */
  public Purchase(Share share, int week) {
    super(share, week, new PurchaseCalculator(share));
  }

  @Override
  public void commit(Player player) {
    // TODO: Implement commit method
  }
}
