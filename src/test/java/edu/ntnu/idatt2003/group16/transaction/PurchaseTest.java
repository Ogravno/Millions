package edu.ntnu.idatt2003.group16.transaction;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.ntnu.idatt2003.group16.investment.Share;
import edu.ntnu.idatt2003.group16.investment.Stock;
import edu.ntnu.idatt2003.group16.player.Player;
import edu.ntnu.idatt2003.group16.transaction.calculator.SaleCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

class PurchaseTest {
  Share share;
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

    purchase = new Purchase(share, 1);
    player = new Player("Player 1", new BigDecimal("1000"));
    poorPlayer = new Player("Player 1", new BigDecimal("4"));
  }

  @Test
  void constructorSuccessful() {
    assertDoesNotThrow(() -> new SaleCalculator(share));
  }

  @Test
  void constructorParameterIsNull() {
    assertThrows(IllegalArgumentException.class, () -> new SaleCalculator(null));
  }

  @Test
  void commitPlayerIsNull() {
    assertThrows(IllegalArgumentException.class, () -> purchase.commit(null));
  }

  @Test
  void commitAlreadyCommitted() {
    assertThrows(IllegalStateException.class, () -> purchase.commit(player));
  }

  @Test
  void commitInsufficientFunds() {
    assertThrows(IllegalStateException.class, () -> purchase.commit(poorPlayer));
  }


}