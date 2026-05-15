package edu.ntnu.idatt2003.group16.view.components;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class LeaderboardCard extends BorderPane {
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

    Label changeLabel = new Label(changePrecentageString);
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
