package controller;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.Card;
import model.Spider;
import model.Tableau;
import view.CardUI;
import view.SpiderUI;

import java.util.ArrayList;

public class  SpdDeckController {
    private Spider spd;
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
        deck.setOnMouseClicked(event -> handleClick(deck, spd, table, controller));
    }


    public void handleClick(Canvas deck, Spider spd, GridPane table, CardController controller) {
        var added = spd.getCards();
        if (added.isEmpty()) {
            deck.setVisible(false);
        } else {
            Tableau tableau = spd.getTableau();
            for (int i = 0; i < 10; i++) {
                VBox col = (VBox) table.lookup("#c" + (i + 1));
                CardUI cardUI = new CardUI(added.get(i));
                col.getChildren().add(cardUI);
                cardUI.draw();
                cardUI.setTranslateY(-110 * tableau.colSize(i));
                cardUI.registerListener(controller);
                cardUI.setOnMouseClicked(event -> {
                    spdCardController.handleClick(cardUI);
                    event.consume();
                });
            }

        }
    }
}