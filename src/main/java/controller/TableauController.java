package controller;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class TableauController {
    public static void handleClick(KlondikeController controller) {
        controller.setSelection("");
    }

    public static void handleClick(SpiderController controller) {
        controller.setPrevCol(-1);
    }
}
