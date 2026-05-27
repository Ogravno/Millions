package edu.ntnu.idatt2003.group16.view.components;

import javafx.geometry.HPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

/**
 * Layout used for stock cards in the exchange view.
 */
public class StockCardLayout extends GridPane {

  /**
   * Creates the stock card layout.
   */
  public StockCardLayout() {
    ColumnConstraints column1 = new ColumnConstraints();
    column1.setPercentWidth(40);
    column1.setHalignment(HPos.LEFT);

    ColumnConstraints column2 = new ColumnConstraints();
    column2.setPercentWidth(25);
    column2.setHalignment(HPos.LEFT);

    ColumnConstraints column3 = new ColumnConstraints();
    column3.setPercentWidth(15);
    column3.setHalignment(HPos.RIGHT);

    ColumnConstraints column4 = new ColumnConstraints();
    column4.setPercentWidth(20);
    column4.setHalignment(HPos.RIGHT);

    this.getColumnConstraints().addAll(column1, column2, column3, column4);

    RowConstraints row1 = new RowConstraints();
    RowConstraints row2 = new RowConstraints(10);
    RowConstraints row3 = new RowConstraints();
    RowConstraints row4 = new RowConstraints();

    this.getRowConstraints().addAll(row1, row2, row3, row4);
  }
}