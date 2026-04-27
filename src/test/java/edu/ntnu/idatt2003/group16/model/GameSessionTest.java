package edu.ntnu.idatt2003.group16.model;

import edu.ntnu.idatt2003.group16.factory.TransactionFactory;
import edu.ntnu.idatt2003.group16.model.exchange.Exchange;
import edu.ntnu.idatt2003.group16.model.investment.Share;
import edu.ntnu.idatt2003.group16.model.investment.Stock;
import edu.ntnu.idatt2003.group16.model.player.Player;
import edu.ntnu.idatt2003.group16.model.transaction.Purchase;
import edu.ntnu.idatt2003.group16.model.transaction.Sale;
import edu.ntnu.idatt2003.group16.observer.GameObserver;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameSessionTest {
  private static List<Stock> stocks = new ArrayList<>();
  private static Stock apple = new Stock("AAPL", "apple",
      new BigDecimal("100"));
  private static Stock microsoft = new Stock("MSFT", "microsoft",
      new BigDecimal("50"));
  private static String gameName = "Game";
  private static Player player = new Player("Player", new BigDecimal("10000"));
  private static TransactionFactory transactionFactory = new TransactionFactory();
  private static Exchange exchange = new Exchange("Exchange", List.of(apple, microsoft));
  private static GameSession gameSession = new GameSession(gameName, player, exchange,
      transactionFactory);


  private static class TestObserver implements GameObserver {
    private boolean notified = false;

    @Override
    public void onGameStateChanged() {
      notified = true;
    }
  }

  @Test
  void shouldAdvanceWeekAndNotifyObservers() {
    TestObserver observer = new TestObserver();
    gameSession.addObserver(observer);

    int initialWeek = exchange.getWeek();

    gameSession.advanceWeek();

    assertEquals(initialWeek + 1, exchange.getWeek());
    assertTrue(observer.notified);
  }

  @Test
  void shouldBuyStockAndReturnPurchase() {
    TestObserver observer = new TestObserver();
    gameSession.addObserver(observer);

    Purchase purchase = gameSession.buyStock("MSFT", new BigDecimal("1"));

    assertNotNull(purchase);
    assertEquals("MSFT", purchase.getShare().getStock().getSymbol());
    assertTrue(observer.notified);
  }

  @Test
  void shouldThrowIfStockIsInvalid() {
    TestObserver observer = new TestObserver();
    gameSession.addObserver(observer);

    assertThrows(IllegalArgumentException.class, () ->
    gameSession.buyStock("Invalid", new BigDecimal("1")));
  }

  @Test
  void shouldThrowIfQuantityIsInvalid() {
    TestObserver observer = new TestObserver();
    gameSession.addObserver(observer);

    assertThrows(IllegalArgumentException.class, () ->
      gameSession.buyStock("MSFT", null));
  }

  @Test
  void shouldSellAndReturnSale() {
    TestObserver observer = new TestObserver();
    gameSession.addObserver(observer);

    gameSession.buyStock("MSFT", new BigDecimal("2"));

    Share share = player.getPortfolio().getShares().getFirst();

    Sale sale = gameSession.sellShare(share);

    assertNotNull(sale);
    assertTrue(observer.notified);
    assertEquals(share, sale.getShare());
  }

  @Test
  void shouldThrowIfShareIsNull() {
    TestObserver observer = new TestObserver();
    gameSession.addObserver(observer);

    assertThrows(IllegalArgumentException.class, () ->
      gameSession.sellShare(null));
  }

  @Test
  void shouldThrowIfShareIsNotInPortfolio() {
    TestObserver observer = new TestObserver();
    gameSession.addObserver(observer);

    Share share = new Share(apple, new BigDecimal("2"), new BigDecimal("100"));

    assertThrows(IllegalArgumentException.class, () ->
      gameSession.sellShare(share));
  }
}
