package controller;

import model.Spider;
import view.GameUI;

public class SpiderController {
    private GameUI view;
    private Spider sp;

    public SpiderController(GameUI view) {
        this.view = view;
        sp = new Spider();
    }


}
