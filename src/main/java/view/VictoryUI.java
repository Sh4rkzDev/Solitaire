package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class VictoryUI {
    @FXML
    private VBox root;
    @FXML
    private Button menuButton;
    private final Scene scene;

    public VictoryUI(Scene scene) {
        this.scene = scene;
        var loader = new FXMLLoader(getClass().getResource("/victory.fxml"));
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Button getButton() {
        return menuButton;
    }

    public void setScene() {
        scene.setRoot(root);
        scene.getStylesheets().add(String.valueOf(this.getClass().getResource("/styles.css")));
    }
}
