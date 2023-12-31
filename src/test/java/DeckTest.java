import junit.framework.TestCase;
import model.Card;
import model.Deck;
import model.Suit;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DeckTest extends TestCase {

    @Test
    public void testCompleteDeck() {
        //We verify that there are as many cards as it should be from each number.
        Deck deck = new Deck((byte) 1, (byte) 2);
        HashMap<String, Integer> dict = new HashMap<>();
        Card card;

        while (!deck.isEmpty()) {
            card = deck.removeCard();
            String key = card.getNum();
            if (dict.containsKey(key)) {
                dict.replace(key, dict.get(key) + 1);
            } else {
                dict.put(key, 1);
            }
        }

        ArrayList<String> orderedDeck = new ArrayList<>(
                Arrays.asList("K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2", "A")
        );

        for (String element : orderedDeck) {
            int amountElem = dict.get(element);
            assertEquals(8, amountElem);

        }
    }

    @Test
    public void testCompleteDeckWith2Suits() {
        //We verify that there are as many cards as it should be from each number and suit (with only 2 suits).
        Deck deck = new Deck((byte) 2, (byte) 2);
        HashMap<String, Integer> dictCLUBS = new HashMap<>();
        HashMap<String, Integer> dictDIAMONDS = new HashMap<>();
        Card card;

        while (!deck.isEmpty()) {
            card = deck.removeCard();
            String key = card.getNum();
            Suit suit = card.getSuit();
            switch (suit) {
                case CLUBS -> {
                    if (dictCLUBS.containsKey(key)) {
                        dictCLUBS.replace(key, dictCLUBS.get(key) + 1);
                    } else {
                        dictCLUBS.put(key, 1);
                    }
                }
                case DIAMONDS -> {
                    if (dictDIAMONDS.containsKey(key)) {
                        dictDIAMONDS.replace(key, dictDIAMONDS.get(key) + 1);
                    } else {
                        dictDIAMONDS.put(key, 1);
                    }
                }
            }

        }

        ArrayList<HashMap<String, Integer>> dictionaries = new ArrayList<>();
        dictionaries.add(dictCLUBS);
        dictionaries.add(dictDIAMONDS);

        ArrayList<String> orderedDeck = new ArrayList<>(
                Arrays.asList("K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2", "A")
        );

        for (String element : orderedDeck) {
            for (HashMap<String, Integer> dict : dictionaries) {
                int amountElem = dict.get(element);
                assertEquals(4, amountElem);
            }

        }
    }

    @Test
    public void testCompleteDeckWith4Suits() {
        //We verify that there are as many cards as it should be from each number and suit (with only 4 suits).
        Deck deck = new Deck((byte) 4, (byte) 2);
        HashMap<String, Integer> dictCLUBS = new HashMap<>();
        HashMap<String, Integer> dictDIAMONDS = new HashMap<>();
        HashMap<String, Integer> dictHEARTS = new HashMap<>();
        HashMap<String, Integer> dictSPADES = new HashMap<>();
        Card card;

        while (!deck.isEmpty()) {
            card = deck.removeCard();
            String key = card.getNum();
            Suit suit = card.getSuit();
            switch (suit) {
                case CLUBS -> {
                    if (dictCLUBS.containsKey(key)) {
                        dictCLUBS.replace(key, dictCLUBS.get(key) + 1);
                    } else {
                        dictCLUBS.put(key, 1);
                    }
                }
                case DIAMONDS -> {
                    if (dictDIAMONDS.containsKey(key)) {
                        dictDIAMONDS.replace(key, dictDIAMONDS.get(key) + 1);
                    } else {
                        dictDIAMONDS.put(key, 1);
                    }
                }
                case HEARTS -> {
                    if (dictHEARTS.containsKey(key)) {
                        dictHEARTS.replace(key, dictHEARTS.get(key) + 1);
                    } else {
                        dictHEARTS.put(key, 1);
                    }
                }
                case SPADES -> {
                    if (dictSPADES.containsKey(key)) {
                        dictSPADES.replace(key, dictSPADES.get(key) + 1);
                    } else {
                        dictSPADES.put(key, 1);
                    }
                }
            }

        }

        ArrayList<HashMap<String, Integer>> dictionaries = new ArrayList<>();
        dictionaries.add(dictCLUBS);
        dictionaries.add(dictDIAMONDS);
        dictionaries.add(dictHEARTS);
        dictionaries.add(dictSPADES);

        ArrayList<String> orderedDeck = new ArrayList<>(
                Arrays.asList("K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2", "A")
        );

        for (String element : orderedDeck) {
            for (HashMap<String, Integer> dict : dictionaries) {
                int amountElem = dict.get(element);
                assertEquals(2, amountElem);
            }

        }
    }

    @Test
    public void testOneDeckWithOneSuit() {
        Deck deck = new Deck((byte) 1);
        HashMap<String, Integer> dict = new HashMap<>();
        Card card;

        while (!deck.isEmpty()) {
            card = deck.removeCard();
            String key = card.getNum();
            if (dict.containsKey(key)) {
                dict.replace(key, dict.get(key) + 1);
            } else {
                dict.put(key, 1);
            }
        }

        ArrayList<String> orderedDeck = new ArrayList<>(
                Arrays.asList("K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2", "A")
        );

        for (String element : orderedDeck) {
            int amountElem = dict.get(element);
            assertEquals(4, amountElem);
        }
    }

    @Test
    public void testDeckWithSeed() {
        Deck deck1 = new Deck((byte) 4, (byte) 2);
        Deck deck2 = new Deck((byte) 4, (byte) 2);
        deck1.shuffle(5);
        deck2.shuffle(5);

        while (!deck1.isEmpty()) {
            Card card1 = deck1.removeCard();
            Card card2 = deck2.removeCard();
            assertEquals(card1.getNum(), card2.getNum());
            assertEquals(card1.getSuit(), card2.getSuit());
        }
        assertTrue(deck1.isEmpty());
        assertTrue(deck2.isEmpty());
    }
}
