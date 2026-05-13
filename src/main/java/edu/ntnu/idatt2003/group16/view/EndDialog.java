package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.controller.GameController;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public class EndDialog extends Dialog<Void> {

  public EndDialog(GameController gameController) {


    setTitle("Game over");
    setHeaderText("Game over");

    getDialogPane().setPrefSize(300, 225);

    Label startMoneyLabel = new Label("Starting money: " + gameController.getPlayerStartMoney().toString());
    Label endMoneyLabel = new Label("Ending money: " + gameController.getNetWorth());
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

  }

  private String profitOrLossCalculator(GameController gameController) {
    return gameController.getNetWorth()
      .subtract(gameController.getPlayerStartMoney())
      .toString();
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

  public Optional<Void> showAndGetResult() {
    return showAndWait();
  }
}
