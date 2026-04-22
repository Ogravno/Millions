package edu.ntnu.idatt2003.group16.factory;

import edu.ntnu.idatt2003.group16.model.investment.Share;
import edu.ntnu.idatt2003.group16.model.investment.Stock;
import edu.ntnu.idatt2003.group16.model.transaction.Purchase;
import edu.ntnu.idatt2003.group16.model.transaction.Sale;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TransactionFactoryTest {

  @Test
  void shouldCreatePurchase() {
    TransactionFactory factory = new TransactionFactory();
    BigDecimal price = new BigDecimal("100");
    Stock stock = new Stock("AAPL", "Aplle", price);
    Share share = new Share(stock, new BigDecimal("10"), price);

    Purchase purchase = factory.createPurchase(share, 1);

    assertNotNull(purchase);
    assertEquals(share, purchase.getShare());
  }

  @Test
  void shouldCreateSale() {
    TransactionFactory factory = new TransactionFactory();
    BigDecimal price = new BigDecimal("100");
    Stock stock = new Stock("AAPL", "Aplle", price);
    Share share = new Share(stock, new BigDecimal("10"), price);

    Sale sale = factory.createSale(share, 1);

    assertNotNull(sale);
    assertEquals(share, sale.getShare());
  }
}