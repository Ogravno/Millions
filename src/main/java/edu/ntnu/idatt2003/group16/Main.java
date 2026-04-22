package edu.ntnu.idatt2003.group16;

import edu.ntnu.idatt2003.group16.controller.GameController;
import edu.ntnu.idatt2003.group16.factory.TransactionFactory;
import edu.ntnu.idatt2003.group16.model.GameSession;
import edu.ntnu.idatt2003.group16.model.exchange.Exchange;
import edu.ntnu.idatt2003.group16.model.investment.Stock;
import edu.ntnu.idatt2003.group16.model.player.Player;
import edu.ntnu.idatt2003.group16.view.MainGameView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.util.List;

/**
 * Starts the MillionsApplication.
 */
public class Main extends Application {

  @Override
  public void start(Stage stage) {
    Player player = new Player("Robin", new BigDecimal("10000"));

    Stock stock1 = new Stock("AAPL", "Apple", new BigDecimal("100.00"));
    Stock stock2 = new Stock("MSFT", "Microsoft", new BigDecimal("200.00"));

    Exchange exchange = new Exchange("NASDAQ", List.of(stock1, stock2));
    TransactionFactory transactionFactory = new TransactionFactory();

    GameSession gameSession = new GameSession(player, exchange, transactionFactory);
    GameController gameController = new GameController(gameSession);
    MainGameView mainGameView = new MainGameView(gameController, gameSession);

    // Test data
    gameSession.buyStock("MSFT", new BigDecimal("2"));
    gameSession.buyStock("AAPL", new BigDecimal("1"));

    stage.setTitle("Millions");
    stage.setScene(mainGameView.getScene());
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
