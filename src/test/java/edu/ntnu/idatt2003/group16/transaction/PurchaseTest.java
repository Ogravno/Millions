package edu.ntnu.idatt2003.group16.transaction;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idatt2003.group16.investment.Share;
import edu.ntnu.idatt2003.group16.investment.Stock;
import edu.ntnu.idatt2003.group16.player.Player;
import edu.ntnu.idatt2003.group16.transaction.calculator.PurchaseCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

class PurchaseTest {
  Share share;
  int week;
  Purchase purchase;
  Player player;
  Player poorPlayer;

  @BeforeEach
  void setUp() {
    BigDecimal purchasePrice = new BigDecimal("3.15");
    Stock stock = new Stock(
        "GOOG", "Alphabet Inc Class C", purchasePrice);
    BigDecimal quantity = new BigDecimal("3");
    share = new Share(stock, quantity, purchasePrice);

    week = 1;
    purchase = new Purchase(share, week);
    player = new Player("Player 1", new BigDecimal("1000"));
    poorPlayer = new Player("Player 2", new BigDecimal("4"));
  }

  @Test
  void constructorSuccessful() {
    assertDoesNotThrow(() -> new PurchaseCalculator(share));
  }

  @Test
  void constructorParameterIsNull() {
    assertThrows(IllegalArgumentException.class, () -> new PurchaseCalculator(null));
  }

  @Test
  void commitPlayerIsNull() {
    assertThrows(IllegalArgumentException.class, () -> purchase.commit(null));
  }

  @Test
  void commitAlreadyCommitted() {
    purchase.commit(player);
    assertThrows(IllegalStateException.class, () -> purchase.commit(player));
  }

  @Test
  void commitInsufficientFunds() {
    assertThrows(IllegalStateException.class, () -> purchase.commit(poorPlayer));
  }

  @Test
  void commitPlayerAlreadyOwnsShare() {
    player.getPortfolio().addShare(share);

    assertThrows(IllegalStateException.class, () -> purchase.commit(player));
  }

  @Test
  void commitWithdrawsFunds() {
    BigDecimal expectedRemainingFunds = player.getMoney()
        .subtract(purchase.getCalculator().calculateTotal());
    purchase.commit(player);

    assertEquals(expectedRemainingFunds, player.getMoney());
  }

  @Test
  void commitAddsShareToPortfolio() {
    purchase.commit(player);
    assertTrue(player.getPortfolio().contains(share));
  }

  @Test
  void commitAddsPurchaseToTransactionArchive() {
    purchase.commit(player);
    assertTrue(player.getTransactionArchive().getPurchases(week).contains(purchase));
  }
}