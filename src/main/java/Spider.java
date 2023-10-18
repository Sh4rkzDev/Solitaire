import java.util.ArrayList;

public class Spider extends Solitaire {

    /**
     * Constructor of the Spider Solitaire Game.
     *
     * @param suits The number of suits to be played.
     */
    public Spider(byte suits) {
        super(suits, (byte) 2, (byte) 10, (byte) 8);
    }

    /**
     * Constructor of the Spider Solitaire Game.
     *
     * @param suits The number of suits to be played.
     * @param seed  It can take a seed to generate a specific game scenario
     */
    public Spider(byte suits, int seed) {
        super(suits, (byte) 2, (byte) 10, (byte) 8, seed);
    }

    /**
     * It adds cards to the tableau.
     * The first 4 columns have one more card than the others.
     * The card lying on the top of the column is set visible.
     */
    @Override
    protected void addCards() {
        for (int i = 0; i < tableauCols; i++) {
            Card top = null;
            for (int j = 0; j < 5; j++) {
                top = deck.removeCard();
                tableau.addCard(top, i);
            }
            if (i < 4) {
                top = deck.removeCard();
                tableau.addCard(top, i);
            }
            top.makeItVisible();
        }
    }

    /**
     * Constructor used for testing purpose only.
     *
     * @param deck       Specific Deck to be passed.
     * @param tableau    Specific Tableau to be passed.
     * @param foundation Specific Foundation to be passed.
     */
    public Spider(Deck deck, Tableau tableau, Foundation foundation) {
        super(deck, tableau, foundation, (byte) 10);
    }

    /**
     * Insert one card to each column of the tableau.
     *
     * @return It returns an ArrayList of the cards inserted. Only for testing purposes.
     */
    @Override
    public ArrayList<Card> getCards() {
        ArrayList<Card> res = new ArrayList<>(tableauCols);
        for (int i = 0; i < tableauCols; i++) {
            if (deck.isEmpty()) return res;
            Card card = deck.removeCard();
            card.makeItVisible();
            tableau.addCard(card, i);
            res.add(card);
        }
        return res;
    }

    /**
     * Verify that it is a valid move and carry it out.
     *
     * @param col  The origin column where the cards should be extract from.
     * @param idx  The index from the origin column where the cards from index to end should be extracted.
     * @param dest The destination column where the extracted cards should be placed.
     * @return Returns true if the move was carried out.
     */
    @Override
    public boolean move(int col, int idx, int dest) {
        int realCol = col - 1;
        int realIdx = idx - 1;
        int realDest = dest - 1;
        if (!validMove(realCol, realIdx, realDest)) return false;
        tableau.move(realCol, realIdx, realDest);
        if (tableau.colSize(realDest) >= 13) checkSequence(realDest);
        return true;
    }

    /**
     * Verify that the given cards are in the correct order.
     * <p>
     * Reminder: two cards are stackable even tho they are not from the same suit.
     * <p>
     * Reminder: we can stack any card above an empty space.
     *
     * @param cardOrigin      The card that is supposed to be on top of the another.
     * @param cardDestination The card that is supposed to be under the another.
     * @return Returns true if it is correct. False otherwise.
     */
    @Override
    protected boolean rightOrder(Card cardOrigin, Card cardDestination) {
        if (cardDestination == null) return true;
        if (cardOrigin.getNum().equals("K")) return false;
        return cardDestination.getNum().equals(orderedDeck.get(orderedDeck.indexOf(cardOrigin.getNum()) - 1));
    }

    /**
     * Verify the whole slice is valid to be moved.
     * Reminder: the slice is valid when the cards are in order, and they are all from the same suit.
     *
     * @param col The column of the tableau where the slice is placed.
     * @param idx The index of the column where the slice starts.
     * @return Returns true in case that it is valid. False otherwise.
     */
    @Override
    protected boolean validSlice(int col, int idx) {
        Card card1;
        Card card2;

        card1 = tableau.getCard(col, idx);
        for (int i = idx + 1; i < tableau.colSize(col); i++) {
            card2 = tableau.getCard(col, i);
            if (card1.getSuit() != card2.getSuit() || !rightOrder(card2, card1)) return false;
            card1 = card2;
        }

        return true;
    }

    /**
     * Check if the column where the last move were carried out has the full sequence to be added to the foundation.
     *
     * @param dest The column where the last move was carried out.
     */
    private void checkSequence(int dest) {
        int idx = 12;
        Suit suit = tableau.getCard(dest).getSuit();
        for (int i = tableau.colSize(dest) - 1; i >= tableau.colSize(dest) - 13; i--) {
            Card card = tableau.getCard(dest, i);
            if (!card.getNum().equals(orderedDeck.get(idx)) || card.getSuit() != suit) return;
            idx--;
        }
        ArrayList<Card> stack = tableau.removeCards(dest, tableau.colSize(dest) - 13);
        foundation.addStack(stack);
    }

    @Override
    protected boolean victory() {
        return foundation.size() == 8;
    }
}
