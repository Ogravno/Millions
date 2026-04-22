package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.controller.GameController;
import edu.ntnu.idatt2003.group16.model.GameSession;
import edu.ntnu.idatt2003.group16.observer.GameObserver;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;



/**
 * Main view for the game.
 */
public class MainGameView implements GameObserver {

  private final GameController gameController;
  private final GameSession gameSession;

  private final Label weekLabel;
  private final Label graphPanel;
  private final Label portfolioPanel;
  private final Label sharesPanel;
  private final Label headerLabel;

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
    this.graphPanel = new Label("Future graph");
    this.portfolioPanel = new Label("Future Portfolio");
    this.sharesPanel = new Label("Future shares");
    this.headerLabel = new Label("Future header");

    Button advanceWeekButton = new Button("Advance Week");
    advanceWeekButton.setOnAction(event -> this.gameController.advanceWeek());

    // Main panes
    BorderPane root = new BorderPane();
    HBox header = new HBox(10);
    VBox mainCenter = new VBox(10);
    // VBox exchangeCenter = new VBox(10);
    // VBox transactionsCenter = new VBox(10);

    // Header
    header.getChildren().addAll(headerLabel, weekLabel, advanceWeekButton);

    // MainCenter
    HBox hBoxMainCenter = new HBox(10); // Contains Graph and portfolio
    hBoxMainCenter.getChildren().addAll(graphPanel, portfolioPanel);
    mainCenter.getChildren().addAll(hBoxMainCenter, sharesPanel);

    // Connect panes
    root.setTop(header);
    root.setCenter(mainCenter);

    // Padding
    root.setPadding(new Insets(20));
    mainCenter.setPadding(new Insets(10));
    header.setPadding(new Insets(10));

    // Spacing
    header.setSpacing(20);
    hBoxMainCenter.setSpacing(20);

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
    weekLabel.setText("Week: " + gameSession.getExchange().getWeek()); /*
    moneyLabel.setText("Money: "+ gameSession.getPlayer().getMoney());
    netWorthLabel.setText("Net worth: " + gameSession.getPlayer().getNetWorth());
    statusLabel.setText("Status: " + gameSession.getPlayer().getStatus());      */
  }

  @Override
  public void onGameStateChanged() {
    updateView();
  }
}
