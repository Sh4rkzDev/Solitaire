import java.util.ArrayList;

public interface Solitaire {
    ArrayList<Card> getCards();

    boolean move(int tableauCol, int idx, int tableauColDestination);

    boolean validMove(int tableauCol, int idx, int tableauColDestination);

    boolean victory();
}
