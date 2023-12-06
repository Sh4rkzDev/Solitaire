package controller;

import javafx.scene.layout.Pane;
import model.Klondike;
import view.CardUI;

public class CardController {
    private final Klondike kld;
    private KlondikeController kldController;

    public CardController(Klondike kld) {
        this.kld = kld;
    }

    public void setKldController(KlondikeController kldController) {
        this.kldController = kldController;
    }

    public void handleClick(CardUI card) {
        Pane column = (Pane) card.getParent();
        int col = Character.getNumericValue(column.getId().charAt(1)) - 1;
        if (!kldController.getSelection().isEmpty()) {
            if (col < 0) kldController.removeSelection();
            else kldController.move(col);
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
        for (int i = idx; i < column.getChildren().size(); i++) {
            CardUI cardUI = (CardUI) column.getChildren().get(i);
            cardUI.addEffect();
        }
    }
}
