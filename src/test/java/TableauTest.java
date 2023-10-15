import org.junit.Test;

import static org.junit.Assert.*;

public class TableauTest {
    @Test
    public void testAddCard() {
        Tableau tableau = new Tableau(10);
        Card card = new Card(Suit.HEARTS, "K");
        tableau.addCard(card, 5);
        assertEquals(1, tableau.colSize(5));
        assertEquals(Suit.HEARTS, tableau.getCard(5).getSuit());
        assertEquals("K", tableau.getCard(5).getNum());
        assertEquals(Suit.HEARTS, tableau.getCard(5, 0).getSuit());
        assertEquals("K", tableau.getCard(5, 0).getNum());
    }

    @Test
    public void testMoveCard() {
        Tableau tableau = new Tableau(10);
        Card card = new Card(Suit.HEARTS, "K");
        tableau.addCard(card, 5);
        tableau.move(5, 0, 0);
        assertEquals(1, tableau.colSize(0));
        assertEquals(0, tableau.colSize(5));
    }

    @Test
    public void testMoveStack() {
        Tableau tableau = new Tableau(10);
        Card card = new Card(Suit.HEARTS, "K");
        for (int i = 0; i < 13; i++) {
            tableau.addCard(card, 0);
        }
        assertEquals(13, tableau.colSize(0));
        tableau.move(0, 10, 1);
        assertEquals(10, tableau.colSize(0));
        assertEquals(3, tableau.colSize(1));
    }

    @Test
    public void testRemoveCards() {
        Tableau tableau = new Tableau(10);
        Card card = new Card(Suit.HEARTS, "K");
        for (int i = 0; i < 13; i++) {
            tableau.addCard(card, 0);
        }
        tableau.removeCards(0, 4);
        assertEquals(4, tableau.colSize(0));
    }
}