package edu.ntnu.idatt2003.group16.view.components;

import edu.ntnu.idatt2003.group16.model.investment.Stock;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.util.List;

public class Leaderboard extends VBox {
  private VBox entries;

  public Leaderboard(String headline) {
    Label headlineLabel = new Label(headline);
    headlineLabel.getStyleClass().add("headline");

    entries = new VBox();

    this.getChildren().addAll(
        headlineLabel,
        entries
    );
  }

  public void setEntries(List<Stock> stocks) {
    entries.getChildren().clear();

    for (int i = 0; i < stocks.size(); i++) {
      LeaderboardCard entry = new LeaderboardCard(i+1, stocks.get(i).getCompany(), stocks.get(i).getCurrentPrice(),
          stocks.get(i).getPriceChangePercentage(1));
      entry.getStyleClass().add("leaderboardCard");

      entries.getChildren().add(entry);
    };
  }
}
