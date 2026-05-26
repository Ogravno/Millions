package edu.ntnu.idatt2003.group16.controller;

import edu.ntnu.idatt2003.group16.model.GameSession;
import java.net.URL;
import javafx.scene.Scene;

/**
 * Controller responsible for handling applications functionality.
 *
 * <p>This includes management of the active game session,
 * loading stylesheets, and switching between light and dark themes.</p>
 */
public class AppController {
  private final GameSession gameSession;

  private boolean darkTheme;

  /**
   * Creates an application controller.
   *
   * @param gameSession the active game session for the application
   */
  public AppController(GameSession gameSession) {
    if (gameSession == null) {
      throw new IllegalArgumentException("GameSession cannot be null");
    }

    this.gameSession = gameSession;

    darkTheme = false;
  }

  /**
   * Gets the active game session.
   *
   * @return the active game session
   */
  public GameSession getGameSession() {
    return gameSession;
  }

  /**
   * Checks if the dark theme is activated.
   *
   * @return {@code true} if dark theme is enabled, {@code false} if not
   */
  public boolean isDarkTheme() {
    return darkTheme;
  }

  /**
   * Changes the application between light and dark mode.
   *
   * @param scene the scene where the stylesheets should be applied
   * @throws IllegalArgumentException if scene is null
   */
  public void changeTheme(Scene scene) {
    if (scene == null) {
      throw new IllegalArgumentException("Scene cannot be null");
    }

    scene.getStylesheets().clear();

    URL themeStyleSheet;
    if (isDarkTheme()) {
      themeStyleSheet = getClass().getResource("/css/light-theme.css");
      darkTheme = false;
    } else {
      themeStyleSheet = getClass().getResource("/css/dark-theme.css");
      darkTheme = true;
    }

    if (themeStyleSheet != null) {
      scene.getStylesheets().add(themeStyleSheet.toExternalForm());
    }

    loadStylesheets(scene);
  }

  /**
   * Loads all shared application stylesheets into the scene.
   *
   * @param scene the scene where the stylesheets should be loaded
   * @throws IllegalArgumentException if the scene is null
   */
  public void loadStylesheets(Scene scene) {
    if (scene == null) {
      throw new IllegalArgumentException("Scene cannot be null");
    }

    URL styleSheet = getClass().getResource("/css/app.css");
    if (styleSheet != null) {
      scene.getStylesheets().add(styleSheet.toExternalForm());
    }

    URL startViewStyleSheet = getClass().getResource("/css/start-view.css");
    if (startViewStyleSheet != null) {
      scene.getStylesheets().add(startViewStyleSheet.toExternalForm());
    }

    URL mainGameStyleSheet = getClass().getResource("/css/main-game-view.css");
    if (mainGameStyleSheet != null) {
      scene.getStylesheets().add(mainGameStyleSheet.toExternalForm());
    }

    URL exchangeStyleSheet = getClass().getResource("/css/exchange-game-view.css");
    if (exchangeStyleSheet != null) {
      scene.getStylesheets().add(exchangeStyleSheet.toExternalForm());
    }

    URL transactionsStyleSheet = getClass().getResource("/css/transaction-view.css");
    if (transactionsStyleSheet != null) {
      scene.getStylesheets().add(transactionsStyleSheet.toExternalForm());
    }
  }
}
