package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.controller.AppController;
import edu.ntnu.idatt2003.group16.controller.GameController;
import edu.ntnu.idatt2003.group16.controller.NewGameController;
import edu.ntnu.idatt2003.group16.model.GameSession;
import edu.ntnu.idatt2003.group16.observer.GameObserver;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;

public class AppView implements GameObserver {
  private final AppController appController;
  private final GameSession gameSession;

  private final StartView startView;

  private MainGameView mainGameView;
  private GameController gameController;

  private final Scene scene;

  public AppView(AppController appController, GameSession gameSession) {
    this.appController = appController;
    this.gameSession = gameSession;

    startView = new StartView(gameSession);

    scene = new Scene(startView.getView(), 1100, 700);

    URL styleSheet = getClass().getResource("/css/app.css");
    if (styleSheet != null) {
      scene.getStylesheets().add(styleSheet.toExternalForm());
    }

    URL themeStyleSheet = getClass().getResource("/css/dark-theme.css");
    if (themeStyleSheet != null) {
      scene.getStylesheets().add(themeStyleSheet.toExternalForm());
    }

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
      mainGameView = new MainGameView(gameController, gameSession, () -> {
        gameSession.removeObserver(mainGameView);

        mainGameView = null;
        gameController = null;

        gameSession.resetSession();

        startView.showStartMenuView();
        scene.setRoot(startView.getView());
      });

      scene.setRoot(mainGameView.getView());
    }
  }
}
