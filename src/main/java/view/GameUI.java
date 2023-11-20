package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class GameUI {
    private final Scene scene;
    @FXML
    private GridPane tableau;
    @FXML
    private AnchorPane table;

    public GameUI() {
        var loader = new FXMLLoader(getClass().getResource("spider.fxml"));
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene = new Scene(table, table.getWidth(), table.getHeight());
        scene.getStylesheets().add(String.valueOf(this.getClass().getResource("styles.css")));
    }
}
