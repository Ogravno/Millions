package edu.ntnu.idatt2003.group16.model.transaction.calculator;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.math.BigDecimal;

/**
 * Interface for transaction calculators.
 *
 * @author Odin Grav
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = PurchaseCalculator.class, name = "PurchaseCalculator"),
    @JsonSubTypes.Type(value = SaleCalculator.class, name = "SaleCalculator")
})
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
