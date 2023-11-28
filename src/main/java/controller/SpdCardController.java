package controller;

import javafx.scene.layout.VBox;
import model.Spider;
import view.CardUI;

public class SpdCardController {
    private final Spider spd;
    private SpiderController spdController;

    public SpdCardController(Spider spd) {
        this.spd = spd;
    }

    public void setSpdController(SpiderController spdController) {
        this.spdController = spdController;
    }

    public void handleClick(CardUI card) {
        VBox column = (VBox) card.getParent();
        int col = Integer.parseInt(column.getId().substring(1)) - 1;
        if (spdController.getPrevCol() >= 0) {
            spdController.move(col);
            return;
        }
        int idx = column.getChildren().indexOf(card);
        if (!spd.validSlice(col, idx)) {
            return;
        }
        spdController.setPrevCol(col);
        spdController.setPrevIdx(idx);
    }
}
