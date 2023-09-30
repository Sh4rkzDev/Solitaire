import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Spider implements Solitaire {

    private Deck deck;
    private final ArrayList<ArrayList<Card>> foundation = new ArrayList<>(8);
    private final ArrayList<ArrayList<Card>> tableau = new ArrayList<>(10);

    public Spider(byte suits) {
        //We represent the Tableau with a matrix made by two arrays, the tableau array and multiple aux arrays
        //In each position of the tableau array we add an aux array, making the tableau positions the columns
        //and the arrays innit (the aux arrays) the rows.
        deck = new Deck(suits, (byte) 2);
        for (int i = 0; i < 10; i++) {
            ArrayList<Card> aux = new ArrayList<>();
            boolean extra = true;
            for (int j = 0; j < 5; j++) {
                if (extra && i < 4) {
                    extra = false;
                    aux.add(deck.getCard());
                }
                aux.add(deck.getCard());
            }
            aux.get(aux.size()-1).makeItVisible();
            tableau.add(i, aux);
        }
    }

    public Spider(byte suits, int seed) {
        //We represent the Tableau at a particular state with a matrix made by two arrays, the tableau array and multiple aux arrays
        //In each position of the tableau array we add an aux array, making the tableau positions the columns
        //and the arrays innit (the aux arrays) the rows.
        deck = new Deck(suits, (byte) 2, seed);
        for (int i = 0; i < 10; i++) {
            ArrayList<Card> aux = new ArrayList<>();
            boolean extra = true;
            for (int j = 0; j < 5; j++) {
                if (extra && i < 4) {
                    extra = false;
                    aux.add(deck.getCard());
                }
                aux.add(deck.getCard());
            }
            aux.get(aux.size()-1).makeItVisible();
            tableau.add(i, aux);
        }
    }

    @Override
    public void getCards() {
        if (deck.isEmpty()) {
            return;
        }
        for ( ArrayList tabCol : tableau ) {
            Card card = deck.getCard();
            card.makeItVisible();
            tabCol.add(card);
        }
    }

    @Override
    public void move(int tableauCol, int idx, int dest) {
        int realCol = tableauCol-1;
        int realIdx = idx-1;
        if (!validMove(realCol, realIdx, dest)) {
            return;
        }
        while (tableau.get(realCol).size() != realIdx) {
            tableau.get(dest).add(tableau.get(realCol).remove(realIdx));
        }
        if (!tableau.get(realCol).isEmpty()) {
            tableau.get(realCol).get(realIdx-1).makeItVisible();
        }
        if (tableau.get(dest).size() >= 13) {
            checkSequence(dest);
        }
    }

    private void checkSequence(int dest) {
        ArrayList<String> order = new ArrayList<>(
                Arrays.asList("K", "Q", "J", "10", "9", "8", "7", "6","5","4", "3","2", "A")
        );
        int idx = 12;
        Suit suit = tableau.get(dest).get(idx).getSuit();
        for (int i = tableau.get(dest).size()-1; i >= tableau.get(dest).size() - 13; i--) {
            Card card = tableau.get(dest).get(i);
            if (!card.getNum().equals(order.get(idx)) || card.getSuit() != suit) {
                return;
            }
            idx--;
        }
        ArrayList<Card> stack = new ArrayList<>(13);
        for (int i = 0; i < 13; i++) {
            stack.add(tableau.get(dest).remove(tableau.get(dest).size()-1));
        }
        foundation.add(stack);
    }

    @Override
    public boolean validMove(int tableauCol, int idx, int tableauColDestination) {
        //Returns true or false in case the move is valid or not.

        if (tableauCol >= tableau.size() || tableauColDestination >= tableau.size() ||
                idx >= tableau.get(tableauCol).size() || !tableau.get(tableauCol).get(idx).isVisible()) {
            return false;
        }

        Card cardOrigin = tableau.get(tableauCol).get(idx);
        Card cardDestination = tableau.get(tableauColDestination).isEmpty() ? null : tableau.get(tableauColDestination).get(tableau.get(tableauColDestination).size() - 1);

        if (!rightOrder(cardOrigin, cardDestination) || !validSlice(tableauCol, idx)) {
            return false;
        }
        return true;
    }

    private boolean validSlice(int tableauCol, int idx){
        //We verify if we can move the whole slice, in order to do that, we have to verify that it's
        //ordered and all from the same suit.
        Card card1;
        Card card2;

        card1 = tableau.get(tableauCol).get(idx);
        for (int i = idx + 1; i < tableau.get(tableauCol).size() ; i++){
            card2 = tableau.get(tableauCol).get(i);
            if ( !Objects.equals(card1.getSuit(), card2.getSuit()) || !rightOrder(card1,card2)) {
                return false;
            }
            card1 = card2;
        }

        return true;
    }

    private boolean rightOrder(Card cardOrigin, Card cardDestination){
        //Reminder: two cards are stackable even tho they are not from the same suit.
        //Reminder: we can stack any card above an empty space.
        if (cardDestination == null) {
            return true;
        }

        ArrayList<String> orderedDeck = new ArrayList<>(
                Arrays.asList("K","Q","J","10","9","8","7","6","5","4","3","2","A")
        );
        return Objects.equals(orderedDeck.indexOf(cardOrigin.getNum())-1, orderedDeck.indexOf(cardDestination.getNum()));
    }


    @Override
    public boolean victory() {
        return foundation.size() == 8;
    }
}
