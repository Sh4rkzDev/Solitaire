package controller;

import java.io.File;
import java.io.IOException;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import model.Klondike;
import model.Spider;
import view.KlondikeUI;
import view.MenuUI;
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

    /*
Alert dialogoConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
dialogoConfirmacion.setTitle("Diálogo de confirmación");
dialogoConfirmacion.setHeaderText("Cabecera del cuadro de diálogo de confirmación personalizado");
dialogoConfirmacion.setContentText("Contenido del cuadro de diálogo de personalizado");
ButtonType boton1 = new ButtonType("Botón 1");
ButtonType boton2 = new ButtonType("Botón 2");
ButtonType boton3 = new ButtonType("Botón 3");
ButtonType boton4 = new ButtonType("Botón 4");
dialogoConfirmacion.getButtonTypes().setAll(boton1, boton2, boton3, boton4);
Optional<ButtonType> opciones = dialogoConfirmacion.showAndWait();
if (opciones.get() == boton1) {
    System.out.println("Pulsado botón 1");
} else if (opciones.get() == boton2) {
    System.out.println("Pulsado botón 2");
} else if (opciones.get() == boton3) {
    System.out.println("Pulsado botón 3");
} else if (opciones.get() == boton4) {
    System.out.println("Pulsado botón 4");
}
*/

    public void switchToSpider(Scene scene) {
        File spdFile = new File("mySpider");
        Spider spd;
        if (spdFile.exists()) {
            try {
                spd = Spider.deserialize("mySpider");
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else spd = new Spider((byte) 4);
        SpiderUI view = new SpiderUI(scene, spd);
        SpiderController controller = new SpiderController(view, spd, this);
    }

    public void switchToKlondike(Scene scene) {
        File kldFile = new File("myKlondike");
        Klondike kld;
        if (kldFile.exists()) {
            try {
                kld = Klondike.deserialize("myKlondike");
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else kld = new Klondike((byte) 3);
        KlondikeUI view = new KlondikeUI(scene, kld);
        KlondikeController controller = new KlondikeController(view, kld, this);
    }


    public void switchToMenu() {
        scene.setRoot(root);
    }
}