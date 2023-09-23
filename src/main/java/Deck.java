import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Deck {

    private final ArrayList<Card> cards = new ArrayList<>();

    public Deck(byte suits) {
        addCards(suits);
        Collections.shuffle(cards);
    }

    public Deck(byte suits, byte decks) {
        for (int nDecks = 0; nDecks < decks; nDecks++) {
            addCards(suits);
        }
        Collections.shuffle(cards);
    }

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

    public Card getCard() {
        return cards.remove(cards.size()-1);
    }
}
