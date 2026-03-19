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

  /**
   * Commits the purchase.
   *
   * <p>Withdraws money from the {@link Player player}, adds the share purchased to the
   * {@link Player player's} portfolio and adds the {@link Purchase purchase} to the
   * {@link Player player's} {@link Transaction transacton archive}.</p>
   *
   * @param player the player performing the purchase
   * @throws IllegalArgumentException when {@code player} is null
   */
  @Override
  public void commit(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("player cannot be null");
    }
    if (committed) {
      throw new IllegalStateException("Purchase already committed");
    }
    if (player.getMoney().compareTo(getCalculator().calculateTotal()) < 0) {
      throw new IllegalStateException("Player has insufficient funds");
    }

    committed = true;
    player.withdrawMoney(getCalculator().calculateTotal());
    player.getPortfolio().addShare(getShare());
    player.getTransactionArchive().add(this);
  }
}
