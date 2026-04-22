package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.controller.GameController;
import edu.ntnu.idatt2003.group16.model.GameSession;
import edu.ntnu.idatt2003.group16.observer.GameObserver;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;



/**
 * Main view for the game.
 */
public class MainGameView implements GameObserver {

  private final GameController gameController;
  private final GameSession gameSession;
  private final Label weekLabel;
  private final Scene scene;

  /**
   * Creates the main game view.
   *
   * @param gameController the controller for user actions.
   * @param gameSession the active game session.
   */
  public MainGameView(GameController gameController, GameSession gameSession) {
    this.gameController = gameController;
    this.gameSession = gameSession;

    this.weekLabel = new Label();

    Button advanceWeekButton = new Button("Advance Week");
    advanceWeekButton.setOnAction(event -> gameController.advanceWeek());

    VBox root = new VBox(10);
    root.setPadding(new Insets(20));
    root.getChildren().addAll(weekLabel, advanceWeekButton);

    this.scene = new Scene(root, 400, 200);

    gameSession.addObserver(this);
    updateView();
  }

  /**
   * Returns the scene for this view.
   *
   * @return the scene
   */
  public Scene getScene() {
    return scene;
  }

  /**
   * Updates the view with current game data.
   */
  private void updateView() {
    weekLabel.setText("Week: " + gameSession.getExchange().getWeek());
  }

  @Override
  public void onGameStateChanged() {
    updateView();
  }
}
