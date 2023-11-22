package controller;

import model.Klondike;
import view.KlondikeUI;

public class KlondikeController {
    private final KlondikeUI view;
    private final Klondike kld;
    private final DeckController deckController;
    boolean selection;

    public KlondikeController(KlondikeUI view, Klondike kld) {
        this.view = view;
        this.kld = kld;
        deckController = new DeckController(kld);
        deckController.setUi(view);
    }

    public void startGame() {

    }
}
