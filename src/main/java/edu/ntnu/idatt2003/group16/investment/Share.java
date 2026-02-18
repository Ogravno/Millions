package edu.ntnu.idatt2003.group16.investment;

import java.math.BigDecimal;

/**
 * The {@code Share} class represents a purchased share, including the related stock,
 * quantity, and purchase price.
 *
 * @Author Robin Strand Prestmo
 */
public class Share {
  private final Stock stock;
  private final BigDecimal quantity;
  private final BigDecimal purchasePrice;

  /**
   * Creates a new share.
   *
   * @param stock is the company to buy shares from.
   * @param quantity is the amount of shares to buy.
   * @param purchasePrice is the price pr share.
   * @throws IllegalArgumentException if stock, quantity or purchasePrice is null,
   *         and if quantity and purchasePrice is not greater than zero.
   *
   * @Author Robin Strand Prestmo
   */
  public Share(Stock stock, BigDecimal quantity, BigDecimal purchasePrice) {
    if (stock == null) {
      throw new IllegalArgumentException("Stock cannot be null.");
    }

    if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("quantity cannot be null, "
          + "and must be greater than zero.");
    }

    if (purchasePrice == null || purchasePrice.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Purchase price cannot be null, "
          + "and must be greater than zero.");
    }

    this.stock = stock;
    this.quantity = quantity;
    this.purchasePrice = purchasePrice;
  }

  public Stock getStock() {
    return stock;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public BigDecimal getPurchasePrice() {
    return purchasePrice;
  }
}
