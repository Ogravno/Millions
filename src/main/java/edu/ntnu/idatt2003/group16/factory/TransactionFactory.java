package edu.ntnu.idatt2003.group16.factory;

import edu.ntnu.idatt2003.group16.model.investment.Share;
import edu.ntnu.idatt2003.group16.model.transaction.Purchase;
import edu.ntnu.idatt2003.group16.model.transaction.Sale;

/**
 * Factory for creating transactions.
 */
public class TransactionFactory {

  /**
   * Creates a purchase transaction.
   *
   * @param share the share that are being purchased.
   * @param week the week the purchase is being made.
   * @return a new purchase transaction.
   */
  public Purchase createPurchase(Share share, int week) {
    return new Purchase(share, week);
  }

  /**
   * Creates a sale transaction.
   *
   * @param share the share that are being sold.
   * @param week the week the sale is being made.
   * @return a new sale transaction.
   */
  public Sale createSale(Share share, int week) {
    return new Sale(share, week);
  }
}
