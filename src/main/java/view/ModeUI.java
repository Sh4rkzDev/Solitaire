package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ModeUI {
    private String game;
    @FXML
    private VBox root;

    public ModeUI(Scene scene, String game, String first, String second, String third) {
        var loader = new FXMLLoader(getClass().getResource("/mode.fxml"));
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Label solitaire = (Label) getNode("#game");
        solitaire.setText(game);
        Button firstButton = (Button) getNode("#firstButton");
        firstButton.setText(first);
        Button secondButton = (Button) getNode("#secondButton");
        secondButton.setText(second);
        Button thirdButton = (Button) getNode("#thirdButton");
        thirdButton.setText(third);
        scene.setRoot(root);
        scene.getStylesheets().add(String.valueOf(this.getClass().getResource("/styles.css")));
    }

    public Node getNode(String id) {
        return root.lookup(id);
    }
}
