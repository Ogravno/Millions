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
import java.net.URL;
import java.nio.file.Paths;

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
    root.getStyleClass().add("start-menu");

    Label title = new Label("New Game");
    title.getStyleClass().addAll("headline");

    Label gameNameLabel = new Label("New game name");
    TextField gameNameField = new TextField();
    gameNameField.setPromptText("Game name");

    VBox gameNameInput = new VBox();
    gameNameInput.getChildren().addAll(gameNameLabel, gameNameField);
    gameNameInput.getStyleClass().add("menu-input-container");

    Label playerNameLabel = new Label("New player name");
    TextField playerNameField = new TextField();
    playerNameField.setPromptText("Player name");

    VBox playerNameInput = new VBox();
    playerNameInput.getChildren().addAll(playerNameLabel, playerNameField);
    playerNameInput.getStyleClass().add("menu-input-container");

    Label startingMoneyLabel = new Label("Starting money:");
    TextField startingMoneyField = new TextField("1000.00");
    startingMoneyField.setPromptText("Starting money");

    VBox startingMoneyInput = new VBox();
    startingMoneyInput.getChildren().addAll(startingMoneyLabel, startingMoneyField);
    startingMoneyInput.getStyleClass().add("menu-input-container");

    FileChooser stockFileChooser = new FileChooser();
    stockFileChooser.getExtensionFilters().add(
        new FileChooser.ExtensionFilter("csv files", "*.csv")
    );

    Button selectFileButton = new Button("Select file");
    selectFileButton.getStyleClass().add("menu-button");

    Button selectStandardStocks = new Button("Select standard stocks");
    selectStandardStocks.getStyleClass().add("menu-button");

    Label selectFileLabel = new Label("No file selected");
    selectFileLabel.getStyleClass().add("standard-text");

    VBox selectFile = new VBox(
        selectFileButton,
        selectStandardStocks,
        selectFileLabel
    );

    Label fileErrorLabel = new Label();
    fileErrorLabel.getStyleClass().addAll("standard-text", "red-text");

    selectStandardStocks.setOnAction(event -> {
      try {
        URL resource = getClass().getResource("/stockFiles/stocks.csv");

        if (resource == null) {
          fileErrorLabel.setText("Default file not found.");
          selectFile.getChildren().add(fileErrorLabel);
          return;
        }

        File file = Paths.get(resource.toURI()).toFile();

        newGameController.processStockFile(file);

        selectFileLabel.setText("Selected file: " + file.getName());
        fileErrorLabel.setText("");
        selectFile.getChildren().remove(fileErrorLabel);
      } catch (Exception e) {
        fileErrorLabel.setText("Could not load default file.");
      }
    });

    selectFileButton.setOnAction(event -> {
      File selectedFile = stockFileChooser.showOpenDialog(null);
      if (selectedFile != null) {
        try {
          newGameController.processStockFile(selectedFile);
          selectFileLabel.setText(selectedFile.getName());
          fileErrorLabel.setText("");
          selectFile.getChildren().remove(fileErrorLabel);
        } catch (Exception e) {
          fileErrorLabel.setText("Invalid file format.");
          selectFile.getChildren().add(fileErrorLabel);
        }
      }
    });

    Button backButton = new Button("Go back");
    Button startButton = new Button("Start game");

    HBox navigationButtons = new HBox();
    navigationButtons.getChildren().addAll(backButton, startButton);
    navigationButtons.getStyleClass().add("navigation-container");

    backButton.setOnAction(event -> {

    });

    Label gameNameErrorLabel = new Label("Required field");
    gameNameErrorLabel.getStyleClass().addAll("standard-text", "red-text");

    Label playerNameErrorLabel = new Label("Required field");
    playerNameErrorLabel.getStyleClass().addAll("standard-text", "red-text");

    Label startingMoneyErrorLabel = new Label();
    startingMoneyErrorLabel.getStyleClass().addAll("standard-text", "red-text");

    startButton.setOnAction(event -> {
      String gameName = gameNameField.getText();
      String playerName = playerNameField.getText();
      String startingMoneyString = startingMoneyField.getText();
      BigDecimal startingMoney = BigDecimal.ZERO;

      boolean failed = false;
      if (gameName.isBlank()) {
        gameNameInput.getChildren().add(gameNameErrorLabel);
        failed = true;
      } else {
        gameNameInput.getChildren().remove(gameNameErrorLabel);
      }

      if (playerName.isBlank()) {
        playerNameInput.getChildren().add(playerNameErrorLabel);
        failed = true;
      } else {
        playerNameInput.getChildren().remove(playerNameErrorLabel);
      }

      try {
        startingMoney = new BigDecimal(startingMoneyString);
        startingMoneyInput.getChildren().remove(startingMoneyErrorLabel);
      } catch (Exception e) {
        startingMoneyErrorLabel.setText("Invalid format");
        startingMoneyInput.getChildren().add(startingMoneyErrorLabel);
        failed = true;
      }

      if (startingMoney.signum() != 1 || startingMoneyString.isBlank()) {
          startingMoneyErrorLabel.setText("Starting money must be greater than 0");
          startingMoneyInput.getChildren().add(startingMoneyErrorLabel);
      } else {
        startingMoneyInput.getChildren().remove(startingMoneyErrorLabel);
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

    VBox newGameMenuOptions = new VBox(
        gameNameInput,
        playerNameInput,
        startingMoneyInput,
        selectFile,
        navigationButtons
    );
    newGameMenuOptions.getStyleClass().add("menu-option-container");

    root.getChildren().addAll(
        title,
        newGameMenuOptions
    );
  }

  public VBox getView() {
    return root;
  }

  @Override
  public void onGameStateChanged() {

  }
}
