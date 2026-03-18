package edu.ntnu.idatt2003.group16.transaction.calculator;

import edu.ntnu.idatt2003.group16.investment.Share;
import java.math.BigDecimal;

/**
 * Class for the calculator used for sale transactions.
 *
 * <p>Implements {@link TransactionCalculator}</p>
 *
 * @author Odin Grav
 */
public class SaleCalculator implements TransactionCalculator {
  private BigDecimal purchasePrice;
  private BigDecimal salesPrice;
  private BigDecimal quantity;

  /**
   * Constructor for the {@link SaleCalculator} class.
   *
   * @param share the share to be sold. Must not be null
   * @throws IllegalArgumentException when share is null
   */
  public SaleCalculator(Share share) {
    if (share == null) {
      throw new IllegalArgumentException("Share must not be null");
    }

    this.purchasePrice = share.getPurchasePrice();
    this.salesPrice = share.getStock().getCurrentPrice();
    this.quantity = share.getQuantity();
  }

  /**
   * Method to calculate the gross value.
   *
   * <p>The gross value is the sales price multiplied by the quantity</p>
   *
   * @return the gross value
   */
  @Override
  public BigDecimal calculateGross() {
    return salesPrice.multiply(quantity);
  }

  /**
   * Method to calculate the commission.
   *
   * <p>The commission is 1% of the gross value</p>
   *
   * @return the commission
   */
  @Override
  public BigDecimal calculateCommission() {
    return calculateGross().multiply(BigDecimal.valueOf(0.01));
  }

  /**
   * Method to calculate the tax.
   *
   * <p>The tax is 30% of the profit of the sale. The profit is the gross value minus the commission
   * minus the purchase expenditures. The purchase expenditures is simplified to the purchase price
   * multiplied by the quantity.</p>
   *
   * @return the tax
   */
  @Override
  public BigDecimal calculateTax() {
    BigDecimal purchaseExpenditures = purchasePrice.multiply(quantity);
    BigDecimal profit = calculateGross().subtract(calculateCommission())
        .subtract(purchaseExpenditures);

    return profit.multiply(BigDecimal.valueOf(0.3));
  }

  /**
   * Method to calculate the total sale value.
   *
   * <p>The total sale value is the gross value minus the commission minus the tax</p>
   *
   * @return the total sale value
   */
  @Override
  public BigDecimal calculateTotal() {
    return calculateGross().subtract(calculateCommission()).subtract(calculateTax());
  }
}
