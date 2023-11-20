package view;

import javafx.scene.canvas.Canvas;
import model.Suit;

public class CardUI {
    private Canvas canvas;

    public CardUI(String num, Suit suit) {
        String strSuit = "";
        switch (suit) {
            case CLUBS -> strSuit = "C";
            case DIAMONDS -> strSuit = "D";
            case SPADES -> strSuit = "S";
            case HEARTS -> strSuit = "H";
        }
        String path = (num.equals("10") ? "T" : num) + strSuit + ".svg";
        
    }
}
