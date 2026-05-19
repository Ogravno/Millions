package edu.ntnu.idatt2003.group16.view.components;

import edu.ntnu.idatt2003.group16.controller.GameController;
import edu.ntnu.idatt2003.group16.model.investment.Stock;
import edu.ntnu.idatt2003.group16.view.BuyDialog;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ExchangeStockCard extends GridPane {
  public ExchangeStockCard(Stock stock, GameController gameController) {
    Label companyLabel = new Label(stock.getCompany());
    companyLabel.getStyleClass().add("sub-heading");

    Label symbolLabel = new Label(stock.getSymbol());
    symbolLabel.getStyleClass().addAll("sub-heading", "light-text");

    Label priceLabel = new Label("$" + stock.getCurrentPrice().toString());
    priceLabel.getStyleClass().addAll("sub-heading", "bold-text");

    ColumnConstraints column1 = new ColumnConstraints();
    column1.setPercentWidth(50);
    column1.setHalignment(HPos.LEFT);

    ColumnConstraints column2 = new ColumnConstraints();
    column2.setPercentWidth(30);
    column2.setHalignment(HPos.LEFT);

    ColumnConstraints column3 = new ColumnConstraints();
    column3.setPercentWidth(20);
    column3.setHalignment(HPos.RIGHT);

    this.getColumnConstraints().addAll(column1, column2, column3);

    this.add(companyLabel, 0, 0);
    this.add(symbolLabel, 1, 0);
    this.add(priceLabel, 2, 0);

    Button buyButton = new Button("Buy");

    buyButton.setOnAction(event -> {
      BuyDialog buyDialog = new BuyDialog(gameController, stock);
      buyDialog.showAndGetResult();
    });

    for (int i = 0; i < 3; i++) {
      BigDecimal changePercentage = stock.getPriceChangePercentage(i+1)
          .multiply(BigDecimal.valueOf(100))
          .setScale(2, RoundingMode.HALF_UP);

      Label changePercentLabel = new Label(changePercentage.toString() + "%");
      if (changePercentage.signum() == 0) {
        changePercentLabel.getStyleClass().add("standard-text");
      } else if (changePercentage.signum() == 1) {
        changePercentLabel.getStyleClass().add("green-text");
      } else {
        changePercentLabel.getStyleClass().add("red-text");
      }
      changePercentLabel.getStyleClass().add("sub-text");

      Label weekLabel = new Label((i+1) + " week(s) ago");
      weekLabel.getStyleClass().addAll("sub-text", "light-text");

      this.add(changePercentLabel, i, 1);
      this.add(weekLabel, i, 2);
    }
  }
}
