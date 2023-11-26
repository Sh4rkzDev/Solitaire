package controller;


import javafx.scene.layout.AnchorPane;
import model.Spider;
import view.SpiderUI;

import java.util.ArrayList;

public class SpiderController {
    private final SpiderUI view;
    private final Spider spd;
    private final SpdDeckController spdDeckController;
    private int prevCol;
    private int prevIdx;

    public SpiderController(SpiderUI view, Spider spd, SpdCardController spdCardController) {
        this.view = view;
        this.spd = spd;
        spdDeckController = new SpdDeckController(spd);
        spdDeckController.setUi(view);
        spdDeckController.setCardController(spdCardController);
        AnchorPane root = view.getRoot();
        view.getAllNodes(".column").forEach(node -> node.setOnMouseClicked(event -> {
            int col = Character.getNumericValue(node.getId().charAt(1)) - 1;
            move(col);
            event.consume();
        }));
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
        if (spd.move(prevCol, prevIdx, dest)) {
            var aux = view.removeCards(prevCol, prevIdx);
            view.addCards(dest, aux);
        }
    }



}

