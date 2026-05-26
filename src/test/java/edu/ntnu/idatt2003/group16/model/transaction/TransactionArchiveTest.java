package edu.ntnu.idatt2003.group16.model.transaction;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idatt2003.group16.model.investment.Share;
import edu.ntnu.idatt2003.group16.model.investment.Stock;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import edu.ntnu.idatt2003.group16.model.transaction.Purchase;
import edu.ntnu.idatt2003.group16.model.transaction.Sale;
import edu.ntnu.idatt2003.group16.model.transaction.Transaction;
import edu.ntnu.idatt2003.group16.model.transaction.TransactionArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class TransactionArchiveTest {
  TransactionArchive transactionArchive;
  List<Purchase> purchases;
  List<Sale> sales;

  @BeforeEach
  void setUp() {
    transactionArchive = new TransactionArchive();
    purchases = new ArrayList<>();
    sales = new ArrayList<>();

    BigDecimal purchasePrice = new BigDecimal("3.15");
    Stock stock = new Stock(
        "GOOG", "Alphabet Inc Class C", purchasePrice);
    stock.changeCurrentPrice(new BigDecimal("3.25"));
    stock.changeCurrentPrice(new BigDecimal("3.30"));
    stock.changeCurrentPrice(new BigDecimal("3.33"));
    BigDecimal quantity = new BigDecimal("3");

    Share share1 = new Share(stock, quantity, purchasePrice);
    purchases.add(new Purchase(share1, 1));
    sales.add(new Sale(share1, 2));

    Share share2 = new Share(stock, quantity, purchasePrice);
    purchases.add(new Purchase(share2, 2));
    sales.add(new Sale(share2, 3));

    Share share3 = new Share(stock, quantity, purchasePrice);
    purchases.add(new Purchase(share3, 1));
    sales.add(new Sale(share3, 3));
  }

  @Test
  void constructorSuccessful() {
    assertDoesNotThrow(() -> new TransactionArchive());
  }

  @Nested
  class AddTests {
    @Test
    void addTransactionIsNull() {
      assertThrows(IllegalArgumentException.class, () -> transactionArchive.add(null));
    }

    @Test
    void addTransactionSuccess() {
      transactionArchive.add(purchases.getFirst());
      assertTrue(transactionArchive.getTransactions().contains(purchases.getFirst()));
    }

    @Test
    void addTransactionAlreadyInArchive() {
      transactionArchive.add(purchases.get(0));
      assertThrows(IllegalArgumentException.class, () -> transactionArchive.add(purchases.get(0)));
    }
  }

  @Nested
  class GetTransactionTests {
    @Test
    void getTransactionsWeekIsNegative() {
      assertThrows(IllegalArgumentException.class, () ->
          transactionArchive.getTransactions(-1));
    }

    @Test
    void getTransactionsWeekIsZero() {
      assertThrows(IllegalArgumentException.class, () ->
          transactionArchive.getTransactions(0));
    }

    @Test
    void getTransactionsReturnsTransactions() {
      List<Transaction> expectedResult = new ArrayList<>();
      expectedResult.add(sales.get(0));
      expectedResult.add(purchases.get(1));

      purchases.forEach(purchase -> transactionArchive.add(purchase));
      sales.forEach(sale -> transactionArchive.add(sale));
      List<Transaction> transactionsWeek2 = transactionArchive.getTransactions(2);

      assertTrue(transactionsWeek2.containsAll(expectedResult));
      assertEquals(transactionsWeek2.size(), expectedResult.size());
    }
  }

  @Nested
  class GetPurchasesTests {
    @Test
    void getPurchasesWeekIsNegative() {
      assertThrows(IllegalArgumentException.class, () ->
          transactionArchive.getPurchases(-1));
    }

    @Test
    void getPurchasesWeekIsZero() {
      assertThrows(IllegalArgumentException.class, () ->
          transactionArchive.getPurchases(0));
    }

    @Test
    void getPurchasesReturnsPurchases() {
      List<Transaction> expectedResult = new ArrayList<>();
      expectedResult.add(purchases.get(0));
      expectedResult.add(purchases.get(2));

      purchases.forEach(purchase -> transactionArchive.add(purchase));
      List<Transaction> purchasesWeek1 = transactionArchive.getPurchases(1);

      assertTrue(purchasesWeek1.containsAll(expectedResult));
      assertEquals(purchasesWeek1.size(), expectedResult.size());
    }
  }

  @Nested
  class GetSalesTests {
    @Test
    void getSalesWeekIsNegative() {
      assertThrows(IllegalArgumentException.class, () ->
          transactionArchive.getSales(-1));
    }

    @Test
    void getSalesWeekIsZero() {
      assertThrows(IllegalArgumentException.class, () ->
          transactionArchive.getSales(0));
    }

    @Test
    void getSalesReturnsSales() {
      List<Transaction> expectedResult = new ArrayList<>();
      expectedResult.add(sales.get(1));
      expectedResult.add(sales.get(2));

      sales.forEach(sale -> transactionArchive.add(sale));
      List<Transaction> salesWeek1 = transactionArchive.getSales(3);

      assertTrue(salesWeek1.containsAll(expectedResult));
      assertEquals(salesWeek1.size(), expectedResult.size());
    }
  }

  @Nested
  class IsEmptyTests {
    @Test
    void isEmptyArchiveEmpty() {
      assertTrue(transactionArchive.isEmpty());
    }

    @Test
    void isEmptyArchiveNotEmpty() {
      transactionArchive.add(purchases.getFirst());
      assertFalse(transactionArchive.isEmpty());
    }
  }

  @Test
  void countDistinctWeeks() {
    purchases.forEach(purchase -> transactionArchive.add(purchase));
    sales.forEach(sale -> transactionArchive.add(sale));

    assertEquals(3, transactionArchive.countDistinctWeeks());
  }

  @Nested
  class FindTransactionsTests {
    @Test
    void findTransactionsSearchTermIsNull() {
      assertThrows(IllegalArgumentException.class, () ->
        transactionArchive.findTransactions(null));
    }

    @Test
    void findTransactionsSearchTermIsBlank() {
      assertThrows(IllegalArgumentException.class, () ->
        transactionArchive.findTransactions(" "));
    }

    @Test
    void findTransactionsReturnsTransactionsByWeek() {
      purchases.forEach(purchase -> transactionArchive.add(purchase));
      sales.forEach(sale -> transactionArchive.add(sale));

      List<Transaction> result = transactionArchive.findTransactions("1");

      assertTrue(result.contains(purchases.get(0)));
      assertTrue(result.contains(purchases.get(2)));
      assertEquals(2, result.size());
    }

    @Test
    void findTransactionsReturnsTransactionsByType() {
      purchases.forEach(purchase -> transactionArchive.add(purchase));
      sales.forEach(sale -> transactionArchive.add(sale));

      List<Transaction> result = transactionArchive.findTransactions("sale");

      assertTrue(result.containsAll(sales));
      assertEquals(sales.size(), result.size());
    }

    @Test
    void findTransactionsReturnsTransactionsBySymbol() {
      purchases.forEach(purchase -> transactionArchive.add(purchase));
      sales.forEach(sale -> transactionArchive.add(sale));

      List<Transaction> result = transactionArchive.findTransactions("goog");

      assertTrue(result.containsAll(purchases));
      assertTrue(result.containsAll(sales));
      assertEquals(6, result.size());
    }

    @Test
    void findTransactionsReturnsEmptyListWhenNoMatch() {
      purchases.forEach(purchase -> transactionArchive.add(purchase));
      sales.forEach(sale -> transactionArchive.add(sale));

      List<Transaction> result = transactionArchive.findTransactions("aapl");

      assertTrue(result.isEmpty());
    }
  }
}