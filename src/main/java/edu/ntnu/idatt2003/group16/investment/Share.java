package edu.ntnu.idatt2003.group16.investment;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * The {@code Share} class represents a purchased share, including the related stock,
 * quantity, and purchase price.
 *
 * @author Robin Strand Prestmo
 */
public class Share {
  private final UUID id = UUID.randomUUID();
  private final Stock stock;
  private final BigDecimal quantity;
  private final BigDecimal purchasePrice;

  /**
   * Creates a new share.
   *
   * @param stock is the company to buy shares from.
   * @param quantity is the amount of shares to buy.
   * @param purchasePrice is the price per share.
   * @throws IllegalArgumentException if stock, quantity or purchasePrice is null,
   *         and if quantity and purchasePrice is not greater than zero.
   */
  public Share(Stock stock, BigDecimal quantity, BigDecimal purchasePrice) {
    if (stock == null) {
      throw new IllegalArgumentException("Stock cannot be null.");
    }

    if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Quantity cannot be null, "
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


  // Ref https://www.geeksforgeeks.org/java/equals-hashcode-methods-java/

  /**
   * Compares shares based on their unique id.
   *
   * @param o the reference object with which to compare.
   * @return true if both shares have same id, false if not.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Share share = (Share) o;
    return id.equals(share.id);
  }

  /**
   * Return hashcode based on the share's unique id.
   *
   * @return hashcode of the id.
   */
  @Override
  public int hashCode() {
    return id.hashCode();
  }

  public UUID getId() {
    return id;
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
