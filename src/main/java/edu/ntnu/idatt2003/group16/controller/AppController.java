package edu.ntnu.idatt2003.group16.controller;

import edu.ntnu.idatt2003.group16.model.GameSession;

public class AppController {
  private final GameSession gameSession;

  public AppController(GameSession gameSession) {
    this.gameSession = gameSession;
  }

  public GameSession getGameSession() {
    return gameSession;
  }
}
