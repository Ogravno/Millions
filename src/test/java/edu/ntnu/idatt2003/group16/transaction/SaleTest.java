package edu.ntnu.idatt2003.group16.transaction;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idatt2003.group16.investment.Share;
import edu.ntnu.idatt2003.group16.investment.Stock;
import edu.ntnu.idatt2003.group16.player.Player;
import edu.ntnu.idatt2003.group16.transaction.calculator.SaleCalculator;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SaleTest {
  Share share;
  int week;
  Sale sale;
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
    sale = new Sale(share, week);
    player = new Player("Player 1", new BigDecimal("1000"));
    player.getPortfolio().addShare(share);
    poorPlayer = new Player("Player 2", new BigDecimal("4"));
  }

  @Nested
  class ConstructorTests {
    @Test
    void constructorSuccessful() {
      assertDoesNotThrow(() -> new SaleCalculator(share));
    }

    @Test
    void constructorParameterIsNull() {
      assertThrows(IllegalArgumentException.class, () -> new SaleCalculator(null));
    }
  }

  @Nested
  class CommitTests {
    @Test
    void commitPlayerIsNull() {
      assertThrows(IllegalArgumentException.class, () -> sale.commit(null));
    }

    @Test
    void commitAlreadyCommitted() {
      sale.commit(player);
      assertThrows(IllegalStateException.class, () -> sale.commit(player));
    }

    @Test
    void commitPlayerDoesNotOwnShare() {
      assertThrows(IllegalStateException.class, () -> sale.commit(poorPlayer));
    }

    @Test
    void commitAddsMoney() {
      BigDecimal expectedNewFunds = player.getMoney().add(sale.getCalculator().calculateTotal());
      sale.commit(player);
      assertEquals(expectedNewFunds, player.getMoney());
    }

    @Test
    void commitRemovedShareFromPortfolio() {
      sale.commit(player);
      assertFalse(player.getPortfolio().contains(share));
    }

    @Test
    void commitAddsSaleToTransactionArchive() {
      sale.commit(player);
      assertTrue(player.getTransactionArchive().getSales(week).contains(sale));
    }
  }
}