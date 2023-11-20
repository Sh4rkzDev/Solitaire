package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import model.Suit;

public class CardUI {
    private final double width = 120;
    private final double height = 200;

    private final Canvas canvas = new Canvas(width, height);


    public CardUI(String num, Suit suit) {
        String strSuit = "";
        switch (suit) {
            case CLUBS -> strSuit = "C";
            case DIAMONDS -> strSuit = "D";
            case SPADES -> strSuit = "S";
            case HEARTS -> strSuit = "H";
        }
        Image img = new Image("src/main/java/resources/" + (num.equals("10") ? "T" : num) + strSuit + ".png");
        canvas.getGraphicsContext2D().drawImage(img, width, height);
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
