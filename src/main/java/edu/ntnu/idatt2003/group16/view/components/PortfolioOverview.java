package edu.ntnu.idatt2003.group16.view.components;

import edu.ntnu.idatt2003.group16.model.GameSession;
import edu.ntnu.idatt2003.group16.observer.GameObserver;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.math.RoundingMode;

public class PortfolioOverview extends HBox implements GameObserver {
  GameSession gameSession;

  Label netWorthLabel;
  Label stockValueLabel;
  Label moneyValueLabel;
  Label statusLabel;

  public PortfolioOverview(GameSession gameSession) {
    this.gameSession = gameSession;

    gameSession.addObserver(this);

    Label headline = new Label("Portfolio");
    headline.getStyleClass().add("headline");

    Label newWorthCaption = new Label("Net worth");
    newWorthCaption.getStyleClass().add("sub-text");
    netWorthLabel = new Label();
    netWorthLabel.getStyleClass().add("sub-heading");

    Label stockValueCaption = new Label("Stocks");
    stockValueCaption.getStyleClass().add("sub-text");
    stockValueLabel = new Label();
    stockValueLabel.getStyleClass().add("standard-text");

    Label moneyValueCaption = new Label("Money");
    moneyValueCaption.getStyleClass().add("sub-text");
    moneyValueLabel = new Label();
    moneyValueLabel.getStyleClass().add("standard-text");

    VBox portfolioInfo = new VBox(
        newWorthCaption,
        netWorthLabel,
        stockValueCaption,
        stockValueLabel,
        moneyValueCaption,
        moneyValueLabel
    );

    statusLabel = new Label();
    statusLabel.getStyleClass().add("sub-heading");

    BorderPane layout = new BorderPane();
    layout.setTop(headline);
    layout.setCenter(portfolioInfo);
    layout.setBottom(statusLabel);

    this.getChildren().add(layout);

    setValues();
  }

  private void setValues() {
    switch (gameSession.getPlayer().getStatus()) {
      case NOVICE -> statusLabel.setText("Novice");
      case INVESTOR -> statusLabel.setText("Investor");
      case SPECULATOR -> statusLabel.setText("Speculator");
      case null, default -> statusLabel.setText("");
    }

    netWorthLabel.setText("$" + gameSession.getPlayer().getFormattedNetWorth());
    stockValueLabel.setText("$" + gameSession
        .getPlayer()
        .getPortfolio()
        .getNetWorth()
        .setScale(2, RoundingMode.HALF_UP)
        .toString());
    moneyValueLabel.setText("$" + gameSession
        .getPlayer()
        .getMoney()
        .setScale(2, RoundingMode.HALF_UP)
        .toString());
  }

  @Override
  public void onGameStateChanged() {
    setValues();
  }
}
