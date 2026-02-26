package edu.ntnu.idatt2003.group16.exchange;

import edu.ntnu.idatt2003.group16.investment.Stock;

import java.util.*;

public class Exchange {
  private String name;
  private int week;
  private Map<String, Stock> stockMap;
  private Random random;

  public Exchange(String name, List<Stock> stocks) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name cannot be null or blank.");
    }

    if (stocks == null) {
      throw new IllegalArgumentException("Stocks cannot be null.");
    }

    this.name = name;
    this.week = 0;
    this.stockMap = new HashMap<>();
    this.random = new Random();

    for (Stock stock : stocks) {
      if (stock == null) {
        throw new IllegalArgumentException("Stock cannot be null.");
      }
      stockMap.put(stock.getSymbol(), stock);
    }
  }

  public String getName() {
    return name;
  }

  public int getWeek() {
    return week;
  }

  public boolean hasStock(String symbol) {
    if (symbol == null || symbol.isBlank()) {
      throw new IllegalArgumentException("Stock cannot be null or blank.");
    }
    return stockMap.containsKey(symbol);
  }
}

















