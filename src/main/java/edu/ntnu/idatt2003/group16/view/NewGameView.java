package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.controller.NewGameController;
import edu.ntnu.idatt2003.group16.observer.GameObserver;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Creates the new game view.
 *
 * @author Odin Grav
 */
public class NewGameView implements GameObserver {

  private final NewGameController newGameController;

  private final VBox root;
  private final Scene scene;

  public NewGameView(NewGameController newGameController) {
    this.newGameController = newGameController;

    this.root = new VBox();
    this.scene = new Scene(root, 600, 400);

    Label gameNameLabel = new Label("New game name");
    TextField gameNameField = new TextField();
    gameNameField.setPromptText("Game name");

    Label startingMoneyLabel = new Label("Starting money:");
    TextField startingMoneyField = new TextField("1000.00");
    startingMoneyField.setPromptText("Starting money");

    File stockFile;
    FileChooser stockFileChooser = new FileChooser();
    stockFileChooser.getExtensionFilters().add(
        new FileChooser.ExtensionFilter("csv files", "*.csv")
    );

    HBox selectFile = new HBox();
    Label selectFileLabel = new Label("No file selected");
    Button selectFileButton = new Button("Select file");
    selectFile.getChildren().addAll(selectFileLabel, selectFileButton);

    HBox navigationButtons = new HBox();
    Button backButton = new Button("Go back");
    Button startButton = new Button("Start game");
    navigationButtons.getChildren().addAll(
        backButton,
        startButton
    );

    root.getChildren().addAll(
        gameNameLabel,
        gameNameField,
        startingMoneyLabel,
        startingMoneyField,
        selectFile,
        navigationButtons
    );
  }

  public Scene getScene() {
    return scene;
  }

  @Override
  public void onGameStateChanged() {

  }
}
