package edu.ntnu.idatt2003.group16.transaction;

import edu.ntnu.idatt2003.group16.investment.Share;
import edu.ntnu.idatt2003.group16.player.Player;
import edu.ntnu.idatt2003.group16.transaction.calculator.SaleCalculator;

/**
 * Class representing a sale.
 *
 * <p>Extends {@link Transaction}</p>
 *
 * @author Odin Grav
 */
public class Sale extends Transaction {
  /**
   * Constructor for the {@link Sale} class.
   *
   * @param share the share to be sold
   * @param week the week of the sale
   * @throws IllegalArgumentException when share is null
   */
  public Sale(Share share, int week) {
    super(share, week, new SaleCalculator(share));
  }

  @Override
  public void commit(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("player cannot be null");
    }
    if (committed) {
      return;
    }

    committed = true;
    player.addMoney(getCalculator().calculateTotal());
    player.getPortfolio().removeShare(getShare());
    player.getTransactionArchive().add(this);
  }
}
