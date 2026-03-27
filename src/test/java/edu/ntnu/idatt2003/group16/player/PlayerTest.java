package edu.ntnu.idatt2003.group16.player;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.ntnu.idatt2003.group16.exchange.Exchange;
import edu.ntnu.idatt2003.group16.investment.Share;
import edu.ntnu.idatt2003.group16.investment.Stock;
import edu.ntnu.idatt2003.group16.transaction.Purchase;
import edu.ntnu.idatt2003.group16.transaction.calculator.SaleCalculator;
import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PlayerTest {
  Player player;
  Share googleShare;
  Share googleShare2;
  Share appleShare;
  Exchange exchange;
  Stock appleStock;
  Stock googleStock;

  @BeforeEach
  void setUp() {
    player = new Player("Player 1", new BigDecimal("100"));

    BigDecimal googlePurchasePrice = new BigDecimal("3.15");
    googleStock = new Stock(
        "GOOG", "Alphabet Inc Class C", googlePurchasePrice);
    BigDecimal googleQuantity = new BigDecimal("3");
    googleShare = new Share(googleStock, googleQuantity, googlePurchasePrice);
    googleShare2 = new Share(googleStock, new BigDecimal("2"), googlePurchasePrice);

    BigDecimal applePurchasePrice = new BigDecimal("251.38");
    appleStock = new Stock(
        "AAPL", "Alphabet Inc Class C", applePurchasePrice);
    BigDecimal appleQuantity = new BigDecimal("15");
    appleShare = new Share(appleStock, appleQuantity, applePurchasePrice);
    exchange = new Exchange("exchange", List.of(appleStock, googleStock));
  }

  @Test
  void getNetWorth() {
    BigDecimal expectedResult = player.getMoney();
    SaleCalculator googleCalculator = new SaleCalculator(googleShare);
    expectedResult = expectedResult.add(googleCalculator.calculateTotal());

    SaleCalculator googleCalculator2 = new SaleCalculator(googleShare2);
    expectedResult = expectedResult.add(googleCalculator2.calculateTotal());

    SaleCalculator appleCalculator = new SaleCalculator(appleShare);
    expectedResult = expectedResult.add(appleCalculator.calculateTotal());

    player.getPortfolio().addShare(googleShare);
    player.getPortfolio().addShare(googleShare2);
    player.getPortfolio().addShare(appleShare);

    assertEquals(expectedResult, player.getNetWorth());
  }

  @Nested
  class ConstructorTests {
    @Test
    void constructorSuccess() {
      assertDoesNotThrow(() -> new Player("Test player", new BigDecimal("100")));
    }

    @Test
    void constructorNameNull() {
      assertThrows(IllegalArgumentException.class, () ->
          new Player(null, new BigDecimal("100")));
    }

    @Test
    void constructorStartingMoneyNull() {
      assertThrows(IllegalArgumentException.class, () ->
          new Player("Test Player", null));
    }
  }

  @Nested
  class AddMoneyTests {
    @Test
    void addMoneyAmountNull() {
      assertThrows(IllegalArgumentException.class, () ->
          player.addMoney(null));
    }

    @Test
    void addMoneyAmountNegative() {
      assertThrows(IllegalArgumentException.class, () ->
          player.addMoney(new BigDecimal("-10")));
    }

    @Test
    void addMoneyAmountZero() {
      assertThrows(IllegalArgumentException.class, () ->
          player.addMoney(new BigDecimal("0")));
    }

    @Test
    void addMoneyAddsMoney() {
      BigDecimal moneyToAdd = new BigDecimal("10");
      BigDecimal expectedResult = player.getMoney().add(moneyToAdd);

      player.addMoney(moneyToAdd);

      assertEquals(expectedResult, player.getMoney());
    }
  }

  @Nested
  class WithdrawMoneyTests {
    @Test
    void withdrawMoneyAmountNull() {
      assertThrows(IllegalArgumentException.class, () ->
          player.withdrawMoney(null));
    }

    @Test
    void withdrawMoneyAmountNegative() {
      assertThrows(IllegalArgumentException.class, () ->
          player.withdrawMoney(new BigDecimal("-10")));
    }

    @Test
    void withdrawMoneyAmountZero() {
      assertThrows(IllegalArgumentException.class, () ->
          player.withdrawMoney(new BigDecimal("0")));
    }

    @Test
    void withdrawMoneyAmountMoreThanPlayerMoney() {
      assertThrows(IllegalArgumentException.class, () ->
          player.withdrawMoney(player.getMoney().add(new BigDecimal("1"))));
    }

    @Test
    void withdrawMoneyWithdrawsMoney() {
      BigDecimal moneyToWithdraw = new BigDecimal("10");
      BigDecimal expectedResult = player.getMoney().subtract(moneyToWithdraw);

      player.withdrawMoney(moneyToWithdraw);

      assertEquals(expectedResult, player.getMoney());
    }
  }

  @Nested
  class getStatusTests {

    @Test
    void shouldReturnNOVICE() {
      Status status = player.getStatus();

      assertEquals(Status.NOVICE, status);
    }

    @Test
    void shouldReturnNOVICEWhenWeekIsLessThanTen() {
      Player player1 = new Player("Player1", new BigDecimal("3000"));

      for (int i = 0; i < 5; i++) {
        exchange.buy("AAPL", BigDecimal.ONE, player1);
        exchange.advance();
        appleStock.changeCurrentPrice(new BigDecimal("250"));
      }

      appleStock.changeCurrentPrice(new BigDecimal("5000"));
      assertEquals(Status.NOVICE, player1.getStatus());
    }

    @Test
    void shouldReturnNOVICEWhenNetWorthIsLessThanTwentyPercentThreshold() {
      Player player1 = new Player("Player1", new BigDecimal("3000"));

      for (int i = 0; i < 10; i++) {
        exchange.buy("AAPL", BigDecimal.ONE, player1);
        exchange.advance();
        appleStock.changeCurrentPrice(new BigDecimal("250"));
      }

      assertEquals(Status.NOVICE, player1.getStatus());
    }

    @Test
    void shouldReturnINVESTOR() {
      Player player1 = new Player("Player1", new BigDecimal("3000"));

      for (int i = 0; i < 10; i++) {
        exchange.buy("AAPL", BigDecimal.ONE, player1);
        exchange.advance();
        appleStock.changeCurrentPrice(new BigDecimal("250"));
      }

      appleStock.changeCurrentPrice(new BigDecimal("5000"));
      assertEquals(Status.INVESTOR, player1.getStatus());
    }

    @Test
    void shouldReturnINVESTORWhenWeekIsLessThanTwenty() {
      Player player1 = new Player("Player1", new BigDecimal("6000"));

      for (int i = 0; i < 15; i++) {
        exchange.buy("AAPL", BigDecimal.ONE, player1);
        exchange.advance();
        appleStock.changeCurrentPrice(new BigDecimal("250"));
      }

      appleStock.changeCurrentPrice(new BigDecimal("5000"));
      assertEquals(Status.INVESTOR, player1.getStatus());
    }

    @Test
    void shouldReturnINVESTORWhenNetWorthIsBetweenTwentyAndHundredPrecentGain() {
      Player player1 = new Player("Player1", new BigDecimal("6000"));

      for (int i = 0; i < 20; i++) {
        exchange.buy("AAPL", BigDecimal.ONE, player1);
        exchange.advance();
        appleStock.changeCurrentPrice(new BigDecimal("250"));
      }

      appleStock.changeCurrentPrice(new BigDecimal("400"));
      assertEquals(Status.INVESTOR, player1.getStatus());
    }

    @Test
    void shouldReturnSPECULATOR() {
      Player player1 = new Player("Player1", new BigDecimal("6000"));

      for (int i = 0; i < 20; i++) {
        exchange.buy("AAPL", BigDecimal.ONE, player1);
        exchange.advance();
        appleStock.changeCurrentPrice(new BigDecimal("250"));
      }

      appleStock.changeCurrentPrice(new BigDecimal("5000"));
      assertEquals(Status.SPECULATOR, player1.getStatus());
    }
  }
}
