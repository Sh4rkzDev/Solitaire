package controller;

import model.Spider;
import view.View;

public class SpiderController {
    private View view;
    private Spider sp;

    public SpiderController(View view) {
        this.view = view;
        sp = new Spider();
    }


}
