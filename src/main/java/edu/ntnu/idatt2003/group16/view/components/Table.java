package edu.ntnu.idatt2003.group16.view.components;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class Table extends GridPane {
  public Table(int columns) {
    ColumnConstraints columnConstraints = new ColumnConstraints();
    columnConstraints.setPercentWidth((double) 100 / columns);

    for (int i = 0; i < columns; i++) {
      this.getColumnConstraints().add(columnConstraints);
    }
  }
}
