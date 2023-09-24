public interface Solitaire {
    void getCards();
    void move(int tableauCol, int idx, int tableauColDestination);
    boolean validMove(int tableauCol, int idx, int tableauColDestination);
    boolean victory();
}
