package edu.ntnu.idatt2003.group16.model;

import edu.ntnu.idatt2003.group16.factory.TransactionFactory;
import edu.ntnu.idatt2003.group16.model.exchange.Exchange;
import edu.ntnu.idatt2003.group16.model.investment.Share;
import edu.ntnu.idatt2003.group16.model.investment.Stock;
import edu.ntnu.idatt2003.group16.model.player.Player;
import edu.ntnu.idatt2003.group16.model.transaction.Purchase;
import edu.ntnu.idatt2003.group16.model.transaction.Sale;
import edu.ntnu.idatt2003.group16.observer.GameObservable;
import edu.ntnu.idatt2003.group16.observer.GameObserver;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GameSession implements GameObservable {

  private String gameName;
  private Player player;
  private Exchange exchange;
  private final TransactionFactory transactionFactory;

  private final List<GameObserver> observers;

  public GameSession(String gameName, Player player, Exchange exchange,
                     TransactionFactory transactionFactory) {
    this.gameName = gameName;
    this.player = player;
    this.exchange = exchange;
    this.transactionFactory = transactionFactory;
    this.observers = new ArrayList<>();
  }

  public String getGameName() {
    return gameName;
  }

  public void setGameName(String gameName) {
    if (gameName == null || gameName.isBlank()) {
      throw new IllegalArgumentException("Parameter gameNane cannot be null or blank");
    }

    this.gameName = gameName;
    notifyObservers();
  }

  public Player getPlayer() {
    return player;
  }

  public void setPlayer(Player player) {
    this.player = player;
    notifyObservers();
  }

  public Exchange getExchange() {
    return exchange;
  }

  public void setExchange(Exchange exchange) {
    this.exchange = exchange;
    notifyObservers();
  }

  /**
   * Advances week and notifies observers after change.
   */
  public void advanceWeek() {
    exchange.advance();
    notifyObservers();
  }

  /**
   * Buys stocks and notifies observers after change.
   *
   * @param symbol the symbol of the stock to buy.
   * @param quantity the amount of stocks to buy.
   * @return the completed purchase transaction
   * @throws IllegalArgumentException if stock or quantity is null, or if quantity is zero or less.
   */
  public Purchase buyStock(String symbol, BigDecimal quantity) {
    Stock stock = exchange.getStock(symbol);
    if (stock == null) {
      throw new IllegalArgumentException("Stock not found: " + symbol);
    }

    if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Quantity must be greater than 0.");
    }

    Share share = new Share(stock, quantity, stock.getCurrentPrice());
    int week = exchange.getWeek();

    Purchase purchase = transactionFactory.createPurchase(share, week);
    purchase.commit(player);

    notifyObservers();

    return purchase;
  }

  /**
   * Sells shares and notifies observers after the change.
   *
   * @param share the share to sell
   * @return the completed sale transaction
   * @throws IllegalArgumentException if share is null or not in portfolio.
   */
  public Sale sellShare(Share share) {
    if (share == null) {
      throw new IllegalArgumentException("Share cannot be null.");
    }

    if(!player.getPortfolio().getShares().contains(share)) {
      throw new IllegalArgumentException("Share is not in portfolio: " + share);
    }

    Sale sale = transactionFactory.createSale(share, exchange.getWeek());
    sale.commit(player);

    notifyObservers();

    return sale;
  }

  public void resetSession() {
    this.gameName = null;
    this.player = null;
    this.exchange = null;
  }

  @Override
  public void addObserver(GameObserver observer) {
    observers.add(observer);
  }

  @Override
  public void removeObserver(GameObserver observer) {
    observers.remove(observer);
  }

  @Override
  public void notifyObservers() {
    for (GameObserver observer : observers) {
      observer.onGameStateChanged();
    }
  }
}
