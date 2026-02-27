package edu.ntnu.idatt2003.group16.transaction.calculator;

import edu.ntnu.idatt2003.group16.investment.Share;
import edu.ntnu.idatt2003.group16.investment.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
class PurchaseCalculatorTest {

  @BeforeEach
  void setUp() {
    }

  @Test
  void constructorTest() {
    Stock testStock = new Stock(
        "GOOG", "Alphabet Inc Class C", new BigDecimal("3.15"));
    BigDecimal quantity = new BigDecimal("1");

    Share testShare = new Share(testStock, quantity, testStock.getSalesPrice().multiply(quantity));

    assertDoesNotThrow(() -> new PurchaseCalculator(testShare));
    assertThrows(IllegalArgumentException.class, () -> new PurchaseCalculator(null));
  }

  @Test
  void calculateGross() {
    }

  @Test
  void calculateCommission() {
    }

  @Test
  void calculateTax() {
    }

  @Test
  void calculateTotal() {
    }
}