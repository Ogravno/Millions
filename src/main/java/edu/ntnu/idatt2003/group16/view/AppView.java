package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.controller.AppController;
import edu.ntnu.idatt2003.group16.controller.GameController;
import edu.ntnu.idatt2003.group16.model.GameSession;
import edu.ntnu.idatt2003.group16.observer.GameObserver;
import java.net.URL;
import javafx.scene.Scene;

/**
 * Main application view responsible for switching
 * between the start view and the main game view.
 */
public class AppView implements GameObserver {
  private final AppController appController;
  private final GameSession gameSession;

  private final StartView startView;

  private MainGameView mainGameView;
  private GameController gameController;

  private final Scene scene;

  /**
   * Creates the main application view.
   *
   * @param appController the application controller
   * @param gameSession the active game session
   */
  public AppView(AppController appController, GameSession gameSession) {
    this.appController = appController;
    this.gameSession = gameSession;

    startView = new StartView(gameSession);

    scene = new Scene(startView.getView(), 1100, 700);

    URL themeStyleSheet = getClass().getResource("/css/light-theme.css");
    if (themeStyleSheet != null) {
      scene.getStylesheets().add(themeStyleSheet.toExternalForm());
    }

    appController.loadStylesheets(scene);

    gameSession.addObserver(this);
  }

  /**
   * Gets the application's main scene.
   *
   * @return the main scene
   */
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
      mainGameView = new MainGameView(appController, gameController, gameSession, () -> {
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
