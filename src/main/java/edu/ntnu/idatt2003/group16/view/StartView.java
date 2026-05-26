package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.controller.NewGameController;
import edu.ntnu.idatt2003.group16.model.GameSession;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;

public class StartView {

  private final NewGameController newGameController;

  private BorderPane root;

  public StartView(GameSession gameSession) {
    newGameController = new NewGameController(gameSession);

    root = new BorderPane();

    showStartMenu();
  }

  private void showStartMenu() {
    Label  logoNormal = new Label("Stock");
    logoNormal.getStyleClass().add("logo");

    Label  logoHighlight = new Label("Sim");
    logoHighlight.getStyleClass().add("logo-highlight");

    HBox logo = new HBox();
    logo.getChildren().addAll(logoNormal, logoHighlight);
    logo.getStyleClass().add("logo-container");

    Button newGameButton = new Button("New Game");
    newGameButton.setOnAction(event -> {
      showNewGameView();
    });
    newGameButton.getStyleClass().addAll("menu-button", "standard-text");

    Button loadGameButton = new Button("Load Game");
    loadGameButton.setOnAction(event -> {

    });
    loadGameButton.getStyleClass().addAll("menu-button", "standard-text");

    Button exitGameButton = new Button("Exit");
    exitGameButton.setOnAction(event -> {
      Platform.exit();
    });
    exitGameButton.getStyleClass().addAll("menu-button", "standard-text");

    VBox menuOptions = new VBox();
    menuOptions.getChildren().addAll(
        newGameButton,
        loadGameButton,
        exitGameButton
    );
    menuOptions.getStyleClass().add("menu-option-container");

    VBox startMenu = new VBox();
    startMenu.getChildren().addAll(
        logo,
        menuOptions
    );
    startMenu.getStyleClass().add("start-menu");

    root.setCenter(startMenu);
  }

  private void showNewGameView() {
    NewGameView newGameView = new NewGameView(newGameController);
    root.setCenter(newGameView.getView());
  }

  public BorderPane getView() {
    return root;
  }

  public void showStartMenuView() {
    showStartMenu();
  }
}
