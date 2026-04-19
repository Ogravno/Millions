package edu.ntnu.idatt2003.group16.investment;

import edu.ntnu.idatt2003.group16.model.investment.Share;
import edu.ntnu.idatt2003.group16.model.investment.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ShareTest {
  private Stock stock;
  private BigDecimal quantity;
  private BigDecimal price;

  @BeforeEach
  void setup() {
    price = new BigDecimal("100");
    stock = new Stock("AAPL", "Apple", price);
    quantity = new BigDecimal("10");
  }

  @Nested
  class constructorTests{

    @Test
    void shouldCreateShare() {
      Share share = new Share(stock, quantity, price);

      assertNotNull(share);
      assertEquals(stock, share.getStock());
      assertEquals(quantity, share.getQuantity());
      assertEquals(price, share.getPurchasePrice());
    }

    @Test
    void shouldThrowIfStockIsNull() {
      assertThrows(IllegalArgumentException.class, () ->
      new Share(null, quantity, price));
    }

    @Test
    void shouldThrowIfQuantityIsNull() {
      assertThrows(IllegalArgumentException.class, () ->
        new Share(stock, null, price));
    }

    @Test
    void shouldThrowIfQuantityIsZero() {
      assertThrows(IllegalArgumentException.class, () ->
        new Share(stock, new BigDecimal("0"), price));
    }

    @Test
    void shouldThrowIfQuantityIsNegative() {
      assertThrows(IllegalArgumentException.class, () ->
        new Share(stock, new BigDecimal("-1"), price));
    }

    @Test
    void shouldThrowIfPurchasePriceIsNull() {
      assertThrows(IllegalArgumentException.class, () ->
        new Share(stock, quantity, null));
    }

    @Test
    void shouldThrowIfPurchasePriceIsZero() {
      assertThrows(IllegalArgumentException.class, () ->
        new Share(stock, quantity, new BigDecimal("0")));
    }

    @Test
    void shouldThrowIfPurchasePriceIsNegative() {
      assertThrows(IllegalArgumentException.class, () ->
        new Share(stock, quantity, new BigDecimal("-1")));
    }
  }

  @Nested
  class equalsTests{

    @Test
    void shouldBeEqual() {
      Share share1 = new Share(stock, quantity, price);
      Share share2 = share1;

      assertEquals(share1, share2);
    }

    @Test
    void shouldReturnFalseWhenComparedWithNull() {
      Share share = new Share(stock, quantity, price);

      assertNotEquals(null, share);
    }

    @Test
    void shouldReturnFalseWhenComparedWithDifferentClass() {
      Share share = new Share(stock, quantity, price);

      assertNotEquals("not a share", share);
    }
  }



}