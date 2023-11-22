package view;

import controller.CardController;
import controller.DeckController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Card;
import model.Klondike;
import model.Tableau;

import java.io.IOException;

public class KlondikeUI {
    private Klondike kld;
    private final Scene scene;
    private GridPane table;
    @FXML
    private AnchorPane root;

    public KlondikeUI(Stage stage, Klondike kld, CardController controller) {
        this.kld = kld;
        var loader = new FXMLLoader(getClass().getResource("/klondike.fxml"));
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        table = (GridPane) root.getChildren().get(0);
        scene = new Scene(root, root.getWidth(), root.getHeight());
        loadGame(controller);
        stage.setScene(scene);
        stage.show();
    }

    private void loadGame(CardController controller) {
        scene.getStylesheets().add(String.valueOf(this.getClass().getResource("/styles.css")));
        Canvas deck = (Canvas) table.lookup("#deck");
        deck.getGraphicsContext2D().drawImage(new Image(String.valueOf(getClass().getResource("/img/1B.png"))), 0, 0);

        Tableau tableau = kld.getTableau();
        for (int i = 0; i < 7; i++) {
            VBox col = (VBox) table.lookup("#c" + (i + 1));
            for (int j = 0; j < i + 1; j++) {
                Card card = tableau.getCard(i, j);
                CardUI cardUI = new CardUI(card);
                col.getChildren().add(cardUI);
                cardUI.draw();
                cardUI.setTranslateY(-110 * j);
                cardUI.registerListener(controller);
            }
        }
    }

    public Node getNode(String id) {
        return table.lookup(id);
    }
}
