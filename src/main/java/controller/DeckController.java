package controller;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import model.Klondike;
import view.CardUI;
import view.KlondikeUI;

public class    DeckController {
    private Klondike kld;
    private KlondikeUI ui;

    public void setCardController(CardController cardController) {
        this.cardController = cardController;
    }

    private CardController cardController;

    public DeckController(Klondike kld) {
        this.kld = kld;
    }

    public void setUi(KlondikeUI ui) {
        this.ui = ui;
        Canvas deck = (Canvas) ui.getNode("#deck");
        deck.setOnMouseClicked(event -> handleClick(deck));
    }

    public void handleClick(Canvas deck) {
        var added = kld.getCards();
        if (added.isEmpty()) {
            deck.setVisible(false);
        } else {
            CardUI cardUI = new CardUI(added.get(0));
            StackPane waste = (StackPane) ui.getNode("#waste");
            waste.getChildren().add(cardUI);
            cardUI.draw();
            cardUI.setOnMouseClicked(event -> {
                cardController.handleClick(cardUI);
                event.consume();
            });
        }
    }
}
