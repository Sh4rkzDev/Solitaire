import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class WasteTest {

    @Test
    public void testOneCard() {
        Waste waste = new Waste();
        Card card = new Card(Suit.DIAMONDS, "K");
        waste.addCard(card);
        assertEquals(card.getNum(), waste.getCard().getNum());
        assertEquals(card.getSuit(), waste.getCard().getSuit());
        Card card1 = waste.removeCard();
        assertEquals(card.getSuit(), card1.getSuit());
        assertEquals(card.getNum(), card1.getNum());
    }

    @Test
    public void testSomeCards() {
        Waste waste = new Waste();
        Card k = new Card(Suit.DIAMONDS, "K");
        Card q = new Card(Suit.DIAMONDS, "Q");
        Card j = new Card(Suit.DIAMONDS, "J");
        Card ten = new Card(Suit.DIAMONDS, "10");
        var aux = new ArrayList<>(Arrays.asList(k, q, j, ten));
        for (Card card : aux) {
            waste.addCard(card);
            assertEquals(card.getNum(), waste.getCard().getNum());
            assertEquals(card.getSuit(), waste.getCard().getSuit());
        }
        for (int i = aux.size() - 1; i > -1; i--) {
            Card card = waste.removeCard();
            assertEquals(aux.get(i).getSuit(), card.getSuit());
            assertEquals(aux.get(i).getNum(), card.getNum());
        }
    }

    @Test
    public void testToDeck() {
        Waste waste = new Waste();
        Card k = new Card(Suit.DIAMONDS, "K");
        Card q = new Card(Suit.DIAMONDS, "Q");
        Card j = new Card(Suit.DIAMONDS, "J");
        Card ten = new Card(Suit.DIAMONDS, "10");
        var aux = new ArrayList<>(Arrays.asList(k, q, j, ten));
        Deck deck1 = new Deck(aux);
        for (int i = aux.size() - 1; i > -1; i--) {
            waste.addCard(aux.get(i));
        }
        Deck deck2 = waste.toDeck();
        while (deck1.isEmpty()) {
            Card card1 = deck1.removeCard();
            Card card2 = deck2.removeCard();
            assertEquals(card1.getNum(), card2.getNum());
            assertEquals(card1.getSuit(), card2.getSuit());
        }
        assertEquals(deck1.isEmpty(), deck2.isEmpty());
    }
}