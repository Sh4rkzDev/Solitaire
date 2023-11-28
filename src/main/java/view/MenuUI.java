package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;

import java.io.IOException;

public class MenuUI {
    private final Scene scene;
    @FXML
    private FlowPane root;

    public MenuUI() {
        var loader = new FXMLLoader(getClass().getResource("/menu.fxml"));
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene = new Scene(root, root.getWidth(), root.getHeight());
        scene.getStylesheets().add(String.valueOf(this.getClass().getResource("/styles.css")));
    }

    public Scene getScene() {
        return scene;
    }

    public Node getNode(String id) {
        return root.lookup(id);
    }
}
