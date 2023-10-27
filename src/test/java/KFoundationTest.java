import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class KFoundationTest {
    @Test
    public void testAddCard() {
        KFoundation foundation = new KFoundation();
        Card card = new Card(Suit.DIAMONDS, "A");
        foundation.addCard(card, 0);
        assertEquals(1, foundation.colSize(0));
        assertEquals("A", foundation.getCard(0).getNum());
        assertEquals(Suit.DIAMONDS, foundation.getCard(0).getSuit());
    }

    @Test
    public void testSomeCards() {
        KFoundation foundation = new KFoundation();
        Card card = new Card(Suit.CLUBS, "A");
        Card card1 = new Card(Suit.CLUBS, "2");
        Card card2 = new Card(Suit.CLUBS, "3");
        var aux = new ArrayList<>(Arrays.asList(card, card1, card2));
        for (int i = 0; i < aux.size(); i++) {
            foundation.addCard(aux.get(i), 2);
            assertEquals(i + 1, foundation.colSize(2));
            assertEquals(aux.get(i).getSuit(), foundation.getCard(2).getSuit());
            assertEquals(aux.get(i).getNum(), foundation.getCard(2).getNum());
        }
    }
}