package controller;

import javafx.scene.Scene;
import view.VictoryUI;

public class VictoryController {
    private final VictoryUI ui;

    public VictoryController(Scene scene, MenuController menuController) {
        ui = new VictoryUI(scene);
        ui.getButton().setOnAction(actionEvent -> menuController.switchToMenu());
    }

    public void switchScene() {
        ui.setScene();
    }
}
