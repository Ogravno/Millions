package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.controller.NewGameController;
import edu.ntnu.idatt2003.group16.observer.GameObserver;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

/**
 * Creates the new game view.
 *
 * @author Odin Grav
 */
public class NewGameView implements GameObserver {

  private final NewGameController newGameController;

  private final VBox root;

  /**
   * Creates the new game view.
   *
   * @param newGameController the controller for creating a new game
   * @param backAction the action used to return to the previous view
   */
  public NewGameView(NewGameController newGameController, EventHandler<ActionEvent> backAction) {
    this.newGameController = newGameController;

    this.root = new VBox();
    root.getStyleClass().add("start-menu");

    Label title = new Label("New Game");
    title.getStyleClass().addAll("headline");

    Label gameNameLabel = new Label("New game name");
    gameNameLabel.getStyleClass().add("standard-text");
    TextField gameNameField = new TextField();
    gameNameField.setPromptText("Game name");
    gameNameField.getStyleClass().add("standard-text");

    VBox gameNameInput = new VBox();
    gameNameInput.getChildren().addAll(gameNameLabel, gameNameField);
    gameNameInput.getStyleClass().add("menu-input-container");

    Label playerNameLabel = new Label("New player name");
    playerNameLabel.getStyleClass().add("standard-text");
    TextField playerNameField = new TextField();
    playerNameField.setPromptText("Player name");
    playerNameField.getStyleClass().add("standard-text");

    VBox playerNameInput = new VBox();
    playerNameInput.getChildren().addAll(playerNameLabel, playerNameField);
    playerNameInput.getStyleClass().add("menu-input-container");

    Label startingMoneyLabel = new Label("Starting money:");
    startingMoneyLabel.getStyleClass().add("standard-text");
    TextField startingMoneyField = new TextField();
    startingMoneyField.setPromptText("Starting money");
    startingMoneyField.getStyleClass().add("standard-text");

    VBox startingMoneyInput = new VBox();
    startingMoneyInput.getChildren().addAll(startingMoneyLabel, startingMoneyField);
    startingMoneyInput.getStyleClass().add("menu-input-container");

    FileChooser stockFileChooser = new FileChooser();
    stockFileChooser.getExtensionFilters().add(
        new FileChooser.ExtensionFilter("csv files", "*.csv")
    );

    Button selectFileButton = new Button("Select file");
    selectFileButton.getStyleClass().add("menu-button");
    selectFileButton.getStyleClass().add("standard-text");

    Button selectStandardStocks = new Button("Select standard stocks");
    selectStandardStocks.getStyleClass().add("menu-button");
    selectStandardStocks.getStyleClass().add("standard-text");

    Label selectFileLabel = new Label("No file selected");
    selectFileLabel.getStyleClass().add("standard-text");

    VBox selectFile = new VBox(
        selectFileButton,
        selectStandardStocks,
        selectFileLabel
    );

    Label defaultFileNotFoundErrorLabel = new Label("Default file not found.");
    defaultFileNotFoundErrorLabel.getStyleClass().addAll("standard-text", "red-text");

    Label defaultFileLoadErrorLabel = new Label("Could not load default file.");
    defaultFileLoadErrorLabel.getStyleClass().addAll("standard-text", "red-text");

    selectStandardStocks.setOnAction(event -> {
      try {
        InputStream inputStream =
            getClass().getResourceAsStream("/stockFiles/stocks.csv");

        if (inputStream == null) {
          selectFile.getChildren().add(defaultFileNotFoundErrorLabel);
          return;
        } else {
          selectFile.getChildren().remove(defaultFileNotFoundErrorLabel);
        }

        newGameController.processStockFile(inputStream);

        selectFileLabel.setText("Selected file: stocks.csv");
        selectFile.getChildren().remove(defaultFileLoadErrorLabel);

      } catch (Exception e) {
        selectFile.getChildren().add(defaultFileLoadErrorLabel);
      }
    });

    Label fileErrorLabel = new Label("Invalid file format.");
    fileErrorLabel.getStyleClass().addAll("standard-text", "red-text");

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
    backButton.getStyleClass().add("standard-text");

    Button startButton = new Button("Start game");
    startButton.getStyleClass().add("standard-text");

    ColumnConstraints column1 = new ColumnConstraints();
    column1.setPercentWidth(48);

    ColumnConstraints column2 = new ColumnConstraints();
    column2.setPercentWidth(4);

    ColumnConstraints column3 = new ColumnConstraints();
    column3.setPercentWidth(48);

    GridPane navigationButtons = new GridPane();
    navigationButtons.getColumnConstraints().addAll(column1, column2, column3);
    navigationButtons.add(backButton, 0, 0);
    navigationButtons.add(startButton, 2, 0);
    navigationButtons.getStyleClass().add("navigation-container");

    backButton.setOnAction(backAction);

    Label gameNameErrorLabel = new Label("Required field");
    gameNameErrorLabel.getStyleClass().addAll("standard-text", "red-text");

    Label playerNameErrorLabel = new Label("Required field");
    playerNameErrorLabel.getStyleClass().addAll("standard-text", "red-text");

    Label startingMoneyRequiredFieldLabel = new Label("Required field");
    startingMoneyRequiredFieldLabel.getStyleClass().addAll("standard-text", "red-text");

    Label startingMoneyInvalidFormatLabel = new Label("Invalid format");
    startingMoneyInvalidFormatLabel.getStyleClass().addAll("standard-text", "red-text");

    Label startingMoneyAmountErrorLabel = new Label("Must be greater than 0");
    startingMoneyAmountErrorLabel.getStyleClass().addAll("standard-text", "red-text");

    Label missingFileErrorLabel = new Label("Must select a file");
    missingFileErrorLabel.getStyleClass().addAll("standard-text", "red-text");

    startButton.setOnAction(event -> {
      String gameName = gameNameField.getText();
      String playerName = playerNameField.getText();
      String startingMoneyString = startingMoneyField.getText();
      BigDecimal startingMoney = BigDecimal.ZERO;

      boolean failed = false;
      if (gameName.isBlank()) {
        if (!gameNameInput.getChildren().contains(gameNameErrorLabel)) {
          gameNameInput.getChildren().add(gameNameErrorLabel);
        }

        failed = true;
      } else {
        gameNameInput.getChildren().remove(gameNameErrorLabel);
      }

      if (playerName.isBlank()) {
        if (!playerNameInput.getChildren().contains(playerNameErrorLabel)) {
          playerNameInput.getChildren().add(playerNameErrorLabel);
        }

        failed = true;
      } else {
        playerNameInput.getChildren().remove(playerNameErrorLabel);
      }

      if (startingMoneyString.isBlank()) {
        if (!startingMoneyInput.getChildren().contains(startingMoneyRequiredFieldLabel)) {
          startingMoneyInput.getChildren().add(startingMoneyRequiredFieldLabel);
        }

        failed = true;
      } else {
        startingMoneyInput.getChildren().remove(startingMoneyRequiredFieldLabel);
      }

      try {
        startingMoney = new BigDecimal(startingMoneyString);
        startingMoneyInput.getChildren().remove(startingMoneyInvalidFormatLabel);
      } catch (Exception e) {
        if (!startingMoneyInput.getChildren().contains(startingMoneyAmountErrorLabel)) {
          startingMoneyInput.getChildren().add(startingMoneyInvalidFormatLabel);
        }

        failed = true;
      }

      if (startingMoney.signum() != 1) {
        if (!startingMoneyInput.getChildren().contains(startingMoneyAmountErrorLabel)) {
          startingMoneyInput.getChildren().add(startingMoneyAmountErrorLabel);
        }
      } else {
        startingMoneyInput.getChildren().remove(startingMoneyAmountErrorLabel);
      }

      if (newGameController.getStocks().isEmpty()) {
        if (!selectFile.getChildren().contains(missingFileErrorLabel)) {
          selectFile.getChildren().add(missingFileErrorLabel);
        }

        failed = true;
      } else {
        selectFile.getChildren().remove(missingFileErrorLabel);
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
