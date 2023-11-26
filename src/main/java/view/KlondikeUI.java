package view;

import controller.CardController;
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
import java.util.ArrayList;
import java.util.Set;

public class KlondikeUI {
    private Klondike kld;
    private final Scene scene;
    @FXML
    private GridPane table;

    public AnchorPane getRoot() {
        return root;
    }

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

    public ArrayList<CardUI> removeCards(int col, int idx) {
        VBox column = (VBox) table.lookup("#c" + (col + 1));
        ArrayList<CardUI> res = new ArrayList<>();
        while (column.getChildren().size() != idx) {
            res.add((CardUI) column.getChildren().remove(idx));
        }
        if (!column.getChildren().isEmpty()) {
            CardUI cardUI = (CardUI) column.getChildren().get(idx - 1);
            cardUI.draw();
        }
        return res;
    }

    public void addCards(int col, ArrayList<CardUI> arr) {
        VBox column = (VBox) table.lookup("#c" + (col + 1));
        for (CardUI cardUI : arr) {
            column.getChildren().add(cardUI);
            cardUI.setTranslateY(-110 * (column.getChildren().size() - 1));
        }
    }

    public Node getNode(String id) {
        return table.lookup(id);
    }

    public Set<Node> getAllNodes(String classStr) {
        return table.lookupAll(classStr);
    }
}
