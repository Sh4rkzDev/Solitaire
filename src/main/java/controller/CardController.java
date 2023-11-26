package controller;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Klondike;
import view.CardUI;
import view.KlondikeUI;

public class CardController {
    private Klondike kld;
    private KlondikeUI ui;
    private KlondikeController kldController;

    public CardController(Klondike kld) {
        this.kld = kld;
    }

    public void setKldController(KlondikeController kldController) {
        this.kldController = kldController;
    }

    public void setUi(KlondikeUI ui) {
        this.ui = ui;
    }

    public void handleClick(CardUI card) {
        Pane column = (Pane) card.getParent();
        int col = Character.getNumericValue(column.getId().charAt(1)) - 1;
        if (!kldController.getSelection().isEmpty() && col >= 0) {
            kldController.move(col);
            return;
        }
        int idx = column.getChildren().indexOf(card);
        if (card.getParent().getStyleClass().get(0).equals("column") && !kld.validSlice(col, idx)) {
            return;
        }
        kldController.setSelection(
                switch (card.getParent().getStyleClass().get(0)) {
                    case "column" -> "tableau";
                    case "waste" -> "waste";
                    default -> "";
                }
        );
        kldController.setPrevCol(col);
        kldController.setPrevIdx(idx);
    }
}
