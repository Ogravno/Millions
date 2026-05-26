package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.controller.AppController;
import edu.ntnu.idatt2003.group16.controller.GameController;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

/**
 * Dialog shown when the game is over.
 *
 * <p>The dialog displays a summary of the player's result
 * and lets the user close the game or return to the main menu.</p>
 */
public class EndDialog extends Dialog<Void> {

  /**
   * Creates the end dialog.
   *
   * @param appController the application controller
   * @param gameController the game controller
   * @param backToMainMenuAction the action used to return to the main menu
   */
  public EndDialog(AppController appController,
                   GameController gameController,
                   Runnable backToMainMenuAction) {

    setTitle("Game over");
    setHeaderText("Game over");

    getDialogPane().setPrefSize(300, 225);

    URL themeStyleSheet;
    if (appController.isDarkTheme()) {
      themeStyleSheet = getClass().getResource("/css/dark-theme.css");
    } else {
      themeStyleSheet = getClass().getResource("/css/light-theme.css");
    }
    if (themeStyleSheet != null) {
      getDialogPane().getStylesheets().add(themeStyleSheet.toExternalForm());
    }

    URL styleSheet = getClass().getResource("/css/dialog.css");
    if (styleSheet != null) {
      getDialogPane().getStylesheets().add(styleSheet.toExternalForm());
    }

    Label startMoneyLabel = new Label(
        "Starting money: " + gameController.getPlayerStartMoney().toString());
    Label endMoneyLabel = new Label("Ending money: " + gameController.getFormattedNetWorth());
    Label profitLossLabel = new Label("Profit or loss: " + profitOrLossCalculator(gameController));
    Label prosentChangeLabel = new Label("Profit/loss in %: " + profitInPercent(gameController));
    Label weeksPlayedLabel = new Label("Weeks played: " + gameController.getWeek());
    Label currentStatusLabel = new Label("Your status: " + gameController.getStatus().toString());

    VBox content = new VBox(10);
    content.getChildren().addAll(
        startMoneyLabel,
        endMoneyLabel,
        profitLossLabel,
        prosentChangeLabel,
        weeksPlayedLabel,
        currentStatusLabel
    );

    getDialogPane().setContent(content);

    ButtonType closeGameButton =
        new ButtonType("Close Game", ButtonBar.ButtonData.CANCEL_CLOSE);

    ButtonType backToMainMenuButton =
        new ButtonType("Back to Main Menu", ButtonBar.ButtonData.OK_DONE);

    getDialogPane().getButtonTypes().addAll(backToMainMenuButton, closeGameButton);

    Button backButton = (Button) getDialogPane().lookupButton(backToMainMenuButton);
    backButton.setOnAction(event -> {
      close();
      backToMainMenuAction.run();
    });

    Button closeButton = (Button) getDialogPane().lookupButton(closeGameButton);
    closeButton.setOnAction(event -> {
      Platform.exit();
    });



  }

  private String profitOrLossCalculator(GameController gameController) {
    BigDecimal profitLoss = gameController.getNetWorth()
        .subtract(gameController.getPlayerStartMoney());

    return profitLoss.setScale(2, RoundingMode.HALF_UP).toString();
  }

  private String profitInPercent(GameController gameController) {
    BigDecimal startMoney = gameController.getPlayerStartMoney();
    BigDecimal endMoney = gameController.getNetWorth();
    return endMoney
      .subtract(startMoney)
      .divide(startMoney, 2, RoundingMode.HALF_UP)
      .multiply(new BigDecimal("100"))
      .toString();
  }

  /**
   * Opens the dialog and waits for the user response.
   *
   * @return an empty optional when the dialog is closed
   */
  public Optional<Void> showAndGetResult() {
    return showAndWait();
  }
}
