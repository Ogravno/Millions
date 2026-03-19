package edu.ntnu.idatt2003.group16.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PlayerTest {
  Player player;

  @BeforeEach
  void setUp() {
    player = new Player("Player 1", new BigDecimal("100"));
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
}