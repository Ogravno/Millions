package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.controller.NewGameController;
import edu.ntnu.idatt2003.group16.model.GameSession;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class StartView {

  private final NewGameView newGameView;
  private final NewGameController newGameController;

  private BorderPane root;

  public StartView(GameSession gameSession) {
    newGameController = new NewGameController(gameSession);
    newGameView = new NewGameView(newGameController);

    root = new BorderPane();

    showStartMenu();
  }

  private void showStartMenu() {
    Text title = new Text("StockSim");

    Button newGameButton = new Button("New Game");
    newGameButton.setOnAction(event -> {
      showNewGameView();
    });

    Button loadGameButton = new Button("Load Game");
    loadGameButton.setOnAction(event -> {

    });

    Button exitGameButton = new Button("Exit");
    exitGameButton.setOnAction(event -> {
      Platform.exit();
    });

    VBox menuOptions = new VBox();
    menuOptions.getChildren().addAll(
        newGameButton,
        loadGameButton,
        exitGameButton
    );

    VBox startMenu = new VBox();
    startMenu.getChildren().addAll(
        title,
        menuOptions
    );

    root.setCenter(startMenu);
  }

  private void showNewGameView() {
    root.setCenter(newGameView.getView());
  }

  public BorderPane getView() {
    return root;
  }

  public void showStartMenuView() {
    showStartMenu();
  }
}
