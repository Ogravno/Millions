package edu.ntnu.idatt2003.group16.exchange;

import edu.ntnu.idatt2003.group16.investment.Share;
import edu.ntnu.idatt2003.group16.investment.Stock;
import edu.ntnu.idatt2003.group16.player.Player;
import edu.ntnu.idatt2003.group16.transaction.Sale;
import edu.ntnu.idatt2003.group16.transaction.Transaction;
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
  private Player player;
  private Stock norwegian;

  @BeforeEach
  void setUp() {
    apple = new Stock("AAPL", "Apple", BigDecimal.valueOf(150));
    tesla = new Stock("TSLA", "Tesla", BigDecimal.valueOf(135));
    norwegian = new Stock("NAS", "Norwegian", BigDecimal.valueOf(27));
    exchange = new Exchange("NASDAQ", List.of(tesla, apple, norwegian));
    player = new Player("Hans", new BigDecimal("1000"));
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

  @Nested
  class findStocksTests {

    @Test
    void findStocks_shouldThrowIfSearchTermIsNull() {
      assertThrows(IllegalArgumentException.class, () -> exchange.findStocks(null));
    }

    @Test
    void findStocks_shouldThrowIfSearchTermIsBlank() {
      assertThrows(IllegalArgumentException.class, () -> exchange.findStocks(" "));
    }

    @Test
    void findStocks_shouldReturnCorrectStock() {
      List<Stock> result = exchange.findStocks("apl");

      assertEquals(1, result.size());
      assertTrue(result.contains(apple));
    }

    @Test
    void findStocks_shouldReturnCorrectStocks() {
      List<Stock> result = exchange.findStocks("l");

      assertEquals(2, result.size());
      assertTrue(result.contains(apple));
      assertTrue(result.contains(tesla));
    }

    @Test
    void findStocks_shouldReturnEmptyList() {
      List<Stock> result = exchange.findStocks("Windows");

      assertTrue(result.isEmpty());
    }
  }

  @Nested
  class buyTests{
    @Test
    void shouldWithdrawMoneyWhenBuyingShares() {
      exchange.buy("AAPL", new BigDecimal("2"), player);

      BigDecimal expectedResult = new BigDecimal("1000")
        .subtract(apple.getCurrentPrice()
          .multiply(new BigDecimal("1.05"))
            .multiply(new BigDecimal("2")));

      assertEquals(expectedResult, player.getMoney());
    }

    @Test
    void shouldReturnTransactionWhenPurchaseIsSuccessful() {
      Transaction transaction = exchange.buy("AAPL", new BigDecimal("1"), player);

      assertNotNull(transaction);
    }

    @Test
    void shouldAddShareToPortfolioWhenPurchaseIsSuccessful() {
      exchange.buy("AAPL", new BigDecimal("2"), player);

      List<Share> appleShares = player.getPortfolio().getShares("AAPL");
      Share share = appleShares.getFirst();

      assertEquals(1, player.getPortfolio().getShares("AAPL").size());
      assertEquals(0, new BigDecimal("2").compareTo(share.getQuantity()));
    }

    @Test
    void shouldThrowIfSymbolIsNull() {
      assertThrows(IllegalArgumentException.class, () ->
        exchange.buy(null, new BigDecimal("1"), player));
    }

    @Test
    void shouldThrowIfSymbolIsBlank() {
      assertThrows(IllegalArgumentException.class, () ->
        exchange.buy(" ", new BigDecimal("1"), player));
    }

    @Test
    void shouldThrowIfQuantityIsNull() {
      assertThrows(IllegalArgumentException.class, () ->
        exchange.buy("AAPL", null, player));
    }

    @Test
    void shouldThrowIfQuantityIsBlank() {
      assertThrows(IllegalArgumentException.class, () ->
        exchange.buy("AAPL", new BigDecimal(" "), player));
    }

    @Test
    void shouldThrowIfQuantityIsZero() {
      assertThrows(IllegalArgumentException.class, () ->
        exchange.buy("AAPL", new BigDecimal("0"), player));
    }

    @Test
    void shouldThrowIfQuantityIsLessThanZero() {
      assertThrows(IllegalArgumentException.class, () ->
        exchange.buy("AAPL", new BigDecimal("-1"), player));
    }

    @Test
    void shouldThrowIfPlayerIsNull() {
      assertThrows(IllegalArgumentException.class, () ->
        exchange.buy("AAPL", new BigDecimal("1"), null));
    }

    @Test
    void shouldThrowIfStockIsNotInExchange() {
      assertThrows(IllegalArgumentException.class, () ->
        exchange.buy("APPL", new BigDecimal("1"), player));
    }

    @Test
    void shouldThrowIfPlayerCannotAffordThePurchase() {
      assertThrows(IllegalArgumentException.class, () ->
        exchange.buy("AAPL", new BigDecimal("100"), player));
    }
  }

  @Nested
  class sellTests{

    @BeforeEach
    void setUp() {
      exchange.buy("AAPL", new BigDecimal("2"), player);
    }

    @Test
    void shouldSellShare() {
      exchange.sell(player.getPortfolio().getShares("AAPL").getFirst(),player);

      assertEquals(0, player.getPortfolio().getShares().size());
    }

    @Test
    void shouldThrowIfShareIsNull() {
      assertThrows(IllegalArgumentException.class, () ->
        exchange.sell(null,player));
    }

    @Test
    void shouldThrowIfPlayerIsNull() {
      assertThrows(IllegalArgumentException.class, () ->
        exchange.sell(player.getPortfolio().getShares("AAPL").getFirst(),null));
    }

    @Test
    void shouldReturnTransaction() {
      Transaction transaction = exchange.sell(player.getPortfolio().getShares("AAPL").getFirst(),player);

      assertNotNull(transaction);
      assertInstanceOf(Sale.class, transaction);
    }

    @Test
    void shouldReturnTransactionWithCorrectShare() {
      Share share = player.getPortfolio().getShares("AAPL").getFirst();

      Sale sale = (Sale) exchange.sell(share,player);

      assertEquals(share, sale.getShare());
    }

    @Test
    void shouldIncreasePlayersBalanceWhenSelling() {
      Share share = player.getPortfolio().getShares("AAPL").getFirst();
      BigDecimal balanceBeforeSale = player.getMoney();

      exchange.sell(share, player);

      assertTrue(player.getMoney().compareTo(balanceBeforeSale) > 0);
    }

  }

  @Nested
  class advanceTests{

    @BeforeEach
    void setUp() {
      exchange.buy("AAPL", new BigDecimal("2"), player);
    }

    @Test
    void shouldIncreaseWeek() {

      int week = exchange.getWeek();
      exchange.advance();
      int nextWeek = exchange.getWeek();

      assertEquals(week + 1, nextWeek);
    }

    @Test
    void shouldKeepPricePositiveAfterAdvance() {
      exchange.advance();

      for (Stock stock : exchange.getAllStocks()) {
        assertTrue(stock.getCurrentPrice().compareTo(new BigDecimal("0.01")) >= 0);
      }
    }

    @Test
    void shouldNeverSetPriceBelowMinimum() {
      Stock cheapStock = new Stock("CHEAP", "Cheap stock", new BigDecimal("0.01"));
      Exchange exchange = new Exchange("Test", List.of(cheapStock));

      for (int i = 0; i < 100; i++) {
        exchange.advance();
        assertTrue(cheapStock.getCurrentPrice().compareTo(new BigDecimal("0.01")) >= 0);
      }
    }

  }

  @Nested
  class getGainersTests{

    @Test
    void shouldReturnTwoStocks() {
      exchange.advance();
      List<Stock> stock = exchange.getGainers(2);

      assertEquals(2, stock.size());
    }

    @Test
    void shouldReturnStocksSortedByHighestGain() {
      Stock s1 = new Stock("A", "AA", new BigDecimal("100"));
      Stock s2 = new Stock("B", "BB", new BigDecimal("100"));
      Stock s3 = new Stock("C", "CC", new BigDecimal("100"));

      // Simuler prisendring
      s1.changeCurrentPrice(new BigDecimal("110")); // +10
      s2.changeCurrentPrice(new BigDecimal("120")); // +20
      s3.changeCurrentPrice(new BigDecimal("105")); // +5

      Exchange exchange = new Exchange("Test", List.of(s1, s2, s3));

      List<Stock> result = exchange.getGainers(3);

      assertEquals("B", result.get(0).getSymbol());
      assertEquals("A", result.get(1).getSymbol());
      assertEquals("C", result.get(2).getSymbol());
    }

    @Test
    void shouldThrowIfLimitIsLessThanOne() {
      assertThrows(IllegalArgumentException.class, () ->
        exchange.getGainers(0));
    }
  }

  @Nested
  class getLosersTests{

    @Test
    void shouldReturnTwoStocks() {
      exchange.advance();
      List<Stock> stock = exchange.getLosers(2);

      assertEquals(2, stock.size());
    }

    @Test
    void shouldReturnStocksSortedByHighestGain() {
      Stock s1 = new Stock("A", "AA", new BigDecimal("100"));
      Stock s2 = new Stock("B", "BB", new BigDecimal("100"));
      Stock s3 = new Stock("C", "CC", new BigDecimal("100"));

      // Simuler prisendring
      s1.changeCurrentPrice(new BigDecimal("90")); // -10
      s2.changeCurrentPrice(new BigDecimal("100")); // 0
      s3.changeCurrentPrice(new BigDecimal("91")); // -9

      Exchange exchange = new Exchange("Test", List.of(s1, s2, s3));

      List<Stock> result = exchange.getLosers(3);

      assertEquals("A", result.get(0).getSymbol());
      assertEquals("C", result.get(1).getSymbol());
      assertEquals("B", result.get(2).getSymbol());
    }

    @Test
    void shouldThrowIfLimitIsLessThanOne() {
      assertThrows(IllegalArgumentException.class, () ->
        exchange.getGainers(0));
    }
  }
}

