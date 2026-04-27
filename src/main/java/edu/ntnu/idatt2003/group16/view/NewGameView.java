package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.controller.NewGameController;
import edu.ntnu.idatt2003.group16.observer.GameObserver;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.math.BigDecimal;

/**
 * Creates the new game view.
 *
 * @author Odin Grav
 */
public class NewGameView implements GameObserver {

  private final NewGameController newGameController;

  private final VBox root;

  public NewGameView(NewGameController newGameController) {
    this.newGameController = newGameController;

    this.root = new VBox();

    Label gameNameLabel = new Label("New game name");
    TextField gameNameField = new TextField();
    gameNameField.setPromptText("Game name");
    Label gameNameErrorLabel = new Label();
    gameNameErrorLabel.setStyle("-fx-text-fill: red;");

    VBox gameNameInput = new VBox();
    gameNameInput.getChildren().addAll(gameNameLabel, gameNameField, gameNameErrorLabel);

    Label playerNameLabel = new Label("New player name");
    TextField playerNameField = new TextField();
    playerNameField.setPromptText("Player name");
    Label playerNameErrorLabel = new Label();
    playerNameErrorLabel.setStyle("-fx-text-fill: red;");

    VBox playerNameInput = new VBox();
    playerNameInput.getChildren().addAll(playerNameLabel, playerNameField, playerNameErrorLabel);

    Label startingMoneyLabel = new Label("Starting money:");
    TextField startingMoneyField = new TextField("1000.00");
    startingMoneyField.setPromptText("Starting money");
    Label startingMoneyErrorLabel = new Label();
    startingMoneyErrorLabel.setStyle("-fx-text-fill: red;");

    VBox startingMoneyInput = new VBox();
    startingMoneyInput.getChildren().addAll(startingMoneyLabel, startingMoneyField,
        startingMoneyErrorLabel);

    FileChooser stockFileChooser = new FileChooser();
    stockFileChooser.getExtensionFilters().add(
        new FileChooser.ExtensionFilter("csv files", "*.csv")
    );

    Label selectFileLabel = new Label("No file selected");
    Button selectFileButton = new Button("Select file");
    Label fileErrorLabel = new Label();
    fileErrorLabel.setStyle("-fx-text-fill: red;");

    HBox fileSelection = new HBox();
    fileSelection.getChildren().addAll(selectFileLabel, selectFileButton);

    VBox selectFile = new VBox();
    selectFile.getChildren().addAll(fileSelection, fileErrorLabel);

    selectFileButton.setOnAction(event -> {
      File selectedFile = stockFileChooser.showOpenDialog(null);
      if (selectedFile != null) {
        try {
          newGameController.processStockFile(selectedFile);
          selectFileLabel.setText(selectedFile.getName());
          fileErrorLabel.setText("");
        } catch (Exception e) {
          fileErrorLabel.setText("Invalid file format.");
        }
      }
    });

    Button backButton = new Button("Go back");
    Button startButton = new Button("Start game");

    HBox navigationButtons = new HBox();
    navigationButtons.getChildren().addAll(backButton, startButton);

    backButton.setOnAction(event -> {

    });

    startButton.setOnAction(event -> {
      String gameName = gameNameField.getText();
      String playerName = playerNameField.getText();
      String startingMoneyString = startingMoneyField.getText();
      BigDecimal startingMoney = BigDecimal.ZERO;

      boolean failed = false;
      if (gameName.isBlank()) {
        gameNameErrorLabel.setText("Required field");
        failed = true;
      } else {
        gameNameErrorLabel.setText("");
      }

      if (playerName.isBlank()) {
        playerNameErrorLabel.setText("Required field");
        failed = true;
      } else {
        playerNameErrorLabel.setText("");
      }

      try {
        startingMoney = new BigDecimal(startingMoneyString);
        startingMoneyErrorLabel.setText("");
      } catch (Exception e) {
        startingMoneyErrorLabel.setText("Incorrect formatting");
        failed = true;
      }

      if (newGameController.getStocks().isEmpty()) {
        fileErrorLabel.setText("Must select a file");
        failed = true;
      } else {
        fileErrorLabel.setText("");
      }

      if (failed) {
        return;
      }

      newGameController.setGameName(gameName);
      newGameController.createPlayer(playerName, startingMoney);
      newGameController.createExchange("Exchange");

      newGameController.startGame();
    });

    root.getChildren().addAll(
        gameNameInput,
        playerNameInput,
        startingMoneyInput,
        selectFile,
        navigationButtons
    );
  }

  public VBox getView() {
    return root;
  }

  @Override
  public void onGameStateChanged() {

  }
}
