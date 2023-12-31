import model.*;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class SpiderTest {
    @Test
    public void testNewGame() {
        // We verify the initial state of a model.Spider model.Solitaire game
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
        assertTrue(sp.move(1, 0, 0));
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
        assertTrue(sp.move(8, 0, 7));
        assertTrue(sp.move(0, 0, 8));
        for (int i = 7; i > 1; i--) {
            assertTrue(sp.move(i, 0, i - 1));
        }
        assertTrue(sp.move(1, 0, 8));
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
        assertFalse(sp.move(0, 0, 5));
        assertFalse(sp.move(9, 0, 30));
        assertFalse(sp.move(10, 0, 0));
        assertFalse(sp.move(0, 0, 9));
        assertFalse(sp.move(0, 14, 1));
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
        assertTrue(sp.move(5, 0, 0));
        assertFalse(sp.move(0, 0, 5));
        assertTrue(sp.move(0, 6, 5));
        assertTrue(sp.move(5, 0, 4));
        assertTrue(sp.move(1, 0, 0));
        assertTrue(sp.move(9, 2, 0));
        assertFalse(sp.move(0, 0, 2));
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

    @Test
    public void testDeserializeException() {
        assertThrows(IOException.class, () -> Spider.deserialize("DoNotExist"));
    }

    @Test
    public void testNewGameWithSeedPersistence() throws IOException, ClassNotFoundException {
        Spider sp1 = new Spider((byte) 1, 9);
        var path = "spider";
        sp1.serialize(path);
        Spider sp2 = Spider.deserialize(path);
        for (int i = 0; i < 5; i++) {
            ArrayList<Card> c1 = sp1.getCards();
            ArrayList<Card> c2 = sp2.getCards();
            for (int j = 0; j < 10; j++) {
                assertEquals(c1.get(j).getSuit(), c2.get(j).getSuit());
                assertEquals(c1.get(j).getNum(), c2.get(j).getNum());
            }
        }
        File file = new File(path);
        file.delete();
    }

    @Test
    public void testPersistence() throws IOException, ClassNotFoundException {
        Tableau tableau = new Tableau(10);
        Foundation foundation = new Foundation(8);
        Deck deck = new Deck((byte) 1, (byte) 2);
        {
            ArrayList<Card> stack = new ArrayList<>(13);
            for (int i = 0; i < 13; i++) {
                stack.add(deck.removeCard());
            }
            foundation.addStack(stack);
        }
        Spider sp1 = new Spider(deck, tableau, foundation);
        sp1.getCards();
        for (int i = 10; i > 5; i--) {
            sp1.move(i - 1, 0, i);
        }

        var path = "spider2";

        sp1.serialize(path);
        Spider sp2 = Spider.deserialize(path);

        assertEquals(sp1.victory(), sp2.victory());
        for (int i = 0; i < 6; i++) {
            ArrayList<Card> c1 = sp1.getCards();
            ArrayList<Card> c2 = sp2.getCards();
            for (int j = 0; j < 10; j++) {
                assertEquals(c1.get(j).getSuit(), c2.get(j).getSuit());
                assertEquals(c1.get(j).getNum(), c2.get(j).getNum());
            }
        }
        File file = new File(path);
        file.delete();
    }

    @Test
    public void testPersistenceWin() {
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

        var path = "spiderWin";
        Spider sp1 = new Spider(deck, tableau, foundation);
        try {
            sp1.serialize(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Spider sp2;
        try {
            sp2 = Spider.deserialize(path);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        assertEquals(sp1.move(1, 0, 0), sp2.move(1, 0, 0));
        assertEquals(sp1.victory(), sp2.victory());

        File file = new File(path);
        file.delete();
    }
}
