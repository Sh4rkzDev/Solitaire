package controller;

import java.io.File;
import java.io.IOException;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import model.Klondike;
import model.Spider;
import view.KlondikeUI;
import view.MenuUI;
import view.ModeUI;
import view.SpiderUI;

public class MenuController {

    private final Scene scene;
    private final FlowPane root;

    public MenuController(Stage stage) {
        MenuUI menuUI = new MenuUI();
        scene = menuUI.getScene();
        root = (FlowPane) menuUI.getNode("#root");
        stage.setScene(scene);
        stage.show();
        Button spdButton = (Button) menuUI.getNode("#spdButton");
        spdButton.setOnAction(actionEvent -> switchToSpider(scene));
        Button kldButton = (Button) menuUI.getNode("#kldButton");
        kldButton.setOnAction(actionEvent -> switchToKlondike(scene));
    }


    public void switchToSpider(Scene scene) {
        var gameMode = new ModeUI(scene, "Spider", "One Suit", "Two Suits", "Four Suits");
        File spdFile = new File("src/main/resources/games/mySpider");

        if (!spdFile.exists()) {
            Button load = (Button) gameMode.getNode("#loadButton");
            load.setDisable(true);
        } else {
            Button load = (Button) gameMode.getNode("#loadButton");
            load.setOnAction(actionEvent -> {
                Spider spd;
                try {
                    spd = Spider.deserialize("src/main/resources/games/mySpider");
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                SpiderUI view = new SpiderUI(scene, spd);
                SpiderController controller = new SpiderController(view, spd, this);
            });
        }

        TextField input = (TextField) gameMode.getNode("#input");
        input.setOnKeyTyped(keyEvent -> {
            char num = keyEvent.getCharacter().charAt(0);
            if (!Character.isDigit(num)) {
                keyEvent.consume();
            }
        });

        Button first = (Button) gameMode.getNode("#firstButton");
        first.setOnAction(actionEvent -> {
            Spider spd;
            if (input.getText().isEmpty()) spd = new Spider((byte) 1);
            else spd = new Spider((byte) 1, Integer.parseInt(input.getText()));
            SpiderUI view = new SpiderUI(scene, spd);
            SpiderController controller = new SpiderController(view, spd, this);
        });

        Button second = (Button) gameMode.getNode("#secondButton");
        second.setOnAction(actionEvent -> {
            Spider spd;
            if (input.getText().isEmpty()) spd = new Spider((byte) 2);
            else spd = new Spider((byte) 2, Integer.parseInt(input.getText()));
            SpiderUI view = new SpiderUI(scene, spd);
            SpiderController controller = new SpiderController(view, spd, this);
        });

        Button third = (Button) gameMode.getNode("#thirdButton");
        third.setOnAction(actionEvent -> {
            Spider spd;
            if (input.getText().isEmpty()) spd = new Spider((byte) 4);
            else spd = new Spider((byte) 4, Integer.parseInt(input.getText()));
            SpiderUI view = new SpiderUI(scene, spd);
            SpiderController controller = new SpiderController(view, spd, this);
        });
    }

    public void switchToKlondike(Scene scene) {
        var gameMode = new ModeUI(scene, "Klondike", "One Redeal", "Two Redeals", "Three Redeals");
        File kldFile = new File("src/main/resources/games/myKlondike");

        if (!kldFile.exists()) {
            Button load = (Button) gameMode.getNode("#loadButton");
            load.setDisable(true);
        } else {
            Button load = (Button) gameMode.getNode("#loadButton");
            load.setOnAction(actionEvent -> {
                Klondike kld;
                try {
                    kld = Klondike.deserialize("src/main/resources/games/myKlondike");
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                KlondikeUI view = new KlondikeUI(scene, kld);
                KlondikeController controller = new KlondikeController(view, kld, this);
            });
        }

        TextField input = (TextField) gameMode.getNode("#input");
        input.setOnKeyTyped(keyEvent -> {
            char num = keyEvent.getCharacter().charAt(0);
            if (!Character.isDigit(num)) {
                keyEvent.consume();
            }
        });

        Button first = (Button) gameMode.getNode("#firstButton");
        first.setOnAction(actionEvent -> {
            Klondike kld;
            if (input.getText().isEmpty()) kld = new Klondike((byte) 1);
            else kld = new Klondike((byte) 1, Integer.parseInt(input.getText()));
            KlondikeUI view = new KlondikeUI(scene, kld);
            KlondikeController controller = new KlondikeController(view, kld, this);
        });

        Button second = (Button) gameMode.getNode("#secondButton");
        second.setOnAction(actionEvent -> {
            Klondike kld;
            if (input.getText().isEmpty()) kld = new Klondike((byte) 2);
            else kld = new Klondike((byte) 2, Integer.parseInt(input.getText()));
            KlondikeUI view = new KlondikeUI(scene, kld);
            KlondikeController controller = new KlondikeController(view, kld, this);
        });

        Button third = (Button) gameMode.getNode("#thirdButton");
        third.setOnAction(actionEvent -> {
            Klondike kld;
            if (input.getText().isEmpty()) kld = new Klondike((byte) 3);
            else kld = new Klondike((byte) 3, Integer.parseInt(input.getText()));
            KlondikeUI view = new KlondikeUI(scene, kld);
            KlondikeController controller = new KlondikeController(view, kld, this);
        });
    }

    public void switchToMenu() {
        scene.setRoot(root);
    }
}