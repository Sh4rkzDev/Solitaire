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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class KlondikeUI {
    private final Klondike kld;

    public Scene getScene() {
        return scene;
    }

    private final Scene scene;
    @FXML
    private GridPane table;

    public AnchorPane getRoot() {
        return root;
    }

    @FXML
    private AnchorPane root;

    public KlondikeUI(Scene scene, Klondike kld) {
        this.kld = kld;
        this.scene = scene;
        var loader = new FXMLLoader(getClass().getResource("/klondike.fxml"));
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene.setRoot(root);
    }

    public void loadGame(CardController controller) {
        scene.getStylesheets().add(String.valueOf(this.getClass().getResource("/styles.css")));

        Canvas deck = (Canvas) table.lookup("#deck");
        if (!kld.getDeck().isEmpty())
            deck.getGraphicsContext2D().drawImage(new Image(String.valueOf(getClass().getResource("/img/1B.png"))), 0, 0);

        StackPane wasteUI = (StackPane) getNode("#waste");
        if (!kld.getWaste().isEmpty()) {
            var kldWaste = kld.getWaste().getWaste();
            for (Card card : kldWaste) {
                CardUI cardUI = new CardUI(card);
                cardUI.draw();
                cardUI.registerListener(controller);
                wasteUI.getChildren().add(cardUI);
            }
        }

        Tableau tableau = kld.getTableau();
        for (int i = 0; i < 7; i++) {
            VBox col = (VBox) table.lookup("#c" + (i + 1));
            for (int j = 0; j < tableau.colSize(i); j++) {
                Card card = tableau.getCard(i, j);
                CardUI cardUI = new CardUI(card);
                col.getChildren().add(cardUI);
                cardUI.draw();
                cardUI.setTranslateY(-110 * j);
                cardUI.registerListener(controller);
            }
        }

        KFoundation foundation = kld.getFoundation();
        for (int i = 0; i < foundation.size(); i++) {
            if (foundation.colSize(i) == 0) continue;
            Canvas actual = (Canvas) getNode("#f" + (i + 1));
            var foundAct = foundation.getCard(i);
            CardUI cardUI = new CardUI(foundAct);
            actual.getGraphicsContext2D().drawImage(cardUI.getImg(), 0, 0);
            cardUI.setMouseTransparent(true);
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
