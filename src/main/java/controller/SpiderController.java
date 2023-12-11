package controller;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.Spider;
import view.CardUI;
import view.SpiderUI;

import java.io.File;
import java.io.IOException;

public class SpiderController {
    private final SpiderUI view;
    private static Spider spd;
    private int prevCol = -1;
    private int prevIdx;
    private final VictoryController victoryController;

    public SpiderController(SpiderUI view, Spider spd, MenuController menuController) {
        this.view = view;
        this.spd = spd;
        this.victoryController = new VictoryController(view.getScene(), menuController);

        SpdDeckController spdDeckController = new SpdDeckController(spd);
        spdDeckController.setUi(view);
        SpdCardController spdCardController = new SpdCardController(spd);
        view.loadGame(spdCardController);
        spdDeckController.setCardController(spdCardController);
        spdCardController.setSpdController(this);
        AnchorPane root = view.getRoot();
        root.setOnMouseClicked(event -> removeSelection());
        Button menu = (Button) view.getNode("#menuButton");
        menu.setOnAction(actionEvent -> {
            save();
            menuController.switchToMenu();
        });
        view.getAllNodes(".column").forEach(node -> node.setOnMouseClicked(event -> {
            if (prevCol < 0) return;
            int col = Character.getNumericValue(node.getId().charAt(1)) - 1;
            move(col);
            event.consume();
            prevCol = -1;
        }));
    }

    public void setPrevCol(int prevCol) {
        this.prevCol = prevCol;
    }

    public int getPrevCol() {
        return prevCol;
    }

    public void setPrevIdx(int prevIdx) {
        this.prevIdx = prevIdx;
    }

    public void removeSelection() {
        if (prevCol != -1) {
            VBox column = (VBox) view.getNode("#c" + (prevCol + 1));
            for (int i = prevIdx; i < column.getChildren().size(); i++) {
                CardUI cardUI = (CardUI) column.getChildren().get(i);
                cardUI.removeEffect();
            }
        }
        prevCol = -1;
    }

    public void move(int dest) {
        boolean moved = false;
        if (spd.move(prevCol, prevIdx, dest)) {
            moved = true;
            var aux = view.removeCards(prevCol, prevIdx);
            for (CardUI cardUI : aux) {
                cardUI.removeEffect();
            }
            view.addCards(dest, aux);
            if (spd.lastRemovedToFoundation()) {
                VBox col = (VBox) view.getNode("#c" + (dest + 1));
                var stack = view.removeCards(dest, col.getChildren().size() - 13);
                Canvas foundation = (Canvas) view.getNode("#f" + (spd.getFoundation().size()));
                foundation.getGraphicsContext2D().drawImage(stack.get(0).getImg(), 0, 0);
            }
        }
        if (!moved) removeSelection();
        prevCol = -1;
        checkVictory();
    }

    private void checkVictory() {
        if (!spd.victory()) return;
        File file = new File("src/main/resources/games/mySpider");
        if (file.exists()) file.delete();
        victoryController.switchScene();
    }

    public static void save() {
        if (spd == null) return;
        try {
            spd.serialize("src/main/resources/games/mySpider");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}