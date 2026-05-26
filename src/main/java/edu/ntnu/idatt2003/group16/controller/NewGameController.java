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

public class NewGameController {
  GameSession gameSession;

  String gameName;
  Player player;
  Exchange exchange;

  StockFileReader stockFileReader;
  List<Stock> stocks;

  public NewGameController(GameSession gameSession) {
    this.gameSession = gameSession;

    this.stockFileReader = new StockFileReader();
    this.stocks = new ArrayList<>();
  }

  public List<Stock> getStocks() {
    return stocks;
  }

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

  public void setGameName(String gameName) {
    this.gameName = gameName;
  }

  public void createPlayer(String name, BigDecimal startingMoney) {
    player = new Player(name, startingMoney);
  }

  public void createExchange(String name) {
    if (stocks.isEmpty()) {
      throw new IllegalStateException("Stocks list cannot be empty");
    }

    exchange = new Exchange(name, stocks);
  }

  public void startGame() {
    gameSession.initialGame(gameName, player, exchange);
  }
}
