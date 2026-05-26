package edu.ntnu.idatt2003.group16.controller;

import edu.ntnu.idatt2003.group16.model.GameSession;
import edu.ntnu.idatt2003.group16.model.exchange.Exchange;
import edu.ntnu.idatt2003.group16.model.filemanagement.StockFileReader;
import edu.ntnu.idatt2003.group16.model.investment.Stock;
import edu.ntnu.idatt2003.group16.model.player.Player;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for creating and preparing a new game.
 */
public class NewGameController {
  GameSession gameSession;

  String gameName;
  Player player;
  Exchange exchange;

  StockFileReader stockFileReader;
  List<Stock> stocks;

  /**
   * Creates a controller for setting up a new game.
   *
   * @param gameSession the game session to initialize
   * @throws IllegalArgumentException if the gameSession is null
   */
  public NewGameController(GameSession gameSession) {
    if (gameSession == null) {
      throw new IllegalArgumentException("GameSession cannot be null.");
    }

    this.gameSession = gameSession;
    this.stockFileReader = new StockFileReader();
    this.stocks = new ArrayList<>();
  }

  public List<Stock> getStocks() {
    return stocks;
  }

  /**
   * Processes stock data from a file and stores the loaded stocks.
   *
   * @param file the file containing stock data
   * @throws IOException if an I/O error occurs while reading the file
   * @throws IllegalArgumentException if the stock data contains invalid values
   */
  public void processStockFile(File file) throws IOException, IllegalArgumentException {
    stocks = stockFileReader.readStocks(file.toPath());
  }

  /**
   * Processes stock data from an input stream and stores the loaded stocks.
   *
   * <p>The stock data must follow the required CSV format:
   * symbol, company, price.
   * </p>
   *
   * @param inputStream the input stream containing stock data.
   * @throws IOException if an I/O error occurs while reading the stream.
   * @throws IllegalArgumentException if the stock data contains invalid values.
   */
  public void processStockFile(InputStream inputStream)
      throws IOException, IllegalArgumentException {
    stocks = stockFileReader.readStocks(inputStream);
  }

  /**
   * Sets the name of the game.
   *
   * @param gameName the name of the game
   */
  public void setGameName(String gameName) {
    this.gameName = gameName;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  public void setExchange(Exchange exchange) {
    this.exchange = exchange;
  }

  /**
   * Creates the player for the new game.
   *
   * @param name the name of the player
   * @param startingMoney the player's starting amount of money
   */
  public void createPlayer(String name, BigDecimal startingMoney) {
    player = new Player(name, startingMoney);
  }

  /**
   * Creates the exchange for the new game.
   *
   * @param name the name of the exchange
   * @throws IllegalStateException if no stocks have been loaded
   */
  public void createExchange(String name) {
    if (stocks.isEmpty()) {
      throw new IllegalStateException("Stocks list cannot be empty");
    }

    exchange = new Exchange(name, stocks);
  }

  /**
   * Starts the game using the selected game name, player, and exchange.
   */
  public void startGame() {
    gameSession.initialGame(gameName, player, exchange);
  }
}
