package edu.ntnu.idatt2003.group16.investment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idatt2003.group16.transaction.calculator.SaleCalculator;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PortfolioTest {
  Portfolio portfolio;
  Share googleShare;
  Share googleShare2;
  Share appleShare;

  @BeforeEach
  void setUp() {
    portfolio = new Portfolio();

    BigDecimal googlePurchasePrice = new BigDecimal("3.15");
    Stock googleStock = new Stock(
        "GOOG", "Alphabet Inc Class C", googlePurchasePrice);
    BigDecimal googleQuantity = new BigDecimal("3");
    googleShare = new Share(googleStock, googleQuantity, googlePurchasePrice);
    googleShare2 = new Share(googleStock, new BigDecimal("2"), googlePurchasePrice);

    BigDecimal applePurchasePrice = new BigDecimal("251.38");
    Stock appleStock = new Stock(
        "AAPL", "Alphabet Inc Class C", applePurchasePrice);
    BigDecimal appleQuantity = new BigDecimal("2");
    appleShare = new Share(appleStock, appleQuantity, applePurchasePrice);
  }

  @Test
  void getNetWorth() {
    BigDecimal expectedResult = BigDecimal.ZERO;
    SaleCalculator googleCalculator = new SaleCalculator(googleShare);
    expectedResult = expectedResult.add(googleCalculator.calculateTotal());

    SaleCalculator googleCalculator2 = new SaleCalculator(googleShare2);
    expectedResult = expectedResult.add(googleCalculator2.calculateTotal());

    SaleCalculator appleCalculator = new SaleCalculator(appleShare);
    expectedResult = expectedResult.add(appleCalculator.calculateTotal());

    portfolio.addShare(googleShare);
    portfolio.addShare(googleShare2);
    portfolio.addShare(appleShare);

    assertEquals(expectedResult, portfolio.getNetWorth());
  }

  @Nested
  class AddShareTests {
    @Test
    void addShareShareNull() {
      assertThrows(IllegalArgumentException.class, () ->
          portfolio.addShare(null));
    }

    @Test
    void addShareAddsShare() {
      assertTrue(portfolio.addShare(googleShare));
      assertTrue(portfolio.getShares().contains(googleShare));
    }

    @Test
    void addShareShareAlreadyAdded() {
      portfolio.addShare(googleShare);
      assertThrows(IllegalArgumentException.class, () ->
          portfolio.addShare(googleShare));
    }
  }

  @Nested
  class RemoveShareTests {
    @Test
    void removeShareShareNull() {
      assertThrows(IllegalArgumentException.class, () ->
          portfolio.removeShare(null));
    }

    @Test
    void removeShareRemovesShare() {
      portfolio.addShare(googleShare);
      assertTrue(portfolio.getShares().contains(googleShare));

      portfolio.removeShare(googleShare);
      assertFalse(portfolio.getShares().contains(googleShare));
    }

    @Test
    void removeShareShareNotInPortfolio() {
      assertThrows(IllegalArgumentException.class, () ->
          portfolio.removeShare(googleShare));
    }
  }

  @Nested
  class GetSharesTests {
    @Test
    void getSharesSymbolNull() {
      assertThrows(IllegalArgumentException.class, () ->
          portfolio.getShares(null));
    }

    @Test
    void getSharesSymbolBlank() {
      assertThrows(IllegalArgumentException.class, () ->
          portfolio.getShares(""));
    }

    @Test
    void getSharesGetsShares() {
      List<Share> expectedResult = new ArrayList<>();
      expectedResult.add(googleShare);
      expectedResult.add(googleShare2);

      portfolio.addShare(googleShare);
      portfolio.addShare(googleShare2);
      portfolio.addShare(appleShare);

      assertEquals(expectedResult, portfolio.getShares("GOOG"));
    }
  }

  @Nested
  class ContainsTests {
    @Test
    void containsShareNull() {
      assertThrows(IllegalArgumentException.class, () ->
          portfolio.contains(null));
    }

    @Test
    void containsShareInPortfolio() {
      portfolio.addShare(googleShare);
      assertTrue(portfolio.contains(googleShare));
    }

    @Test
    void containsShareNotInPortfolio() {
      assertFalse(portfolio.contains(googleShare));
    }
  }
}