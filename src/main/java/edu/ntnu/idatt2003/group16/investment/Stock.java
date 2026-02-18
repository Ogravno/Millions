package edu.ntnu.idatt2003.group16.investment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a share in a company.
 *
 * @Author Robin Strand Prestmo
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
   *
   * @Author Robin Strand Prestmo
   */
  public Stock(String symbol, String company, BigDecimal salesPrice) {
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

  public BigDecimal getSalesPrice() {
    return prices.getLast();
  }

  /**
   * Add´s a new price to the stock
   *
   * @param newPrice is the new price to the stock
   *
   * @Author Robin Strand Prestmo
   */
  public void addNewSalesPrice(BigDecimal newPrice) {
    prices.add(newPrice);
  }
}
