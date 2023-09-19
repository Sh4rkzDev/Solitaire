import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Deck {

    private final ArrayList<Card> cards = new ArrayList<>();

    public Deck(byte quant) { // TODO: quantity of suits to use. Will create deck with that amount of suits
        ArrayList<String> numbers = new ArrayList<>(
                Arrays.asList("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
        );
        for (Suit s : Suit.values()) {
            if (s.ordinal()-1 == quant) {
                break;
            }

            for (String num : numbers) {
                Card c = new Card(s, num);
                cards.add(c);
            }
        }
        Collections.shuffle(cards);

    }

    public Card getCard() {
        return cards.remove(cards.size()-1);
    }
}
