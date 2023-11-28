package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Waste implements Serializable {

    private ArrayList<Card> waste;

    public Waste() {
        waste = new ArrayList<>();
    }

    public boolean isEmpty() {
        return waste.isEmpty();
    }

    public void addCard(Card card) {
        waste.add(card);
    }

    public Card getCard() {
        return waste.get(waste.size() - 1);
    }

    public Card removeCard() {
        return waste.remove(waste.size() - 1);
    }

    public Deck toDeck() {
        Collections.reverse(waste);
        Deck res = new Deck(waste);
        waste = new ArrayList<>();
        return res;
    }

    public ArrayList<Card> getWaste() {
        return waste;
    }
}
