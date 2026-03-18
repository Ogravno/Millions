package edu.ntnu.idatt2003.group16.investment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a share in a company.
 *
 * @author Robin Strand Prestmo
 */
public class Stock {
  private final String symbol;
  private final String company;
  private final List<BigDecimal> prices;

  /**
   * Creates a new stock.
   *
   * @param symbol is the company´s unique identifier
   * @param company is the name of the company
   * @param salesPrice is the present price of the stock
   * @throws IllegalArgumentException if symbol or company is null/blank,
   *         or if salesPrice is null or not greater than zero.
   */
  public Stock(String symbol, String company, BigDecimal salesPrice) {
    if (symbol == null || symbol.isBlank()) {
      throw new IllegalArgumentException("Symbol cannot be null or blank.");
    }

    if (company == null || company.isBlank()) {
      throw new IllegalArgumentException("Company cannot be null or blank.");
    }

    if (salesPrice == null || salesPrice.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Sales price cannot be null,"
          + " and must be greater than zero.");
    }

    this.symbol = symbol;
    this.company = company;

    this.prices = new ArrayList<>();
    this.prices.add(salesPrice);

  }

  public String getSymbol() {
    return symbol;
  }

  public String getCompany() {
    return company;
  }

  public BigDecimal getCurrentPrice() {
    return prices.getLast();
  }

  /**
   * Add´s a new price to the stock.
   *
   * @param newPrice is the new price to the stock
   * @throws IllegalArgumentException if symbol or company is null/blank,
   *         or if salesPrice is null or not greater than zero
   */
  public void changeCurrentPrice(BigDecimal newPrice) {
    if (newPrice == null || newPrice.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Sales price cannot be null, "
          + "and must be greater than zero.");
    }

    prices.add(newPrice);
  }

  public List<BigDecimal> getHistoricalPrices() {
    return Collections.unmodifiableList(prices);
  }

  public BigDecimal getHighestPrice() {
    return prices.stream().max(BigDecimal::compareTo)
      .orElseThrow(() -> new IllegalStateException("No prices found"));
  }

  public BigDecimal getLowestPrice() {
    return prices.stream().min(BigDecimal::compareTo)
      .orElseThrow(() -> new IllegalStateException("No prices found"));
  }

  public BigDecimal getLatestPriceChange() {
    if (prices.size() <= 1) {
      return new BigDecimal("0");
    }
    BigDecimal latestPrice = prices.getLast();
    BigDecimal secondLatestPrice = prices.get(prices.size() - 2);
    return latestPrice.subtract(secondLatestPrice);
  }
}
