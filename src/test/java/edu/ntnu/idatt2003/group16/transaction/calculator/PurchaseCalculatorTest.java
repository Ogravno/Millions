package edu.ntnu.idatt2003.group16.transaction.calculator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.ntnu.idatt2003.group16.investment.Share;
import edu.ntnu.idatt2003.group16.investment.Stock;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseCalculatorTest {
  private static Share share;
  private static BigDecimal expectedGrossValue;
  private static BigDecimal expectedCommission;
  private static BigDecimal expectedTax;
  private static BigDecimal expectedTotal;
  private static PurchaseCalculator purchaseCalculator;

  @BeforeAll
  static void setUp() {
    BigDecimal purchasePrice = new BigDecimal("3.15");
    Stock stock = new Stock(
        "GOOG", "Alphabet Inc Class C", purchasePrice);

    BigDecimal quantity = new BigDecimal("3");
    share = new Share(stock, quantity, purchasePrice);

    purchaseCalculator = new PurchaseCalculator(share);

    expectedGrossValue = purchasePrice.multiply(quantity);
    expectedCommission = expectedGrossValue.multiply(new BigDecimal("0.005"));
    expectedTax = new BigDecimal("0");
    expectedTotal = expectedGrossValue.add(expectedCommission).add(expectedTax);
  }

  @Test
  void constructorTest() {
    assertDoesNotThrow(() -> new PurchaseCalculator(share));
    assertThrows(IllegalArgumentException.class, () -> new PurchaseCalculator(null));
  }

  @Test
  void calculateGross() {
    assertEquals(expectedGrossValue, purchaseCalculator.calculateGross());
  }

  @Test
  void calculateCommission() {
    assertEquals(expectedCommission, purchaseCalculator.calculateCommission());
  }

  @Test
  void calculateTax() {
    assertEquals(expectedTax, purchaseCalculator.calculateTax());
  }

  @Test
  void calculateTotal() {
    assertEquals(expectedTotal, purchaseCalculator.calculateTotal());
  }
}