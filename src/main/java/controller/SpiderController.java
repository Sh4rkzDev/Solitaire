package controller;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.Spider;
import view.SpiderUI;

import java.io.IOException;

public class SpiderController {
    private final SpiderUI view;
    private static Spider spd;
    private int prevCol = -1;
    private int prevIdx;

    public SpiderController(SpiderUI view, Spider spd, MenuController menuController) {
        this.view = view;
        this.spd = spd;
        SpdDeckController spdDeckController = new SpdDeckController(spd);
        spdDeckController.setUi(view);
        SpdCardController spdCardController = new SpdCardController(spd);
        view.loadGame(spdCardController);
        spdDeckController.setCardController(spdCardController);
        spdCardController.setSpdController(this);
        AnchorPane root = view.getRoot();
        root.setOnMouseClicked(event -> TableauController.handleClick(this));
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

    public void move(int dest) {
        if (spd.move(prevCol, prevIdx, dest)) {
            var aux = view.removeCards(prevCol, prevIdx);
            view.addCards(dest, aux);
            if (spd.lastRemovedToFoundation()) {
                VBox col = (VBox) view.getNode("#c" + (dest + 1));
                var stack = view.removeCards(dest, col.getChildren().size() - 13);
                Canvas foundation = (Canvas) view.getNode("#f" + (spd.getFoundation().size()));
                foundation.getGraphicsContext2D().drawImage(stack.get(0).getImg(), 0, 0);
            }
        }
        prevCol = -1;
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

