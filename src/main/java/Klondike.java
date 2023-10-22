import java.io.*;
import java.util.ArrayList;

public class Klondike extends Solitaire {

    protected KFoundation foundation;
    private final Waste waste = new Waste();
    private byte reDeals;

    /**
     * Constructor of the Klondike Solitaire Game.
     *
     * @param reDeals Number of redeals allow.
     */
    public Klondike(byte reDeals) {
        super((byte) 4, (byte) 1, (byte) 7, (byte) 4);
        foundation = new KFoundation();
        this.reDeals = reDeals;
    }

    /**
     * Constructor of the Klondike Solitaire Game.
     *
     * @param reDeals Number of redeals allow.
     * @param seed    It can take a seed to generate a specific game scenario.
     */
    public Klondike(byte reDeals, int seed) {
        super((byte) 4, (byte) 1, (byte) 7, (byte) 4, seed);
        foundation = new KFoundation();
        this.reDeals = reDeals;
    }

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
     * @param deck       Specific Deck to be passed.
     * @param tableau    Specific Tableau to be passed.
     * @param foundation Specific Foundation to be passed.
     */
    public Klondike(Deck deck, Tableau tableau, KFoundation foundation) {
        super(deck, tableau, foundation, (byte) 7);
    }

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

    public boolean moveFromWasteToTableau(int col) {
        Card cardOrigin = waste.getCard();
        Card cardDest = tableau.colSize(col) == 0 ? null : tableau.getCard(col);

        if (!rightOrder(cardOrigin, cardDest)) return false;
        Card card = waste.removeCard();
        tableau.addCard(card, col);
        return true;
    }

    public boolean moveFromWasteToFoundation() {
        Card cardOrigin = waste.getCard();
        Card cardDest = foundation.getCard(cardOrigin.getSuit().ordinal());

        if (!rightOrderInverted(cardOrigin, cardDest)) return false;
        Card card = waste.removeCard();
        foundation.addCard(card, card.getSuit().ordinal());
        return true;
    }

    public boolean moveFromTableauToFoundation(int col) {
        Card cardOrigin = tableau.getCard(col);
        Card cardDest = foundation.colSize(cardOrigin.getSuit().ordinal()) == 0 ? null : foundation.getCard(cardOrigin.getSuit().ordinal());

        if (!rightOrderInverted(cardOrigin, cardDest)) return false;
        Card card = tableau.removeCards(col, tableau.colSize(col) - 1).get(0);
        foundation.addCard(card, card.getSuit().ordinal());
        return true;
    }

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

    @Override
    protected boolean validSlice(int col, int idx) {
        Card card1;
        Card card2;

        card1 = tableau.getCard(col, idx);
        for (int i = idx + 1; i < tableau.colSize(col); i++) {
            card2 = tableau.getCard(col, i);
            if (card1.getSuit().color.equals(card2.getSuit().color) || !rightOrder(card2, card1)) return false;
            card1 = card2;
        }

        return true;
    }

    @Override
    protected boolean victory() {
        for (int i = 0; i < foundation.size(); i++) {
            if (foundation.colSize(i) != 13) return false;
        }
        return true;
    }

    @Override
    public void serialize(String path) throws IOException {
        ObjectOutputStream obj = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(path)));
        obj.writeObject(this);
        obj.flush();
        obj.close();
    }

    @Override
    public Klondike deserialize(String path) throws IOException, ClassNotFoundException {
        ObjectInputStream obj = new ObjectInputStream(new BufferedInputStream(new FileInputStream(path)));
        Klondike res = (Klondike) obj.readObject();
        obj.close();
        return res;
    }
}
