import java.util.ArrayList;

public class Spider implements Solitaire {

    private Deck deck;
    private final ArrayList<ArrayList<Card>> foundation = new ArrayList<>(8);
    private final ArrayList<ArrayList<Card>> tableau = new ArrayList<>(10);

    public Spider(byte suits) {
        deck = new Deck(suits, (byte) 2);
        for (int i = 0; i < 10; i++) {
            ArrayList<Card> aux = new ArrayList<>();
            boolean extra = true;
            for (int j = 0; j < 5; j++) {
                if (extra && i < 4) {
                    extra = false;
                    aux.add(deck.getCard());
                }
                aux.add(deck.getCard());
            }
            tableau.add(i, aux);
        }
    }

    public void move(int row, int col, int dest) {
        if (!validAction()) {
            return;
        }
        int realRow = row-1;
        int realCol = col-1;
        while (tableau.get(realRow).size() != realCol) {
            tableau.get(dest).add(tableau.get(realRow).remove(realCol));
        }
    }

    @Override
    public boolean validAction() {
        return false;
    }

    @Override
    public boolean victory() {
        return foundation.size() == 8;
    }
}
