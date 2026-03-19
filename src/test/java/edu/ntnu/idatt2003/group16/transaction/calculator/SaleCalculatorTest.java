package edu.ntnu.idatt2003.group16.transaction.calculator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.ntnu.idatt2003.group16.investment.Share;
import edu.ntnu.idatt2003.group16.investment.Stock;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SaleCalculatorTest {
  private static Share share;
  private static BigDecimal expectedGrossValue;
  private static BigDecimal expectedCommission;
  private static BigDecimal expectedTax;
  private static BigDecimal expectedTotal;
  private static SaleCalculator saleCalculator;

  @BeforeEach
  void setUp() {
    BigDecimal purchasePrice = new BigDecimal("3.15");
    Stock stock = new Stock(
        "GOOG", "Alphabet Inc Class C", purchasePrice);

    BigDecimal quantity = new BigDecimal("3");
    share = new Share(stock, quantity, purchasePrice);

    BigDecimal salesPrice = stock.getCurrentPrice();

    expectedGrossValue = salesPrice.multiply(quantity);
    expectedCommission = expectedGrossValue.multiply(new BigDecimal("0.01"));
    expectedTax = expectedGrossValue.subtract(expectedCommission)
        .subtract(salesPrice.multiply(quantity)).multiply(BigDecimal.valueOf(0.3));
    expectedTotal = expectedGrossValue.subtract(expectedCommission).subtract(expectedTax);

    saleCalculator = new SaleCalculator(share);
  }

  @Test
  void constructorSuccessful() {
    assertDoesNotThrow(() -> new SaleCalculator(share));
  }

  @Test
  void constructorParameterIsNull() {
    assertThrows(IllegalArgumentException.class, () -> new SaleCalculator(null));
  }

  @Test
  void calculateGross() {
    assertEquals(expectedGrossValue, saleCalculator.calculateGross());
  }

  @Test
  void calculateCommission() {
    assertEquals(expectedCommission, saleCalculator.calculateCommission());
  }

  @Test
  void calculateTax() {
    assertEquals(expectedTax, saleCalculator.calculateTax());
  }

  @Test
  void calculateTotal() {
    assertEquals(expectedTotal, saleCalculator.calculateTotal());
  }
}