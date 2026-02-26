package edu.ntnu.idatt2003.group16.exchange;

import edu.ntnu.idatt2003.group16.investment.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExchangeTest {
  private Stock apple;
  private Stock tesla;
  private Exchange exchange;

  @BeforeEach
  void setUp() {
    apple = new Stock("AAPL", "Apple", BigDecimal.valueOf(150));
    tesla = new Stock("TSLA", "Tesla", BigDecimal.valueOf(135));
    exchange = new Exchange("NASDAQ", List.of(tesla, apple));
  }

  @Nested
  class ConstructorTests{

    @Test
    void constructor_shouldThrowIfNameIsNull() {
      List<Stock> stocks = List.of(tesla);

      assertThrows(IllegalArgumentException.class, () -> new Exchange(null, stocks));
    }

    @Test
    void constructor_shouldThrowNameIsBlank() {
      List<Stock> stocks = List.of(tesla);

      assertThrows(IllegalArgumentException.class, () -> new Exchange("  ", stocks));
    }

    @Test
    void constructor_shouldThrowStockIsNull() {
      assertThrows(IllegalArgumentException.class, () -> new Exchange("Test", null));
    }

    @Test
    void constructor_shouldCreateExchangeWithStocks() {

      assertTrue(exchange.hasStock("TSLA"));
      assertTrue(exchange.hasStock("AAPL"));
    }

    @Test
    void constructor_shouldThrowIfStockListContainsNull() {
      List<Stock> stocks = new ArrayList<>();
      stocks.add(tesla);
      stocks.add(null);

      assertThrows(IllegalArgumentException.class, () -> new Exchange("NASDAQ", stocks));
    }
  }

  @Nested
  class hasStockTests {

    @Test
    void hasStock_shouldThrowIfSymbolIsNull() {
      assertThrows(IllegalArgumentException.class, () -> exchange.hasStock(null));
    }

    @Test
    void hasStock_shouldThrowIfSymbolIsBlank() {
      assertThrows(IllegalArgumentException.class, () -> exchange.hasStock("  "));
    }

    @Test
    void hasStock_shouldReturnTrueIfStockExists() {
      boolean result = exchange.hasStock("AAPL");

      assertTrue(result);
    }

    @Test
    void hasStock_shouldReturnFalseIfStockDoesNotExist() {
      boolean result = exchange.hasStock("NPER");

      assertFalse(result);
    }
  }

  @Nested
  class getStockTests {

    @Test
    void getStock_shouldThrowIfSymbolIsNull() {
      assertThrows(IllegalArgumentException.class, () -> exchange.getStock(null));
    }

    @Test
    void getStock_shouldThrowIfSymbolIsBlank() {
      assertThrows(IllegalArgumentException.class, () -> exchange.getStock("  "));
    }

    @Test
    void getStock_shouldReturnCorrectStock() {
      Stock result = exchange.getStock("AAPL");
      assertEquals(apple, result);
    }

    @Test
    void getStock_shouldReturnNullIfStockDoesNotExist() {
      Stock result = exchange.getStock("PDFE");
      assertNull(result);
    }
  }
}