import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SpiderTest {
    @Test
    public void testNewGame() {
        // We verify the initial state of a Spider Solitaire game
        var sp = new Spider((byte) 1);
        assertNotNull(sp);
        assertFalse(sp.victory());
    }

    @Test
    public void testWonGame() {
        Tableau tableau = new Tableau(10);
        Foundation foundation = new Foundation(8);
        Deck deck = new Deck((byte) 1, (byte) 2);
        for (int j = 0; j < 7; j++) {
            ArrayList<Card> stack = new ArrayList<>(13);
            for (int i = 0; i < 13; i++) {
                stack.add(deck.removeCard());
            }
            foundation.addStack(stack);
        }
        for (int i = 0; i < 12; i++) {
            Card card = deck.removeCard();
            card.makeItVisible();
            tableau.addCard(card, 0);
        }
        Card left = deck.removeCard();
        left.makeItVisible();
        tableau.addCard(left, 1);
        assertTrue(deck.isEmpty());
        assertEquals(7, foundation.size());
        Spider sp = new Spider(deck, tableau, foundation);
        assertTrue(sp.move(2, 1, 1));
        assertTrue(sp.victory());
    }

    @Test
    public void testValidMovesOneSuit() {
        Tableau tableau = new Tableau(10);
        Foundation foundation = new Foundation(8);
        Deck deck = new Deck((byte) 1, (byte) 2);

        for (int i = 0; i < 10; i++) {
            Card card = deck.removeCard();
            card.makeItVisible();
            tableau.addCard(card, i);
            if (i < 4) {
                card = deck.removeCard();
                card.makeItVisible();
                tableau.addCard(card, i);
            }
        }

        Spider sp = new Spider(deck, tableau, foundation);
        assertTrue(sp.move(9, 1, 8));
        assertTrue(sp.move(1, 1, 9));
        for (int i = 8; i > 1; i--) {
            assertTrue(sp.move(i, 1, i - 1));
        }
        assertTrue(sp.move(1, 1, 9));
    }

    @Test
    public void testInvalidMovesOneSuit() {
        Tableau tableau = new Tableau(10);
        Foundation foundation = new Foundation(8);
        Deck deck = new Deck((byte) 1, (byte) 2);

        for (int i = 0; i < 10; i++) {
            Card card = deck.removeCard();
            card.makeItVisible();
            tableau.addCard(card, i);
            if (i < 4) {
                card = deck.removeCard();
                card.makeItVisible();
                tableau.addCard(card, i);
            }
        }

        Spider sp = new Spider(deck, tableau, foundation);
        assertFalse(sp.move(1, 1, 6));
        assertFalse(sp.move(10, 1, 30));
        assertFalse(sp.move(11, 1, 1));
        assertFalse(sp.move(1, 1, 10));
        assertFalse(sp.move(1, 15, 2));
    }

    @Test
    public void testMovesTwoSuits() {
        Tableau tableau = new Tableau(10);
        Foundation foundation = new Foundation(8);
        Deck deck = new Deck((byte) 2, (byte) 2);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 6; j++) {
                Card card = deck.removeCard();
                card.makeItVisible();
                tableau.addCard(card, i);
            }
            if ((i - 1) % 2 == 0) {
                Card card = deck.removeCard();
                card.makeItVisible();
                tableau.addCard(card, i);
            }
        }

        Spider sp = new Spider(deck, tableau, foundation);
        assertTrue(sp.move(6, 1, 1));
        assertFalse(sp.move(1, 1, 6));
        assertTrue(sp.move(1, 7, 6));
        assertTrue(sp.move(6, 1, 5));
        assertTrue(sp.move(2, 1, 1));
        assertTrue(sp.move(10, 3, 1));
        assertFalse(sp.move(1, 1, 3));
    }

    @Test
    public void testSpiderWithSeed() {
        Spider sp1 = new Spider((byte) 4, 9);
        Spider sp2 = new Spider((byte) 4, 9);
        for (int i = 0; i < 5; i++) {
            ArrayList<Card> c1 = sp1.getCards();
            ArrayList<Card> c2 = sp2.getCards();
            for (int j = 0; j < 10; j++) {
                assertEquals(c1.get(j).getSuit(), c2.get(j).getSuit());
                assertEquals(c1.get(j).getNum(), c2.get(j).getNum());
            }
        }
    }
}
