import java.util.ArrayList;

public class Column {
    private final ArrayList<Card> col = new ArrayList<>(6);

    public Column() {
    }

    public void addCard(Card card) {
        col.add(card);
    }

    public void addCard(ArrayList<Card> stack) {
        col.addAll(stack);
    }

    public Card getCard(int idx) {
        return col.get(idx);
    }

    public ArrayList<Card> removeCards(int idx) {
        ArrayList<Card> res = new ArrayList<>();
        while (col.size() != idx) {
            res.add(col.remove(idx));
        }
        if (!col.isEmpty()) col.get(idx - 1).makeItVisible();
        return res;
    }

    public int size() {
        return col.size();
    }
}
