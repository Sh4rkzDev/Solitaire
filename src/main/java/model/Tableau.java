package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Tableau implements Serializable {
    private final ArrayList<Column> tableau;

    public Tableau(int cols) {
        tableau = new ArrayList<>(cols);
        for (int i = 0; i < cols; i++) {
            tableau.add(new Column());
        }
    }

    /**
     * Add the given card to the given column
     *
     * @param card The card to be added.
     * @param col  The column where the card will be added.
     */
    public void addCard(Card card, int col) {
        tableau.get(col).addCard(card);
    }

    /**
     * It removes the cards from the given column and put them to destination column.
     *
     * @param col  The origin column where the cards will be extracted.
     * @param idx  The index from the model.Column where the cards will be extracted to the end.
     * @param dest The column where the extracted cards will be placed.
     */
    public void move(int col, int idx, int dest) {
        ArrayList<Card> stack = removeCards(col, idx);
        tableau.get(dest).addCard(stack);
    }

    public int colSize(int col) {
        return tableau.get(col).size();
    }

    public Card getCard(int col) {
        return tableau.get(col).getCard(colSize(col) - 1);
    }

    public Card getCard(int col, int idx) {
        return tableau.get(col).getCard(idx);
    }

    public ArrayList<Card> removeCards(int col, int idx) {
        return tableau.get(col).removeCards(idx);
    }
}
