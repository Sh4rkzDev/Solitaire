public abstract class Solitaire implements Interaction {
    protected final byte tableauCols;
    protected Deck deck;
    protected Foundation foundation;
    protected Tableau tableau;

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

    protected abstract boolean validMove(int tableauCol, int idx, int tableauColDestination);

    protected abstract boolean victory();
}
