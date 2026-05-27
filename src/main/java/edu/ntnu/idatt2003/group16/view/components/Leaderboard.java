package edu.ntnu.idatt2003.group16.view.components;

import edu.ntnu.idatt2003.group16.model.investment.Stock;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Draws a leaderboard for the exchange page.
 *
 * @author Odin Grav
 */
public class Leaderboard extends VBox {
  private VBox entries;

  /**
   * Draws the leaderboard.
   *
   * @param headline the headline of the leaderboard
   */
  public Leaderboard(String headline) {
    Label headlineLabel = new Label(headline);
    headlineLabel.getStyleClass().add("headline");

    entries = new VBox();

    this.getChildren().addAll(
        headlineLabel,
        entries
    );
  }

  /**
   * Sets the entries in the leaderboard.
   *
   * @param stocks the stocks to draw in the leaderboard
   */
  public void setEntries(List<Stock> stocks) {
    entries.getChildren().clear();

    for (int i = 0; i < stocks.size(); i++) {
      LeaderboardCard entry = new LeaderboardCard(i + 1, stocks.get(i).getCompany(),
          stocks.get(i).getCurrentPrice(),
          stocks.get(i).getPriceChangePercentage(1));
      entry.getStyleClass().add("leaderboardCard");

      entries.getChildren().add(entry);
    }
  }
}
