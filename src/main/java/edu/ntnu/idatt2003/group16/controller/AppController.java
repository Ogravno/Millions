package edu.ntnu.idatt2003.group16.controller;

import edu.ntnu.idatt2003.group16.model.GameSession;
import javafx.scene.Scene;

import java.net.URL;

public class AppController {
  private final GameSession gameSession;

  private boolean darkTheme;

  public AppController(GameSession gameSession) {
    this.gameSession = gameSession;

    darkTheme = false;
  }

  public GameSession getGameSession() {
    return gameSession;
  }

  public boolean isDarkTheme() {
    return darkTheme;
  }

  public void changeTheme(Scene scene) {
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

  public void loadStylesheets(Scene scene) {
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
