package edu.ntnu.idatt2003.group16.view.components;

import java.math.BigDecimal;
import java.math.RoundingMode;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * View for the leaderboard cards in the exchange page.
 *
 * @author Odin Grav
 */
public class LeaderboardCard extends BorderPane {

  /**
   * Draws the leaderboard card.
   *
   * @param position the positon in the leaderboard
   * @param name the name of the company/stock
   * @param value the value of the stock
   * @param changePercentage the percentage change of the stock
   */
  public LeaderboardCard(int position, String name, BigDecimal value, BigDecimal changePercentage) {
    Label positionLabel = new Label(position + ".");
    positionLabel.getStyleClass().add("sub-heading");

    Label stockNameLabel = new Label(name);
    stockNameLabel.getStyleClass().add("standard-text");

    Label stockValueLabel = new Label("$" + value.toString());
    stockValueLabel.getStyleClass().add("sub-text");

    this.setLeft(
        new HBox(
            positionLabel,
            new VBox(
                stockNameLabel,
                stockValueLabel
            )
        )
    );

    String changePrecentageString = changePercentage.multiply(BigDecimal.valueOf(100))
        .setScale(2, RoundingMode.HALF_UP)
        .toString();

    Label changeLabel = new Label(changePrecentageString + "%");
    FontIcon changeArrow;

    if (changePercentage.signum() == 1) {
      changeLabel.getStyleClass().addAll("standard-text", "green-text");

      changeArrow = new FontIcon("mdi2a-arrow-up");
      changeArrow.getStyleClass().addAll("standard-icon", "green-icon");
    } else if (changePercentage.signum() == -1) {
      changeLabel.getStyleClass().addAll("standard-text", "red-text");

      changeArrow = new FontIcon("mdi2a-arrow-down");
      changeArrow.getStyleClass().addAll("standard-icon", "red-icon");
    } else {
      changeLabel.getStyleClass().add("standard-text");

      changeArrow = new FontIcon("mdi2e-equal");

    }

    changeLabel.setGraphic(changeArrow);

    VBox changeLabelContainer = new VBox(changeLabel);
    this.setRight(changeLabelContainer);
  }
}
