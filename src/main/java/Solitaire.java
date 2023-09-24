public interface Solitaire {
    boolean validMove(int tableauCol, int idx, int tableauColDestination);
    boolean victory();
}
