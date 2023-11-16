package controller;

import model.Klondike;
import view.GameUI;

public class KlondikeController {

    private GameUI view;
    private Klondike kld;

    public KlondikeController(GameUI view) {
        this.view = view;
        kld = new Klondike();
    }
}
