package edu.ntnu.idatt2003.group16.transaction.calculator;

import java.math.BigDecimal;

/**
 * Interface for transaction calculators.
 *
 * @author Odin Grav
 */
public interface TransactionCalculator {
  /**
   * Method to calculate the gross value.
   *
   * @return the gross value
   */
  BigDecimal calculateGross();

  /**
   * Method to calculate the commission of the transaction.
   *
   * @return the commission
   */
  BigDecimal calculateCommission();

  /**
   * Method to calculate the tax.
   *
   * @return the tax
   */
  BigDecimal calculateTax();

  /**
   * Method to calculate the total value.
   *
   * @return the total value
   */
  BigDecimal calculateTotal();
}
