package controller;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.Spider;
import view.SpiderUI;

import java.io.IOException;

public class SpiderController {
    private final SpiderUI view;
    private final Spider spd;
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
            try {
                spd.serialize("mySpider");
            } catch (IOException e) {
                System.out.println("No se pudo guardar el juego Spider");
            }
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
            if (spd.checkSequence(dest)) {
                VBox col = (VBox) view.getNode("#c" + dest);
                view.removeCards(dest, col.getChildren().size() - 13);
            }
        }
        prevCol = -1;
    }


}

