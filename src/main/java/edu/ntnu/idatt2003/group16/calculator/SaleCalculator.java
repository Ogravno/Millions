package edu.ntnu.idatt2003.group16.calculator;

import edu.ntnu.idatt2003.group16.investment.Share;
import java.math.BigDecimal;

/**
 * Class for the calculator used for sale transactions.
 *
 * @author Odin Grav
 */
public class SaleCalculator implements TransactionCalculator {
  private BigDecimal purchasePrice;
  private BigDecimal salesPrice;
  private BigDecimal quantity;

  /**
   * Constructor for the SaleCalculator class.
   *
   * @param share the share to be sold. Must not be null
   * @throws IllegalArgumentException when share is null
   */
  SaleCalculator(Share share) {
    if (share == null) {
      throw new IllegalArgumentException("Share must not be null");
    }

    this.purchasePrice == share.getPurchasePrice();
    this.salesPrice == share.getStock.getSalesPrice;
    this.quantity == share.getQuantity;
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
    return this.salesPrice.multiply(this.quantity);
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
    return this.calculateGross().multiply(BigDecimal.valueOf(0.01));
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
    BigDecimal purchaseExpenditures = this.purchasePrice.multiply(this.quantity);
    BigDecimal profit = this.calculateGross().subtract(this.calculateCommission())
        .subtract(purchaseExpenditures);

    return profit.multiply(BigDecimal.valueOf(0.3));
  }

  /**
   * Method to calculate the total sale value.
   *
   * <p>The sales value is the gross value minus the commission minus the tax</p>
   *
   * @return the total sale value
   */
  @Override
  public BigDecimal calculateTotal() {
    return this.calculateGross().subtract(this.calculateCommission()).subtract(this.calculateTax());
  }
}
