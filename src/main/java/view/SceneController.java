package view;

import java.io.IOException;

import controller.KlondikeController;
import controller.SpiderController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Klondike;
import model.Spider;

public class SceneController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToSpider(ActionEvent event) throws IOException {
        Spider spd = new Spider((byte) 1);
        SpiderUI view = new SpiderUI(stage, spd);
        SpiderController controller = new SpiderController(view, spd);
    }

    public void switchToKlondike(ActionEvent event) throws IOException {
        Klondike kld = new Klondike((byte) 1);
        KlondikeUI view = new KlondikeUI(stage, kld);
        KlondikeController controller = new KlondikeController(view, kld);
    }

    public void switchToMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/menu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}