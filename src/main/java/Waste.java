import java.util.ArrayList;

public class Waste {

    private ArrayList<Card> waste;

    public Waste() {
        waste = new ArrayList<>();
    }

    public void addCard(Card card) {
        waste.add(card);
    }

    public Card getCard() {
        return waste.get(waste.size() - 1);
    }

    public Card removeCard() {
        return waste.remove(waste.size() - 1);
    }
}
