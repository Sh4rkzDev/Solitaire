package controller;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Spider;
import view.CardUI;
import view.SpiderUI;

public class SpdCardController {
    private Spider spd;
    private SpiderUI ui;
    private SpiderController spdController;

    public SpdCardController(Spider spd) {
        this.spd = spd;
    }

    public void setSpdController(SpiderController spdController) {
        this.spdController = spdController;
    }

    public void setUi(SpiderUI ui) {
        this.ui = ui;
    }

    public void handleClick(CardUI card) {
        Pane column = (Pane) card.getParent();
        int col = Character.getNumericValue(column.getId().charAt(1)) - 1;
        if (col >= 0) {
            spdController.move(col);
            return;
        }
        int idx = column.getChildren().indexOf(card);
        if (card.getParent().getStyleClass().get(0).equals("column") && !spd.validSlice(col, idx)) {
            return;
        }
        spdController.setPrevCol(col);
        spdController.setPrevIdx(idx);
    }
}
