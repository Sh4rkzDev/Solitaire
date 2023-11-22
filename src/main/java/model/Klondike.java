package model;

import java.io.*;
import java.util.ArrayList;

public class Klondike extends Solitaire {

    protected KFoundation foundation;
    private final Waste waste = new Waste();
    private byte reDeals;

    /**
     * Constructor of the model.Klondike model.Solitaire Game.
     *
     * @param reDeals Number of redeals allow.
     */
    public Klondike(byte reDeals) {
        super((byte) 4, (byte) 1, (byte) 7, (byte) 4);
        foundation = new KFoundation();
        this.reDeals = reDeals;
    }

    /**
     * Constructor of the model.Klondike model.Solitaire Game.
     *
     * @param reDeals Number of redeals allow.
     * @param seed    It can take a seed to generate a specific game scenario.
     */
    public Klondike(byte reDeals, int seed) {
        super((byte) 4, (byte) 1, (byte) 7, (byte) 4, seed);
        foundation = new KFoundation();
        this.reDeals = reDeals;
    }

    /**
     * It adds cards to each column in the form of the model.Klondike tableau:
     * The first column has one card, the second column has two cards, and so on. The card that is on top is visible.
     */
    @Override
    protected void addCards() {
        for (int i = 0; i < tableauCols; i++) {
            Card top = null;
            for (int j = 0; j < i + 1; j++) {
                top = deck.removeCard();
                tableau.addCard(top, i);
            }
            top.makeItVisible();
        }
    }

    /**
     * Constructor used for testing purposes only.
     *
     * @param deck       Specific model.Deck to be passed.
     * @param tableau    Specific model.Tableau to be passed.
     * @param foundation Specific model.Foundation to be passed.
     */
    public Klondike(Deck deck, Tableau tableau, KFoundation foundation) {
        super(deck, tableau, foundation, (byte) 7);
        this.foundation = foundation;
    }

    /**
     * Add a card to the model.Waste. In case of no cards left in the deck, the waste is returned to the deck upside down in case of redeals left.
     *
     * @return Returns an ArrayList of the card that is added to the model.Waste.
     */
    @Override
    public ArrayList<Card> getCards() {
        ArrayList<Card> res = new ArrayList<>();
        if (deck.isEmpty()) {
            if (reDeals == 0) return res;
            deck = waste.toDeck();
            reDeals--;
        }
        Card card = deck.removeCard();
        card.makeItVisible();
        waste.addCard(card);
        res.add(card);
        return res;
    }

    /**
     * Move the card from the model.Waste to the model.Tableau in case of being valid.
     *
     * @param col The column where the card should be put on.
     * @return Returns true if it is valid and the move was executed. False otherwise.
     */
    public boolean moveFromWasteToTableau(int col) {
        if (waste.isEmpty()) return false;
        int realCol = col - 1;
        Card cardOrigin = waste.getCard();
        Card cardDest = tableau.colSize(realCol) == 0 ? null : tableau.getCard(realCol);

        if (!rightOrder(cardOrigin, cardDest) || !validColor(cardOrigin, cardDest)) return false;
        Card card = waste.removeCard();
        tableau.addCard(card, realCol);
        return true;
    }

    /**
     * Move the model.Card from the model.Waste to the foundation in case of being valid.
     *
     * @return Returns true if it is valid the move was executed. False otherwise.
     */
    public boolean moveFromWasteToFoundation() {
        if (waste.isEmpty()) return false;
        Card cardOrigin = waste.getCard();
        Card cardDest = foundation.colSize(cardOrigin.getSuit().ordinal()) == 0 ? null : foundation.getCard(cardOrigin.getSuit().ordinal());

        if (!rightOrderInverted(cardOrigin, cardDest)) return false;
        Card card = waste.removeCard();
        foundation.addCard(card, card.getSuit().ordinal());
        return true;
    }

