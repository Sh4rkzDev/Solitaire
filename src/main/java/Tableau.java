import java.util.ArrayList;

public class Tableau {
    private final ArrayList<Column> tableau;

    public Tableau(int cols) {
        tableau = new ArrayList<>(cols);
        for (int i = 0; i < cols; i++) {
            tableau.add(new Column());
        }
    }

    public void addCard(Card card, int col) {
        tableau.get(col).addCard(card);
    }

    public void move(int col, int idx, int dest) {
        ArrayList<Card> stack = removeCards(col, idx);
        tableau.get(dest).addCard(stack);
    }

    public int colSize(int col) {
        return tableau.get(col).size();
    }

    public Card getCard(int col) {
        return tableau.get(col).getCard(colSize(col) - 1);
    }

    public Card getCard(int col, int idx) {
        return tableau.get(col).getCard(idx);
    }

    public ArrayList<Card> removeCards(int col, int idx) {
        return tableau.get(col).removeCards(idx);
    }
}
