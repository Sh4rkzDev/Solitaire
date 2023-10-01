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

    }

    @Test
    public void testRightOrder1Suit() {
        // We verify that two cards of the same suit and in right order can be stackable in Spider Solitaire
        var sp = new Spider((byte) 1);
        ArrayList<String> orderedDeck = new ArrayList<>(
                Arrays.asList("K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2", "A"));
        var cardOrigin = new Card(Suit.CLUBS, "Q");
        var cardDest = new Card(Suit.CLUBS, "K");

        for (int i = 2; i < orderedDeck.size(); i++) {
            cardOrigin = new Card(Suit.CLUBS, orderedDeck.get(i));
            cardDest = new Card(Suit.CLUBS, orderedDeck.get(i - 1));
        }

        cardOrigin = new Card(Suit.CLUBS, "K");
        cardDest = new Card(Suit.CLUBS, "A");
        cardOrigin = new Card(Suit.CLUBS, "J");
        cardDest = new Card(Suit.CLUBS, "A");
    }

    @Test
    public void testRightOrder2Suits() {
        // We verify that two cards of different suits and in right order can be stackable in Spider Solitaire
        var sp = new Spider((byte) 2);
        ArrayList<String> orderedDeck = new ArrayList<>(
                Arrays.asList("K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2", "A"));
        var cardOrigin = new Card(Suit.CLUBS, "Q");
        var cardDest = new Card(Suit.DIAMONDS, "K");

        for (int i = 2; i < orderedDeck.size(); i++) {

            cardOrigin = new Card(Suit.DIAMONDS, cardOrigin.getNum());
            cardDest = new Card(Suit.CLUBS, cardDest.getNum());

            cardOrigin = new Card(Suit.CLUBS, orderedDeck.get(i));
            cardDest = new Card(Suit.DIAMONDS, orderedDeck.get(i - 1));
        }

        cardOrigin = new Card(Suit.CLUBS, "K");
        cardDest = new Card(Suit.DIAMONDS, "K");
        cardOrigin = new Card(Suit.DIAMONDS, "7");
        cardDest = new Card(Suit.CLUBS, "A");
    }

}
