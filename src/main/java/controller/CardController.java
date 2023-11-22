package controller;

import javafx.scene.layout.VBox;
import view.CardUI;

public class CardController {
    public CardController() {
    }

    public void handleClick(CardUI card) {
        VBox col = (VBox) card.getParent();
        System.out.println("COL: " + col.getId() + "   IDX: " + (col.getChildren().indexOf(card) + 1));
    }
}
