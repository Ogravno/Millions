package edu.ntnu.idatt2003.group16.model.exchange;

import edu.ntnu.idatt2003.group16.model.investment.Share;
import edu.ntnu.idatt2003.group16.model.investment.Stock;
import edu.ntnu.idatt2003.group16.model.player.Player;
import edu.ntnu.idatt2003.group16.model.transaction.Purchase;
import edu.ntnu.idatt2003.group16.model.transaction.Sale;
import edu.ntnu.idatt2003.group16.model.transaction.Transaction;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Represents an exchange that contains stocks.
 *
 * @author Robin Strand Prestmo
 */
public class Exchange {
  private String name;
  private int week;
  private Map<String, Stock> stockMap;

  /**
   * Creates a new exchange.
   *
   * @param name is the name of the exchange
   * @param stocks is a list of stocks in the exchange
   * @throws IllegalArgumentException if name is null or blank, or if stock is null.
   */
  public Exchange(String name, List<Stock> stocks) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name cannot be null or blank.");
    }

    if (stocks == null) {
      throw new IllegalArgumentException("Stocks cannot be null.");
    }

    this.name = name;
    this.week = 1;
    this.stockMap = new HashMap<>();

    for (Stock stock : stocks) {
      if (stock == null) {
        throw new IllegalArgumentException("Stock cannot be null.");
      }
      stockMap.put(stock.getSymbol(), stock);
    }
  }

  public Exchange() {
  }

  public String getName() {
    return name;
  }

  public int getWeek() {
    return week;
  }

  /**
   * Check if stock is in exchange.
   *
   * @param symbol represents a unique identifier for a company with four letters
   * @return true if exchange contains stock, false if not
   * @throws IllegalArgumentException if symbol is null or blank
   */
  public boolean hasStock(String symbol) {
    if (symbol == null || symbol.isBlank()) {
      throw new IllegalArgumentException("Symbol cannot be null or blank.");
    }
    return stockMap.containsKey(symbol);
  }

  /**
   * Get all stocks in Exchange as a list.
   *
   * @return List with copy of stockMap.values()
   */
  public List<Stock> getAllStocks() {
    return stockMap.values().stream()
        .sorted(Comparator.comparing(Stock::getSymbol))
        .toList();
  }

  /**
   * Gets the stock that matches the symbol.
   *
   * @param symbol to find matching stock with
   * @return stockMap with stock that has matching symbol
   */
  public Stock getStock(String symbol) {
    if (symbol == null || symbol.isBlank()) {
      throw new IllegalArgumentException("Symbol cannot be null or blank.");
    }
    return stockMap.get(symbol);
  }

  /**
   * Find stock through searching.
   *
   * @param searchTerm is a string to search after stock with
   * @return list with matching stocks.
   * @throws IllegalArgumentException if searchTerm is null or blank.
   */
  public List<Stock> findStocks(String searchTerm) {
    if (searchTerm == null || searchTerm.isBlank()) {
      throw new IllegalArgumentException("SearchTerm cannot be null or blank.");
    }

    String searchLower = searchTerm.toLowerCase();

    return stockMap.values().stream()
        .filter(stock -> stock.getSymbol().toLowerCase().contains(searchLower)
            || stock.getCompany().toLowerCase().contains(searchLower))
        .sorted(Comparator.comparing(Stock::getSymbol))
        .toList();
  }

  /**
   * Buys a specified quantity of a stock for a player and commits the transaction.
   *
   * @param symbol represents the stock to be bought
   * @param quantity the amount of stocks to be bought
   * @param player the player who is buying
   * @return the completed purchase transaction
   * @throws IllegalArgumentException if symbol is null or blank, if quantity is null, zero or less,
   *        if player is null, or if the exchange doesn't have the stock
   */
  public Transaction buy(String symbol, BigDecimal quantity, Player player) {
    if (symbol == null || symbol.isBlank()) {
      throw new IllegalArgumentException("Symbol cannot be null or blank");
    }
    if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Quantity must be positive");
    }
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null");
    }
    if (!hasStock(symbol)) {
      throw new IllegalArgumentException("Stock not found: " + symbol);
    }

    Stock stock = getStock(symbol);

    Share share = new Share(stock, quantity, stock.getCurrentPrice());
    Transaction transaction = new Purchase(share, week);

    if (player.getMoney().compareTo(stock.getCurrentPrice().multiply(quantity)) < 0) {
      throw new IllegalArgumentException("Player has not enough money");
    }

    transaction.commit(player);
    return transaction;
  }

  /**
   * Sells a specified quantity of a stock for a player and commits the transaction.
   *
   * @param share the shares to be sold
   * @param player the player who is selling
   * @return the completed sale transaction
   * @throws IllegalArgumentException if share or player is null
   */
  public Transaction sell(Share share, Player player) {
    if (share == null) {
      throw new IllegalArgumentException("Share cannot be null");
    }
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null");
    }

    Transaction transaction = new Sale(share, week);
    transaction.commit(player);
    return transaction;
  }

  /**
   * Advances the exchange by one week.
   *
   * <p>This updates the stock prices for all stocks in the exchange.</p>
   */
  public void advance() {
    week++;

     Random random = new Random();
     stockMap.values().forEach(stock -> {
       BigDecimal currentPrice = stock.getCurrentPrice();

      double change = (random.nextDouble() - 0.5) * 0.1; // +- 5%
      BigDecimal multiplier = BigDecimal.valueOf(1 + change);

      BigDecimal newPrice = currentPrice
          .multiply(multiplier)
          .max(BigDecimal.valueOf(0.01)) // Set minimum price
          .setScale(2, RoundingMode.HALF_UP);

      stock.changeCurrentPrice(newPrice);
    });
  }

  /**
   * Get a list of stocks with the most gain in percent since last week.
   * The amount of stocks depends on the parameter.
   *
   * @param limit the amount of stocks to show
   * @return list with the limit amount of stocks
   * @throws IllegalArgumentException if limit is less than 1
   */
  public List<Stock> getGainers(int limit) {
    if (limit < 1) {
      throw new IllegalArgumentException("limit cannot be less than 1");
    }

    return stockMap.values().stream()
        .sorted(Comparator.comparing((Stock stock) -> stock.getPriceChangePercentage(1))
          .reversed())
          .limit(limit)
          .toList();
  }

  /**
   * Get a list of stocks with the biggest loss in percent since last week.
   * The amount of stocks depends on the parameter.
   *
   * @param limit the amount of stocks to show
   * @return list with the limit amount of stocks
   * @throws IllegalArgumentException if limit is less than 1
   */
  public List<Stock> getLosers(int limit) {
    if (limit < 1) {
      throw new IllegalArgumentException("limit cannot be less than 1");
    }

    return stockMap.values().stream()
      .sorted(Comparator.comparing((Stock stock) -> stock.getPriceChangePercentage(1)))
      .limit(limit)
      .toList();
  }
}
