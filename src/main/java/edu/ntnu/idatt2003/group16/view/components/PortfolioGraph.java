package edu.ntnu.idatt2003.group16.view.components;

import edu.ntnu.idatt2003.group16.model.GameSession;
import edu.ntnu.idatt2003.group16.observer.GameObserver;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PortfolioGraph extends VBox implements GameObserver {
  GameSession gameSession;

  public PortfolioGraph(GameSession gameSession) {
    this.gameSession = gameSession;

    this.gameSession.addObserver(this);

    drawChart();
  }

  private void drawChart() {
    List<BigDecimal> xPoints = new ArrayList<>();
    int weeksNr = gameSession.getExchange().getWeek();

    for (int i = 1; i < weeksNr; i++) {
      xPoints.add(gameSession.getPlayer().getNetWorth(i));
    }

    gameSession.getPlayer().getTransactionArchive().getTransactions();

    NumberAxis xAxis = new NumberAxis(0, gameSession.getExchange().getWeek() - 1,
        Math.round((double) gameSession.getExchange().getWeek() / 10));
    xAxis.setLabel("Week nr.");

    double maxXValue = xPoints.stream()
        .max(Comparator.naturalOrder())
        .orElse(BigDecimal.valueOf(100))
        .doubleValue();

    double minXValue = xPoints.stream()
        .min(Comparator.naturalOrder())
        .orElse(BigDecimal.valueOf(100))
        .doubleValue();

    NumberAxis yAxis = new NumberAxis(
        Math.round(minXValue - 100),
        Math.round(maxXValue + 100),
        Math.round((double) (maxXValue + 100) - (minXValue - 100) / 5));
    yAxis.setLabel("Net worth");

    LineChart chart = new LineChart(xAxis, yAxis);
    chart.setLegendVisible(false);
    chart.setCreateSymbols(false);

    XYChart.Series series = new XYChart.Series();

    for (int i = 0; i < xPoints.size(); i++) {
      series.getData().add(new XYChart.Data(i + 1, xPoints.get(i)));
    }

    chart.getData().add(series);

    this.getChildren().clear();
    this.getChildren().add(chart);
  }


  @Override
  public void onGameStateChanged() {
    drawChart();
  }
}
