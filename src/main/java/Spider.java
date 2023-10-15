import java.util.ArrayList;
import java.util.Arrays;

public class Spider implements Solitaire {

    private final int tableauCols = 10;
    private final Deck deck;
    private Foundation foundation = new Foundation(8);
    private Tableau tableau = new Tableau(tableauCols);

    public Spider(byte suits) {
        //We represent the Tableau with a matrix made by two arrays, the tableau array and multiple aux arrays
        //In each position of the tableau array we add an aux array, making the tableau positions the columns
        //and the arrays innit (the aux arrays) the rows.
        deck = new Deck(suits, (byte) 2);
        deck.shuffle();
        addCards();
    }

    public Spider(byte suits, int seed) {
        //We represent the Tableau at a particular state with a matrix made by two arrays, the tableau array and multiple aux arrays
        //In each position of the tableau array we add an aux array, making the tableau positions the columns
        //and the arrays innit (the aux arrays) the rows.
        deck = new Deck(suits, (byte) 2);
        deck.shuffle(seed);
        addCards();
    }

    private void addCards() {
        for (int i = 0; i < tableauCols; i++) {
            Card top = null;
            for (int j = 0; j < 5; j++) {
                top = deck.getCard();
                tableau.addCard(top, i);
            }
            if (i < 4) {
                top = deck.getCard();
                tableau.addCard(top, i);
            }
            top.makeItVisible();
        }
    }

    public Spider(Deck deck, Tableau tableau, Foundation foundation) {
        this.deck = deck;
        this.tableau = tableau;
        this.foundation = foundation;
    }

    @Override
    public ArrayList<Card> getCards() {
        ArrayList<Card> res = new ArrayList<>(tableauCols);
        for (int i = 0; i < tableauCols; i++) {
            if (deck.isEmpty()) return res;
            Card card = deck.getCard();
            card.makeItVisible();
            tableau.addCard(card, i);
            res.add(card);
        }
        return res;
    }

    @Override
    public boolean move(int col, int idx, int dest) {
        int realCol = col - 1;
        int realIdx = idx - 1;
        int realDest = dest - 1;
        if (!validMove(realCol, realIdx, realDest)) {
            return false;
        }
        tableau.move(realCol, realIdx, realDest);
        if (tableau.colSize(realDest) >= 13) {
            checkSequence(realDest);
        }
        return true;
    }

    @Override
    public boolean validMove(int col, int idx, int dest) {
        //Returns true or false in case the move is valid or not.

        if (col >= tableauCols || dest >= tableauCols ||
                idx >= tableau.colSize(col) || !tableau.getCard(col, idx).isVisible()) {
            return false;
        }

        Card cardOrigin = tableau.getCard(col, idx);
        Card cardDestination = tableau.colSize(dest) == 0 ? null : tableau.getCard(dest);

        return rightOrder(cardOrigin, cardDestination) && validSlice(col, idx);
    }

    private boolean rightOrder(Card cardOrigin, Card cardDestination) {
        //Reminder: two cards are stackable even tho they are not from the same suit.
        //Reminder: we can stack any card above an empty space.
        if (cardDestination == null) {
            return true;
        }
        if (cardOrigin.getNum().equals("K")) return false;

        ArrayList<String> orderedDeck = new ArrayList<>(
                Arrays.asList("K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2", "A")
        );
        return cardDestination.getNum().equals(orderedDeck.get(orderedDeck.indexOf(cardOrigin.getNum()) - 1));
    }

    private boolean validSlice(int col, int idx) {
        //We verify if we can move the whole slice, in order to do that, we have to verify that it's
        //ordered and all from the same suit.
        Card card1;
        Card card2;

        card1 = tableau.getCard(col, idx);
        for (int i = idx + 1; i < tableau.colSize(col); i++) {
            card2 = tableau.getCard(col, i);
            if (card1.getSuit() != card2.getSuit() || !rightOrder(card2, card1)) {
                return false;
            }
            card1 = card2;
        }

        return true;
    }

    private void checkSequence(int dest) {
        ArrayList<String> order = new ArrayList<>(
                Arrays.asList("K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2", "A")
        );
        int idx = 12;
        Suit suit = tableau.getCard(dest).getSuit();
        for (int i = tableau.colSize(dest) - 1; i >= tableau.colSize(dest) - 13; i--) {
            Card card = tableau.getCard(dest, i);
            if (!card.getNum().equals(order.get(idx)) || card.getSuit() != suit) {
                return;
            }
            idx--;
        }
        ArrayList<Card> stack = tableau.removeCards(dest, tableau.colSize(dest) - 13);
        foundation.addStack(stack);
    }

    @Override
    public boolean victory() {
        return foundation.size() == 8;
    }
}
