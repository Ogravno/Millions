package edu.ntnu.idatt2003.group16.view;

import edu.ntnu.idatt2003.group16.controller.GameController;
import edu.ntnu.idatt2003.group16.model.investment.Share;
import edu.ntnu.idatt2003.group16.model.transaction.Sale;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

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

    Label buyingPriceLabel = new Label("Buying price: " + share.getPurchasePrice());
    Label currentPriceLabel = new Label("Current price: " + share.getStock().getCurrentPrice());
    Label totalProfitLabel = new Label("Total profit: " // Check if there is a selling fee !!!
        + share.getStock().getCurrentPrice().subtract(
        share.getPurchasePrice())
        .multiply(share.getQuantity()));

    VBox content = new VBox(10);
    content.getChildren().addAll(
      new Label("Stock: " + share.getStock().getCompany()),
        buyingPriceLabel,
        currentPriceLabel,
        totalProfitLabel
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
