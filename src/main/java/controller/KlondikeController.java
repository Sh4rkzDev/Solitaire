package controller;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.Klondike;
import view.CardUI;
import view.KlondikeUI;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class KlondikeController {
    private final KlondikeUI view;
    private static Klondike kld;
    private String selection = "";
    private int prevCol;
    private int prevIdx;
    private final VictoryController victoryController;

    public KlondikeController(KlondikeUI view, Klondike kld, MenuController menuController) {
        this.view = view;
        this.kld = kld;
        this.victoryController = new VictoryController(view.getScene(), menuController);

        DeckController deckController = new DeckController(kld);
        deckController.setUi(view);
        CardController cardController = new CardController(kld);
        view.loadGame(cardController);
        deckController.setCardController(cardController);
        cardController.setKldController(this);
        AnchorPane root = view.getRoot();
        root.setOnMouseClicked(event -> removeSelection());
        Button menu = (Button) view.getNode("#menuButton");
        menu.setOnAction(actionEvent -> {
            save();
            menuController.switchToMenu();
        });
        view.getAllNodes(".column").forEach(node -> node.setOnMouseClicked(event -> {
            if (selection.isEmpty()) return;
            int col = Character.getNumericValue(node.getId().charAt(1)) - 1;
            move(col);
            event.consume();
        }));
        view.getAllNodes(".foundation").forEach(node -> node.setOnMouseClicked(event -> moveToFoundation()));
    }

    public String getSelection() {
        return selection;
    }

    public void setPrevCol(int prevCol) {
        this.prevCol = prevCol;
    }

    public void setPrevIdx(int prevIdx) {
        this.prevIdx = prevIdx;
    }

    public void move(int dest) {
        boolean moved = false;
        switch (selection) {
            case "tableau" -> {
                if (kld.move(prevCol, prevIdx, dest)) {
                    moved = true;
                    var aux = view.removeCards(prevCol, prevIdx);
                    for (CardUI cardUI : aux) {
                        cardUI.removeEffect();
                    }
                    view.addCards(dest, aux);
                }
            }
            case "waste" -> {
                if (kld.moveFromWasteToTableau(dest)) {
                    moved = true;
                    var arr = new ArrayList<CardUI>();
                    StackPane waste = (StackPane) view.getNode("#waste");
                    CardUI cardUI = (CardUI) waste.getChildren().removeLast();
                    cardUI.removeEffect();
                    arr.add(cardUI);
                    view.addCards(dest, arr);
                }
            }
        }
        if (!moved) removeSelection();
        selection = "";
    }

    public void moveToFoundation() {
        boolean moved = false;
        switch (selection) {
            case "waste" -> {
                if (!kld.moveFromWasteToFoundation()) break;
                moved = true;
                StackPane waste = (StackPane) view.getNode("#waste");
                CardUI cardUI = (CardUI) waste.getChildren().removeLast();
                int ordinal = cardUI.getSuit().ordinal();
                Canvas found = (Canvas) view.getNode("#f" + (ordinal + 1));
                found.getGraphicsContext2D().drawImage(cardUI.getImg(), 0, 0);
                cardUI.setMouseTransparent(true);
            }
            case "tableau" -> {
                VBox column = (VBox) view.getNode("#c" + (prevCol + 1));
                if (prevIdx != (column.getChildren().size() - 1) || !kld.moveFromTableauToFoundation(prevCol)) break;
                moved = true;
                CardUI cardUI = view.removeCards(prevCol, prevIdx).get(0);
                int ordinal = cardUI.getSuit().ordinal();
                Canvas found = (Canvas) view.getNode("#f" + (ordinal + 1));
                found.getGraphicsContext2D().drawImage(cardUI.getImg(), 0, 0);
                cardUI.setMouseTransparent(true);
            }
        }
        checkVictory();
        if (!moved) removeSelection();
        selection = "";
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

    public void removeSelection() {
        switch (selection) {
            case "waste" -> {
                StackPane waste = (StackPane) view.getNode("#waste");
                CardUI cardUI = (CardUI) waste.getChildren().get(waste.getChildren().size() - 1);
                cardUI.removeEffect();
            }
            case "tableau" -> {
                VBox column = (VBox) view.getNode("#c" + (prevCol + 1));
                for (int i = prevIdx; i < column.getChildren().size(); i++) {
                    CardUI cardUI = (CardUI) column.getChildren().get(i);
                    cardUI.removeEffect();
                }
            }
        }
        selection = "";
    }

    private void checkVictory() {
        if (!kld.victory()) return;
        File file = new File("src/main/resources/games/myKlondike");
        if (file.exists()) file.delete();
        victoryController.switchScene();
    }

    public static void save() {
        if (kld == null) return;
        try {
            kld.serialize("src/main/resources/games/myKlondike");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
