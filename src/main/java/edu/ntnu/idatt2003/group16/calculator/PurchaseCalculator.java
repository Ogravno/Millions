package edu.ntnu.idatt2003.group16.calculator;

import edu.ntnu.idatt2003.group16.investment.Share;
import java.math.BigDecimal;

/**
 * Class for the calculator used for purchase transactions.
 *
 * @author Odin Grav
 */
public class PurchaseCalculator implements TransactionCalculator {
  private BigDecimal purchasePrice;
  private BigDecimal quantity;

  /**
   * Constructor for the PurchaseCalculator class.
   *
   * <p>share must not be null</p>
   *
   * @param share the share to be purchase
   * @throws IllegalArgumentException when share is null
   */
  PurchaseCalculator(Share share) {
    if (share == null) {
      throw new IllegalArgumentException("Share must not be null");
    }

    this.purchasePrice == share.getPurchasePrice();
    this.quantity == share.getQuantity();
  }

  /**
   * Method to calculate the gross value.
   *
   * <p>The gross value is the purchase price multiplied by the quantity</p>
   *
   * @return the gross value
   */
  @Override
  public BigDecimal calculateGross() {
    return purchasePrice.multiply(quantity);
  }

  /**
   * Method to calculate the commission.
   *
   * <p>The commission is 0.5% of the gross value</p>
   *
   * @return the commission
   */
  @Override
  public BigDecimal calculateCommission() {
    return this.calculateGross().multiply(BigDecimal.valueOf(0.005));
  }

  /**
   * Method to calculate the tax.
   *
   * <p>No tax on purchases</p>
   *
   * @return the tax
   */
  @Override
  public BigDecimal calculateTax() {
    return BigDecimal.valueOf(0);
  }

  /**
   * Method to calculate the total price of the purchase.
   *
   * <p>The total price is the gross value + commission + tax</p>
   *
   * @return the total price of the purchase
   */
  @Override
  public BigDecimal calculateTotal() {
    return this.calculateGross().add(this.calculateCommission()).add(this.calculateTax());
  }
}
