import model.Card;
import model.Column;
import model.Suit;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ColumnTest {
    @Test
    public void testAddCard() {
        Card card = new Card(Suit.HEARTS, "K");
        Column column = new Column();
        column.addCard(card);
        assertEquals(1, column.size());
        assertEquals("K", column.getCard(0).getNum());
        assertEquals(Suit.HEARTS, column.getCard(0).getSuit());
    }

    @Test
    public void testRemoveCard() {
        Card card = new Card(Suit.HEARTS, "K");
        Column column = new Column();
        column.addCard(card);
        assertEquals(1, column.size());
        column.removeCards(0);
        assertEquals(0, column.size());
    }

    @Test
    public void testAddCards() {
        ArrayList<Card> stack = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            stack.add(new Card(Suit.HEARTS, "A"));
        }
        Column column = new Column();
        column.addCard(stack);
        assertEquals(13, column.size());
    }

    @Test
    public void testRemoveCards() {
        ArrayList<Card> stack = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            stack.add(new Card(Suit.HEARTS, "A"));
        }
        Column column = new Column();
        column.addCard(stack);
        column.removeCards(5);
        assertEquals(5, column.size());
    }
}