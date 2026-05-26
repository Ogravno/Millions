package edu.ntnu.idatt2003.group16;

import edu.ntnu.idatt2003.group16.controller.AppController;
import edu.ntnu.idatt2003.group16.factory.TransactionFactory;
import edu.ntnu.idatt2003.group16.model.GameSession;
import edu.ntnu.idatt2003.group16.view.AppView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class for starting the Millions application.
 */
public class Main extends Application {

  /**
   * Starts the application and initializes the main view.
   *
   * @param stage the primary stage for the application
   */
  @Override
  public void start(Stage stage) {
    TransactionFactory transactionFactory = new TransactionFactory();

    GameSession gameSession = new GameSession(null, null, null,
        transactionFactory);

    AppController appController = new AppController(gameSession);
    AppView appView = new AppView(appController, gameSession);

    stage.setTitle("Millions");
    stage.setScene(appView.getScene());
    stage.show();
  }

  /**
   * Launches the application.
   *
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }
}
