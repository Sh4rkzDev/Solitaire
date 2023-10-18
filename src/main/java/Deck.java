import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Deck {

    private ArrayList<Card> cards = new ArrayList<>(52);

    public Deck(byte suits) {
        addCards(suits);
    }

    public Deck(byte suits, byte decks) {
        for (int nDecks = 0; nDecks < decks; nDecks++) {
            addCards(suits);
        }
    }

    public Deck(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public void shuffle(int seed) {
        Random sd = new Random(seed);
        Collections.shuffle(cards, sd);
    }

    /**
     * @param suits Add the number of cards according to the number of suits. I.e: if the number of suits is 2, there will be 26 cards of one suit, and 26 of another suit.
     */
    private void addCards(byte suits) {
        ArrayList<String> numbers = new ArrayList<>(
                Arrays.asList("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
        );
        for (Suit s : Suit.values()) {
            if (s.ordinal() == suits) {
                break;
            }
            for (int i = 0; i <= Suit.values().length - suits; i++) {
                if (suits == 2 && i == 2) { // For suits = 1 or 4 it works, but for 2 suits it doesn't.
                    break;
                }
                for (String num : numbers) {
                    Card c = new Card(s, num);
                    cards.add(c);
                }
            }
        }
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public Card removeCard() {
        return cards.remove(cards.size() - 1);
    }
}
