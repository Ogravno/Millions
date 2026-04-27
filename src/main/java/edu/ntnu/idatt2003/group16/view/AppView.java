package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.controller.AppController;
import edu.ntnu.idatt2003.group16.controller.GameController;
import edu.ntnu.idatt2003.group16.controller.NewGameController;
import edu.ntnu.idatt2003.group16.model.GameSession;
import edu.ntnu.idatt2003.group16.observer.GameObserver;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public class AppView implements GameObserver {
  private final AppController appController;
  private final GameSession gameSession;

  private final NewGameView newGameView;
  private final NewGameController newGameController;

  private MainGameView mainGameView;
  private GameController gameController;

  private final Scene scene;

  public AppView(AppController appController, GameSession gameSession) {
    this.appController = appController;
    this.gameSession = gameSession;

    newGameController = new NewGameController(appController.getGameSession());
    newGameView = new NewGameView(newGameController);

    VBox root = newGameView.getView();
    scene = new Scene(root, 600, 400);

    gameSession.addObserver(this);
  }

  public Scene getScene() {
    return scene;
  }

  @Override
  public void onGameStateChanged() {
    if (
        mainGameView == null
        && gameSession.getGameName() != null
        && gameSession.getPlayer() != null
        && gameSession.getExchange() != null
    ) {
      gameController = new GameController(gameSession);
      mainGameView = new MainGameView(gameController, gameSession);

      scene.setRoot(mainGameView.getView());
    }
  }
}