    /**
     * Move the model.Card from the given column of the tableau to the foundation. You can only add card by card to the foundation.
     *
     * @param col The column from the tableau where the card should be extracted. The card that is on top of the column is the one to be moved.
     * @return Returns true if it is valid the move was executed. False otherwise.
     */
    public boolean moveFromTableauToFoundation(int col) {
        int realCol = col - 1;
        if (tableau.colSize(realCol) == 0) return false;
        Card cardOrigin = tableau.getCard(realCol);
        Card cardDest = foundation.colSize(cardOrigin.getSuit().ordinal()) == 0 ? null : foundation.getCard(cardOrigin.getSuit().ordinal());

        if (!rightOrderInverted(cardOrigin, cardDest)) return false;
        Card card = tableau.removeCards(realCol, tableau.colSize(realCol) - 1).get(0);
        foundation.addCard(card, card.getSuit().ordinal());
        return true;
    }

    /**
     * Move the stack of cards from the origin column to the destination column if it is valid.
     *
     * @param col  The origin column where the cards will be extracted.
     * @param idx  The position from the slice in the column that want to be moved.
     * @param dest The destination column where the slice will be added
     * @return Returns true in case of being a valid move and it was executed. False otherwise.
     */
    @Override
    public boolean move(int col, int idx, int dest) {
        int realCol = col - 1;
        int realIdx = idx - 1;
        int realDest = dest - 1;
        if (!validMove(realCol, realIdx, realDest)) return false;
        tableau.move(realCol, realIdx, realDest);
        return true;
    }

    @Override
    protected boolean rightOrder(Card cardOrigin, Card cardDestination) {
        if (cardDestination == null) return cardOrigin.getNum().equals("K");
        return cardDestination.getNum().equals(orderedDeck.get(orderedDeck.indexOf(cardOrigin.getNum()) - 1));
    }

    protected boolean rightOrderInverted(Card cardOrigin, Card cardDestination) {
        if (cardDestination == null) return cardOrigin.getNum().equals("A");
        return cardDestination.getNum().equals(orderedDeck.get(orderedDeck.indexOf(cardOrigin.getNum()) + 1));
    }

    /**
     * Reminder: it is a valid slice if the cards are of different colors.
     *
     * @param col The column where the slice is placed.
     * @param idx The index of the beginning of the slice.
     * @return Returns true if it is a valid slice.
     */
    @Override
    public boolean validSlice(int col, int idx) {
        Card card1;
        Card card2;

        card1 = tableau.getCard(col, idx);
        for (int i = idx + 1; i < tableau.colSize(col); i++) {
            card2 = tableau.getCard(col, i);
            if (!validColor(card2, card1) || !rightOrder(card2, card1)) return false;
            card1 = card2;
        }

        return true;
    }

    private boolean validColor(Card cardOrigin, Card cardDest) {
        if (cardDest == null) return true;
        return !cardOrigin.getSuit().color.equals(cardDest.getSuit().color);
    }

    @Override
    public boolean victory() {
        for (int i = 0; i < foundation.size(); i++) {
            if (foundation.colSize(i) != 13) return false;
        }
        return true;
    }

    /**
     * Save the game to continue it at any moment.
     *
     * @param path Name of the file where the game will be saved.
     * @throws IOException Throws an Exception in case of any problem while saving it.
     */
    @Override
    public void serialize(String path) throws IOException {
        try (var obj = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(path)))) {
            obj.writeObject(this);
            obj.flush();
        }
    }

    /**
     * Load a saved game to continue it.
     *
     * @param path Name of the file where the game is saved.
     * @return Returns the model.Klondike game with all movements that had been done.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Klondike deserialize(String path) throws IOException, ClassNotFoundException {
        Klondike res;
        try (var obj = new ObjectInputStream(new BufferedInputStream(new FileInputStream(path)))) {
            res = (Klondike) obj.readObject();
        }
        return res;
    }

    @Override
    public KFoundation getFoundation() {
        return this.foundation;
    }
}
