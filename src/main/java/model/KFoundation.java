package model;

import java.io.Serializable;
import java.util.ArrayList;

public class KFoundation extends Foundation implements Serializable {

    public KFoundation() {
        super(4);
        for (int i = 0; i < 4; i++) {
            foundation.add(new ArrayList<>(13));
        }
    }

    public Card getCard(int col) {
        return foundation.get(col).get(foundation.get(col).size() - 1);
    }

    public void addCard(Card card, int col) {
        foundation.get(col).add(card);
    }

    public int colSize(int col) {
        return foundation.get(col).size();
    }
}
