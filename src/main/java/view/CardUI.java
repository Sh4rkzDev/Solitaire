package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import model.Suit;

public class CardUI extends Canvas {
    public CardUI(String num, Suit suit) {
        super();
        String strSuit = "";
        switch (suit) {
            case CLUBS -> strSuit = "C";
            case DIAMONDS -> strSuit = "D";
            case SPADES -> strSuit = "S";
            case HEARTS -> strSuit = "H";
        }
        String path = "/img/" + (num.equals("10") ? "T" : num) + strSuit + ".png";
        Image img = new Image(path);
        double height = 140;
        double width = 100;
        super.getGraphicsContext2D().drawImage(img, width, height);
    }

    public void registerListener(EventHandler<MouseEvent> event) {
        super.setOnMouseClicked(event);
    }
}
