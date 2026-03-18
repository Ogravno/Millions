package edu.ntnu.idatt2003.group16.exchange;

import edu.ntnu.idatt2003.group16.investment.Share;
import edu.ntnu.idatt2003.group16.investment.Stock;
import edu.ntnu.idatt2003.group16.player.Player;
import edu.ntnu.idatt2003.group16.transaction.Purchase;
import edu.ntnu.idatt2003.group16.transaction.Sale;
import edu.ntnu.idatt2003.group16.transaction.Transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Exchange {
  private final String name;
  private int week;
  private final Map<String, Stock> stockMap;
  private final Random random;

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
      throw new IllegalArgumentException("Symbol cannot be null or blank.");
    }
    return stockMap.containsKey(symbol);
  }

  public Stock getStock(String symbol) {
    if (symbol == null || symbol.isBlank()) {
      throw new IllegalArgumentException("Symbol cannot be null or blank.");
    }
    return stockMap.get(symbol);
  }

  public List<Stock> findStocks(String searchTerm) {
    if (searchTerm == null || searchTerm.isBlank()) {
      throw new IllegalArgumentException("SearchTerm cannot be null or blank.");
    }

    String searchLower = searchTerm.toLowerCase();
    List<Stock> result = new ArrayList<>();

    for (Stock stock : stockMap.values()) {
      String symbolLower = stock.getSymbol().toLowerCase();
      String companyLower = stock.getCompany().toLowerCase();
      if (symbolLower.contains(searchLower) || companyLower.contains(searchLower)){
        result.add(stock);
      }
    }
    return result;
  }

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

    transaction.commit(player);
    return transaction;
  };

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
   };

   public void Advance() {
     week++;

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
   };
}

















