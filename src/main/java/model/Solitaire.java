package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class Solitaire implements Serialization, Serializable {
    protected final byte tableauCols;
    protected Deck deck;
    protected Foundation foundation;
    protected Tableau tableau;
    protected ArrayList<String> orderedDeck = new ArrayList<>(
            Arrays.asList("K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2", "A")
    );

    public Solitaire(byte suits, byte decks, byte tableauCols, byte foundationCols) {
        this.deck = new Deck(suits, decks);
        this.tableau = new Tableau(tableauCols);
        this.tableauCols = tableauCols;
        this.foundation = new Foundation(foundationCols);
        deck.shuffle();
        addCards();
    }

    public Solitaire(byte suits, byte decks, byte tableauCols, byte foundationCols, int seed) {
        this.deck = new Deck(suits, decks);
        this.tableau = new Tableau(tableauCols);
        this.tableauCols = tableauCols;
        this.foundation = new Foundation(foundationCols);
        deck.shuffle(seed);
        addCards();
    }

    public Solitaire(Deck deck, Tableau tableau, Foundation foundation, byte tableauCols) {
        this.deck = deck;
        this.tableau = tableau;
        this.foundation = foundation;
        this.tableauCols = tableauCols;
    }

    protected abstract void addCards();

    protected boolean validMove(int col, int idx, int dest) {
        if (col >= tableauCols || dest >= tableauCols ||
                idx >= tableau.colSize(col) || !tableau.getCard(col, idx).isVisible()) {
            return false;
        }

        Card cardOrigin = tableau.getCard(col, idx);
        Card cardDestination = tableau.colSize(dest) == 0 ? null : tableau.getCard(dest);

        return rightOrder(cardOrigin, cardDestination) && validSlice(col, idx);
    }

    protected abstract boolean rightOrder(Card cardOrigin, Card cardDestination);

    protected abstract boolean validSlice(int col, int idx);

    public abstract ArrayList<Card> getCards();

    public abstract boolean move(int tableauCol, int idx, int tableauColDestination);

    public abstract boolean victory();
}
