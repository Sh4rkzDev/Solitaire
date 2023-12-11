import model.*;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class KlondikeTest {

    @Test
    public void testNewGame() {
        // We verify the initial state of a model.Klondike model.Solitaire game
        var kld = new Klondike((byte) 3);
        assertNotNull(kld);
        assertFalse(kld.victory());
    }

    @Test
    public void testWonGame() {
        Tableau tableau = new Tableau(7);
        KFoundation foundation = new KFoundation();
        Deck deck = new Deck((byte) 4);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 13; j++) {
                Card card = deck.removeCard();
                foundation.addCard(card, card.getSuit().ordinal());
            }
        }
        for (int i = 0; i < 13; i++) {
            Card card = deck.removeCard();
            card.makeItVisible();
            tableau.addCard(card, 0);
        }
        assertTrue(deck.isEmpty());
        Klondike kld = new Klondike(deck, tableau, foundation);
        for (int i = 0; i < 13; i++) {
            assertTrue(kld.moveFromTableauToFoundation(0));
        }
        assertTrue(kld.victory());
    }

    @Test
    public void testValidMovesInTableau() {
        Tableau tableau = new Tableau(7);
        KFoundation foundation = new KFoundation();
        Deck deck = new Deck((byte) 4);

        for (int i = 0; i < 4; i++) {
            Card card = deck.removeCard();
            card.makeItVisible();
            tableau.addCard(card, i);
            if (i == 3) break;
            for (int j = 0; j < 13; j++) {
                deck.removeCard();
            }
        }

        Klondike kld = new Klondike(deck, tableau, foundation);
        for (int i = 3; i > 0; i--) {
            assertTrue(kld.move(i, 0, i - 1));
        }
        assertTrue(kld.move(0, 0, 3));
    }

    @Test
    public void testInvalidMovesInTableau() {
        Tableau tableau = new Tableau(7);
        KFoundation foundation = new KFoundation();
        Deck deck = new Deck((byte) 4);

        for (int i = 0; i < 4; i++) {
            Card card = deck.removeCard();
            card.makeItVisible();
            tableau.addCard(card, i);
            if (i == 3) {
                card = deck.removeCard();
                card.makeItVisible();
                tableau.addCard(card, i);
                break;
            }
            for (int j = 0; j < 13; j++) {
                deck.removeCard();
            }
        }

        Klondike kld = new Klondike(deck, tableau, foundation);
        assertFalse(kld.move(3, 0, 0));
        assertFalse(kld.move(3, 0, 6));
        assertFalse(kld.move(3, 1, 4));
        assertFalse(kld.move(9, 0, 30));
        assertFalse(kld.move(1, 0, 2));
        assertFalse(kld.move(0, 0, 10));
        assertFalse(kld.move(0, 15, 1));
    }

    @Test
    public void testGetCards() {
        Klondike kld = new Klondike((byte) 1);
        ArrayList<Card> order = new ArrayList<>(24);
        for (int i = 0; i < 24; i++) {
            order.addAll(kld.getCards());
        }
        for (Card card : order) {
            Card cardDeck = kld.getCards().get(0);
            assertEquals(card.getNum(), cardDeck.getNum());
            assertEquals(card.getSuit(), cardDeck.getSuit());
        }
        assertTrue(kld.getCards().isEmpty());
    }

    @Test
    public void testKlondikeWithSeed() {
        Klondike kld1 = new Klondike((byte) 3, 9);
        Klondike kld2 = new Klondike((byte) 3, 9);
        for (int i = 0; i < 24; i++) {
            ArrayList<Card> c1 = kld1.getCards();
            ArrayList<Card> c2 = kld2.getCards();
            assertEquals(c1.get(0).getSuit(), c2.get(0).getSuit());
            assertEquals(c1.get(0).getNum(), c2.get(0).getNum());
        }
    }

    @Test
    public void testMoveWasteToTableau() {
        Tableau tableau = new Tableau(7);
        KFoundation foundation = new KFoundation();
        Deck deck = new Deck((byte) 4);

        Klondike kld = new Klondike(deck, tableau, foundation);
        kld.getCards();
        assertTrue(kld.moveFromWasteToTableau(0));
        for (int i = 0; i < 12; i++) {
            kld.getCards();
            assertFalse(kld.moveFromWasteToTableau(0));
            assertFalse(kld.moveFromWasteToTableau(1));
        }
        kld.getCards();
        kld.getCards();
        assertTrue(kld.moveFromWasteToTableau(0));
        assertTrue(kld.moveFromWasteToTableau(1));
    }

    @Test
    public void testWasteToFoundation() {
        Tableau tableau = new Tableau(7);
        KFoundation foundation = new KFoundation();
        Deck deck = new Deck((byte) 4);

        Klondike kld = new Klondike(deck, tableau, foundation);
        int aux = 0;
        for (int i = 0; i < 52; i++) {
            kld.getCards();
            if (i == 12 + 13 * aux) {
                aux++;
                continue;
            }
            assertFalse(kld.moveFromWasteToFoundation());
        }
        for (int i = 0; i < 52; i++) {
            assertTrue(kld.moveFromWasteToFoundation());
        }
        assertTrue(kld.victory());

        tableau = new Tableau(7);
        foundation = new KFoundation();
        deck = new Deck((byte) 4);
        kld = new Klondike(deck, tableau, foundation);
        for (int i = 0; i < 14; i++) {
            kld.getCards();
        }
        assertFalse(kld.moveFromWasteToFoundation());
        assertTrue(kld.moveFromWasteToTableau(4));
        for (int i = 0; i < 13; i++) {
            assertTrue(kld.moveFromWasteToFoundation());
        }
    }

    @Test
    public void testDeserializeException() {
        assertThrows(IOException.class, () -> Klondike.deserialize("DoNotExist"));
    }

    @Test
    public void testNewGameWithSeedPersistence() throws IOException, ClassNotFoundException {
        Klondike kld1 = new Klondike((byte) 1, 9);
        var path = "klondike";
        kld1.serialize(path);
        Klondike kld2 = Klondike.deserialize(path);
        for (int i = 0; i < 5; i++) {
            ArrayList<Card> c1 = kld1.getCards();
            ArrayList<Card> c2 = kld2.getCards();
            assertEquals(c1.get(0).getSuit(), c2.get(0).getSuit());
            assertEquals(c1.get(0).getNum(), c2.get(0).getNum());
        }

        File file = new File(path);
        file.delete();
    }

    @Test
    public void testPersistence() throws IOException, ClassNotFoundException {
        Tableau tableau = new Tableau(7);
        KFoundation foundation = new KFoundation();
        Deck deck = new Deck((byte) 4);

        Klondike kld = new Klondike(deck, tableau, foundation);
        kld.getCards();
        kld.moveFromWasteToTableau(0);
        for (int i = 0; i < 12; i++) {
            kld.getCards();
        }
        kld.moveFromWasteToFoundation();
        kld.getCards();
        kld.moveFromWasteToTableau(1);
        kld.getCards();
        assertTrue(kld.moveFromWasteToTableau(0));

        var path = "klondike2";
        kld.serialize(path);
        Klondike kld2 = Klondike.deserialize(path);

        for (int i = 0; i < 35; i++) {
            Card c1 = kld.getCards().get(0);
            Card c2 = kld2.getCards().get(0);
            assertEquals(c1.getSuit(), c2.getSuit());
            assertEquals(c1.getNum(), c2.getNum());
        }
        File file = new File(path);
        file.delete();
    }

}