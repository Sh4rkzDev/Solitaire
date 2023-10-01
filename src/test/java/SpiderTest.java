import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

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
        ArrayList<ArrayList<Card>> tableau = new ArrayList<>(10);
        ArrayList<ArrayList<Card>> foundation = new ArrayList<>(8);
        Deck deck = new Deck((byte) 1, (byte) 2);
        for (int j = 0; j < 7; j++) {
            ArrayList<Card> stack = new ArrayList<>(13);
            for (int i = 0; i < 13; i++) {
                stack.add(deck.getCard());
            }
            foundation.add(stack);
        }
        ArrayList<Card> stack = new ArrayList<>(13);
        for (int i = 0; i < 12; i++) {
            Card card = deck.getCard();
            card.makeItVisible();
            stack.add(card);
        }
        tableau.add(stack);
        ArrayList<Card> oneLeft = new ArrayList<>(1);
        Card left = deck.getCard();
        left.makeItVisible();
        oneLeft.add(left);
        assertTrue(deck.isEmpty());
        assertTrue(foundation.size() == 7);
        tableau.add(oneLeft);
        for (int i = 0; i < 8; i++) {
            ArrayList<Card> aux = new ArrayList<>();
            tableau.add(aux);
        }
        assertTrue(tableau.size() == 10);
        Spider sp = new Spider(deck, tableau, foundation);
        assertTrue(sp.move(2, 1, 1));
        assertTrue(sp.victory());
    }

    @Test
    public void testValidMoves() {
        ArrayList<ArrayList<Card>> tableau = new ArrayList<>(10);
        ArrayList<ArrayList<Card>> foundation = new ArrayList<>(8);
        Deck deck = new Deck((byte) 1, (byte) 2);

        for (int i = 0; i < 10; i++) {
            ArrayList<Card> aux = new ArrayList<>();
            Card card = deck.getCard();
            card.makeItVisible();
            aux.add(card);
            if (i < 4) {
                card = deck.getCard();
                card.makeItVisible();
                aux.add(card);
            }
            tableau.add(aux);
        }

        Spider sp = new Spider(deck, tableau, foundation);
        assertFalse(sp.move(1, 1, 6));
        assertTrue(sp.move(9, 1, 8));
        assertTrue(sp.move(1, 1, 9));
        for (int i = 8; i > 1; i--) {
            assertTrue(sp.move(i, 1, i - 1));
        }
        assertFalse(sp.move(10, 1, 30));
        assertFalse(sp.move(11, 1, 1));
        assertFalse(sp.move(1, 1, 10));
        assertTrue(sp.move(1, 1, 9));
    }

    @Test
    public void testInvalidMoves() {
        ArrayList<ArrayList<Card>> tableau = new ArrayList<>(10);
        ArrayList<ArrayList<Card>> foundation = new ArrayList<>(8);
        Deck deck = new Deck((byte) 1, (byte) 2);

        for (int i = 0; i < 10; i++) {
            ArrayList<Card> aux = new ArrayList<>();
            Card card = deck.getCard();
            card.makeItVisible();
            aux.add(card);
            if (i < 4) {
                card = deck.getCard();
                card.makeItVisible();
                aux.add(card);
            }
            tableau.add(aux);
        }

        Spider sp = new Spider(deck, tableau, foundation);
    }
}
