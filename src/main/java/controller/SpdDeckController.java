package controller;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.VBox;
import model.Spider;
import view.CardUI;
import view.SpiderUI;

public class SpdDeckController {
    private final Spider spd;
    private SpiderUI ui;

    public void setCardController(SpdCardController spdCardController) {
        this.spdCardController = spdCardController;
    }

    private SpdCardController spdCardController;

    public SpdDeckController(Spider spd) {
        this.spd = spd;
    }

    public void setUi(SpiderUI ui) {
        this.ui = ui;
        Canvas deck = (Canvas) ui.getNode("#deck");
        deck.setOnMouseClicked(event -> handleClick(deck));
    }


    public void handleClick(Canvas deck) {
        var added = spd.getCards();
        if (spd.getDeck().isEmpty()) deck.setVisible(false);
        if (added.isEmpty()) return;
        for (int i = 0; i < 10; i++) {
            VBox col = (VBox) ui.getNode("#c" + (i + 1));
            CardUI cardUI = new CardUI(added.get(i));
            col.getChildren().add(cardUI);
            cardUI.draw();
            cardUI.setTranslateY(-110 * (col.getChildren().size() - 1));
            cardUI.setOnMouseClicked(event -> {
                spdCardController.handleClick(cardUI);
                event.consume();
            });
        }
    }
}
