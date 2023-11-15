package controller;

import model.Klondike;
import view.View;

public class KlondikeController {

    private View view;
    private Klondike kld;

    public KlondikeController(View view) {
        this.view = view;
        kld = new Klondike();
    }
}
