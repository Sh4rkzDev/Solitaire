package model;

import java.io.Serializable;

public enum Suit implements Serializable {
    CLUBS("black"), DIAMONDS("red"), SPADES("black"), HEARTS("red");

    public String color;

    Suit(String color) {
        this.color = color;
    }
}
