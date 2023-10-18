import java.util.ArrayList;

public interface Interaction {
    ArrayList<Card> getCards();

    boolean move(int tableauCol, int idx, int tableauColDestination);
}
