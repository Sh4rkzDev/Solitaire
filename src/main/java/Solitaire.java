public interface Solitaire {
    void getCards();

    boolean move(int tableauCol, int idx, int tableauColDestination);

    boolean validMove(int tableauCol, int idx, int tableauColDestination);

    boolean victory();
}
