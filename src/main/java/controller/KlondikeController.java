package controller;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.Klondike;
import view.CardUI;
import view.KlondikeUI;

import java.util.ArrayList;

public class KlondikeController {
    private final KlondikeUI view;
    private final Klondike kld;
    private final DeckController deckController;
    private String selection = "";
    private int prevCol;
    private int prevIdx;

    public KlondikeController(KlondikeUI view, Klondike kld, CardController cardController) {
        this.view = view;
        this.kld = kld;
        deckController = new DeckController(kld);
        deckController.setUi(view);
        deckController.setCardController(cardController);
        AnchorPane root = view.getRoot();
        root.setOnMouseClicked(event -> TableauController.handleClick(this));
        view.getAllNodes(".column").forEach(node -> node.setOnMouseClicked(event -> {
            if (selection.isEmpty()) return;
            int col = Character.getNumericValue(node.getId().charAt(1)) - 1;
            move(col);
            event.consume();
            selection = "";
        }));
        view.getAllNodes(".foundation").forEach(node -> node.setOnMouseClicked(event -> {
            FoundationController.handleClick(this);
        }));
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

    public String getSelection() {
        return selection;
    }

    public void setPrevCol(int prevCol) {
        this.prevCol = prevCol;
    }

    public int getPrevIdx() {
        return prevIdx;
    }

    public void setPrevIdx(int prevIdx) {
        this.prevIdx = prevIdx;
    }

    public void move(int dest) {
        switch (selection) {
            case "tableau" -> {
                if (kld.move(prevCol, prevIdx, dest)) {
                    var aux = view.removeCards(prevCol, prevIdx);
                    view.addCards(dest, aux);
                }
            }
            case "waste" -> {
                if (kld.moveFromWasteToTableau(dest)) {
                    var arr = new ArrayList<CardUI>();
                    StackPane waste = (StackPane) view.getNode("#waste");
                    arr.add((CardUI) waste.getChildren().removeLast());
                    view.addCards(dest, arr);
                }
            }
        }
        selection = "";
    }

    public void moveToFoundation() {
        switch (selection) {
            case "waste" -> {
                if (!kld.moveFromWasteToFoundation()) return;
                StackPane waste = (StackPane) view.getNode("#waste");
                CardUI cardUI = (CardUI) waste.getChildren().removeLast();
                int ordinal = cardUI.getSuit().ordinal();
                Canvas found = (Canvas) view.getNode("#f" + (ordinal + 1));
                found.getGraphicsContext2D().drawImage(cardUI.getImg(), 0, 0);
                cardUI.setMouseTransparent(true);
            }
            case "tableau" -> {
                VBox column = (VBox) view.getNode("#c" + (prevCol + 1));
                if (prevIdx != (column.getChildren().size() - 1) || !kld.moveFromTableauToFoundation(prevCol)) return;
                CardUI cardUI = view.removeCards(prevCol, prevIdx).get(0);
                int ordinal = cardUI.getSuit().ordinal();
                Canvas found = (Canvas) view.getNode("#f" + (ordinal + 1));
                found.getGraphicsContext2D().drawImage(cardUI.getImg(), 0, 0);
                cardUI.setMouseTransparent(true);
            }
        }
    }
}
