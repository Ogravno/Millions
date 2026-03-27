package edu.ntnu.idatt2003.group16.investment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class StockTest {

  @Nested
  class StockTests {

    @Test
    void shouldCreateStock() {

      Stock stock = new Stock("AAPL", "Apple", new BigDecimal("127.53"));

      assertEquals("AAPL", stock.getSymbol());
      assertEquals("Apple", stock.getCompany());
      assertEquals(new BigDecimal("127.53"), stock.getCurrentPrice());
      assertEquals(1, stock.getHistoricalPrices().size());
    }

    @Test
    void shouldThrowIfSymbolIsNull() {
      assertThrows(IllegalArgumentException.class, () ->
          new Stock(null, "Apple", new BigDecimal("127.53")));;
    }

    @Test
    void shouldThrowIfSymbolIsBlank() {
      assertThrows(IllegalArgumentException.class, () ->
          new Stock(" ", "Apple", new BigDecimal("127.53")));;
    }

    @Test
    void shouldThrowIfCompanyIsNull() {
      assertThrows(IllegalArgumentException.class, () ->
          new Stock("AAPL", null, new BigDecimal("127.53")));;
    }

    @Test
    void shouldThrowIfCompanyIsBlank() {
      assertThrows(IllegalArgumentException.class, () ->
          new Stock("AAPL", " ", new BigDecimal("127.53")));;
    }

    @Test
    void shouldThrowIfSalesPriceIsNull() {
      assertThrows(IllegalArgumentException.class, () ->
          new Stock("AAPL", "Apple", null));;
    }

    @Test
    void shouldThrowIfSalesPriceIsEqualToZero() {
      assertThrows(IllegalArgumentException.class, () ->
          new Stock("AAPL", "Apple", new BigDecimal("0")));;
    }

    @Test
    void shouldThrowIfSalesPriceIsLessThanZero() {
      assertThrows(IllegalArgumentException.class, () ->
          new Stock("AAPL", "Apple", new BigDecimal("-1")));;
    }

  }

  @Nested
  class ChangeCurrentPrice {

    @Test
    void shouldChangePrice() {
      Stock stock = new Stock("AAPL", "Apple", new BigDecimal("123"));
      stock.changeCurrentPrice(new BigDecimal("345"));

      assertEquals(new BigDecimal("345"), stock.getCurrentPrice());
    }

    @Test
    void shouldThrowIfNewPriceIsNull() {
      Stock stock = new Stock("AAPL", "Apple", new BigDecimal("123"));
      assertThrows(IllegalArgumentException.class, () ->
          stock.changeCurrentPrice(null));
    }

    @Test
    void shouldThrowIfNewPriceIsZero() {
      Stock stock = new Stock("AAPL", "Apple", new BigDecimal("123"));
      assertThrows(IllegalArgumentException.class, () ->
          stock.changeCurrentPrice(new BigDecimal("0")));
    }

    @Test
    void shouldThrowIfNewPriceLessThanZero() {
      Stock stock = new Stock("AAPL", "Apple", new BigDecimal("123"));
      assertThrows(IllegalArgumentException.class, () ->
          stock.changeCurrentPrice(new BigDecimal("-1")));
    }
  }

  @Test
  void shouldGetHighestPrice() {
    Stock stock = new Stock("AAPL", "Apple", new BigDecimal("127.53"));
    stock.changeCurrentPrice(new BigDecimal("95"));
    stock.changeCurrentPrice(new BigDecimal("453"));
    stock.changeCurrentPrice(new BigDecimal("100"));

    assertEquals(new BigDecimal("453"), stock.getHighestPrice());
  }

  @Test
  void shouldGetLowestPrice() {
    Stock stock = new Stock("AAPL", "Apple", new BigDecimal("127.53"));
    stock.changeCurrentPrice(new BigDecimal("95"));
    stock.changeCurrentPrice(new BigDecimal("453"));
    stock.changeCurrentPrice(new BigDecimal("100"));

    assertEquals(new BigDecimal("95"), stock.getLowestPrice());
  }

  @Test
  void shouldGetLatestPriceChange() {
    Stock stock = new Stock("AAPL", "Apple", new BigDecimal("127.53"));
    stock.changeCurrentPrice(new BigDecimal("95"));

    assertEquals(new BigDecimal("-32.53"), stock.getLatestPriceChange());
  }

  @Test
  void shouldGetLastestPriceChangeAsZero() {
    Stock stock = new Stock("AAPL", "Apple", new BigDecimal("127.53"));

    assertEquals(new BigDecimal("0"), stock.getLatestPriceChange());
  }
}