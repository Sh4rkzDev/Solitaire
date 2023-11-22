package view;

import controller.CardController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import model.Card;
import model.Suit;

public class CardUI extends Canvas {
    Image img;
    Card card;
    private final double height = 140;
    private final double width = 100;

    public CardUI(Card card) {
        super(100, 140);
        this.card = card;
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
            getGraphicsContext2D().drawImage(new Image(String.valueOf(getClass().getResource("/img/1B.png"))), 0, 0);
            return;
        }
        getGraphicsContext2D().drawImage(img, 0, 0);
    }

    public void registerListener(CardController controller) {
        setOnMouseClicked(event -> controller.handleClick(this));
    }
}
