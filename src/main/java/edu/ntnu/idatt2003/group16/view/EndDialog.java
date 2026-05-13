package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.controller.GameController;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class EndDialog extends Dialog<Void> {

  public EndDialog(GameController gameController) {


    setTitle("Game over");
    setHeaderText("Game over");

    getDialogPane().setPrefSize(300, 225);

    Label startMoneyLabel = new Label(gameController.getPlayerStartMoney().toString());
    Label endMoneyLabel = new Label(gameController.getPlayerMoney().toString());
    Label profitLossLabel = new Label(profitOrLossCalculator(gameController));
    Label prosentChangeLabel = new Label(profitInPrecent(gameController));
    Label weeksPlayedLabel = new Label(Integer.toString(gameController.getWeek()));
    Label currentStatusLabel = new Label(gameController.getStatus().toString());

    VBox content = new VBox(10);
    content.getChildren().addAll(
      startMoneyLabel,
      endMoneyLabel,
      profitLossLabel,
      prosentChangeLabel,
      weeksPlayedLabel,
      currentStatusLabel
    );

  }

  private String profitOrLossCalculator(GameController gameController) {
    return gameController.getPlayerStartMoney()
      .subtract(gameController.getPlayerMoney())
      .toString();
  }

  private String profitInPrecent(GameController gameController) {
    BigDecimal startMoney = gameController.getPlayerStartMoney();
    BigDecimal endMoney = gameController.getPlayerMoney();
    return endMoney
      .subtract(startMoney)
      .divide(startMoney, 2, RoundingMode.HALF_UP)
      .multiply(new BigDecimal("100"))
      .toString();
  }
}
