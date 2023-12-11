package view;

import controller.CardController;
import controller.SpdCardController;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import model.Card;
import model.Suit;

public class CardUI extends Pane {
    Image img;
    Card card;
    Canvas canvas;
    private final double height = 140;
    private final double width = 100;

    public CardUI(Card card) {
        super();
        setMinSize(width, height);
        setMaxSize(width, height);
        this.card = card;
        this.canvas = new Canvas(100, 140);
        getChildren().add(canvas);
        String num = card.getNum();
        Suit suit = card.getSuit();
        String strSuit = "";
        switch (suit) {
            case CLUBS -> strSuit = "C";
            case DIAMONDS -> strSuit = "D";
            case SPADES -> strSuit = "S";
            case HEARTS -> strSuit = "H";
        }
        String path = "/img/" + (num.equals("10") ? "T" : num) + strSuit + ".png";
        img = new Image(path);
    }

    public void draw() {
        if (!card.isVisible()) {
            canvas.getGraphicsContext2D().drawImage(new Image(String.valueOf(getClass().getResource("/img/1B.png"))), 0, 0);
            return;
        }
        canvas.getGraphicsContext2D().drawImage(img, 0, 0);
    }

    public void registerListener(CardController controller) {
        setOnMouseClicked(event -> {
            controller.handleClick(this);
            event.consume();
        });
    }

    public void registerListener(SpdCardController controller) {
        setOnMouseClicked(event -> {
            controller.handleClick(this);
            event.consume();
        });
    }

    public Image getImg() {
        return img;
    }

    public void addEffect() {
        canvas.setEffect(new Glow(0.8));
    }

    public void removeEffect() {
        canvas.setEffect(null);
    }

    public Suit getSuit() {
        return card.getSuit();
    }
}
