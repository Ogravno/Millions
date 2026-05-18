package edu.ntnu.idatt2003.group16.view.components;

import edu.ntnu.idatt2003.group16.controller.GameController;
import edu.ntnu.idatt2003.group16.model.investment.Stock;
import edu.ntnu.idatt2003.group16.view.BuyDialog;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.math.BigDecimal;

public class ExchangeStockCard extends HBox {
  public ExchangeStockCard(Stock stock, GameController gameController) {
    Label symbolLabel = new Label(stock.getSymbol());

    Label companyLabel = new Label(stock.getCompany());

    Label priceLabel = new Label(stock.getCurrentPrice().toString());

    Button buyButton = new Button("Buy");

    buyButton.setOnAction(event -> {
      BuyDialog buyDialog = new BuyDialog(gameController, stock);
      buyDialog.showAndGetResult();
    });

    this.getChildren().addAll(
        symbolLabel,
        companyLabel,
        priceLabel
    );


  }
}
