package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.controller.AppController;
import edu.ntnu.idatt2003.group16.controller.GameController;
import edu.ntnu.idatt2003.group16.model.GameSession;
import edu.ntnu.idatt2003.group16.view.components.PortfolioOverview;
import edu.ntnu.idatt2003.group16.view.components.PortfolioShares;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

/**
 * Home view for displaying portfolio and shares.
 */
public class PortfolioView {

  private final GameController gameController;
  private final GameSession gameSession;

  private final GridPane root;

  /**
   * Creates the portfolio view.
   *
   * @param appController the application controller
   * @param gameController the game controller
   * @param gameSession the active game session
   */
  public PortfolioView(AppController appController,
                       GameController gameController,
                       GameSession gameSession) {
    this.gameController = gameController;
    this.gameSession = gameSession;

    Label graphPanel = new Label("Future graph");
    graphPanel.getStyleClass().add("standard-text");

    VBox graphContainer = new VBox(graphPanel);
    graphContainer.getStyleClass().add("tile");

    PortfolioOverview portfolio = new PortfolioOverview(gameSession);
    portfolio.getStyleClass().add("tile");

    PortfolioShares shares = new PortfolioShares(appController, gameSession, gameController);
    shares.getStyleClass().add("tile");

    root = new GridPane();
    root.getStyleClass().add("content-container");

    ColumnConstraints column1 = new ColumnConstraints();
    column1.setPercentWidth(33);

    ColumnConstraints column2 = new ColumnConstraints();
    column2.setPercentWidth(34);

    ColumnConstraints column3 = new ColumnConstraints();
    column3.setPercentWidth(33);

    root.getColumnConstraints().addAll(column1, column2, column3);

    RowConstraints row1 = new RowConstraints(250);

    root.getRowConstraints().add(row1);

    root.add(portfolio, 2, 0, 1, 1);
    root.add(graphContainer, 0, 0, 2, 1);
    root.add(shares, 0, 2, 3, 1);
  }

  public GridPane getView() {
    return root;
  }
}