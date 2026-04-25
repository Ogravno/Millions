package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.controller.GameController;
import edu.ntnu.idatt2003.group16.model.investment.Share;
import edu.ntnu.idatt2003.group16.model.transaction.Sale;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.Optional;

/**
 * Dialog for selling share.
 */
public class SellDialog extends Dialog<Sale> {

  public SellDialog(GameController gameController, Share share) {
    if (gameController == null) {
      throw new IllegalArgumentException("GameController cannot be null");
    }
    if (share == null) {
      throw new IllegalArgumentException("Share cannot be null");
    }

   setTitle("Sell share");
   setHeaderText("Sell " + share.getStock().getSymbol());

    Label priceLabel = new Label("Price: " + share.getStock().getCurrentPrice());


    VBox content = new VBox(10);
    content.getChildren().addAll(
      new Label("Stock: " + share.getStock().getCompany()),
      priceLabel
    );

    ButtonType sellButtonType = new ButtonType("Sell");

    getDialogPane().setContent(content);
    getDialogPane().getButtonTypes().addAll(sellButtonType, ButtonType.CANCEL);

    setResultConverter(button -> {
      if (button == sellButtonType) {
        return gameController.sellShare(share);
      }
      return null;
    });
  }

  /**
   * Opens Dialog and returns the sale if completed.
   *
   * @return The completed sale, if the user sold its share.
   */
  public Optional<Sale> showAndGetResult() {
    return showAndWait();
  }
}
