import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Spider implements Solitaire {

    private Deck deck;
    private final ArrayList<ArrayList<Card>> foundation = new ArrayList<>(8);
    private final ArrayList<ArrayList<Card>> tableau = new ArrayList<>(10);

    public Spider(byte suits) {
        //We represent the Tableau with a matrix made by two arrays, the tableau array and multiple aux arrays
        //In each position of the tableau array we add an aux array, making the tableau positions the columns
        //and the arrays innit (the aux arrays) the rows.
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
        //COMENTARIO GENERAL, PARA MI DEBERIAMOS PONER QUE ROW SEAN LAS FILAS Y COL LAS COLUMNAS, NO TIENE
        //SENTIDO SINO HACER TABLEAU.GET(REALROW) CUANDO TABLEAU REPRESENTA COLUMNAS.
        if (!validMove(row, col, dest)) { //LA FUNCION VALID MOVE USA LOS ROWS COMO FILAS (como debe ser) Y LA COL COMO COLUMNA.
            return;
        }
        int realRow = row-1;
        int realCol = col-1;
        while (tableau.get(realRow).size() != realCol) {
            tableau.get(dest).add(tableau.get(realRow).remove(realCol));
        }
    }

    public boolean validMove(int row, int col, int colDestination){
        //Returns true or false in case the move is valid or not.

        if ( col<tableau.size() && colDestination<tableau.size() && row<tableau.get(col).size()) { //We verify that the given columns and row are valid.

            int indexLastElementOrigin =  tableau.get(col).size()-1;
            int indexLastElementDestination = tableau.get(colDestination).size()-1;
            Card cardOrigin = tableau.get(col).get(row);
            Card cardDestination = tableau.get(col).get(indexLastElementDestination);

            if (cardOrigin.isVisible() && cardDestination.isVisible()){
                return false;
            }

            if (rightOrder(cardDestination,cardOrigin)){
                //Once verified that the chosen card and the last card of the chosen column are in order
                //we verify if the card chosen is only one card or if it's a slice of cards.
                if (row < indexLastElementOrigin && validSlice(row,col,indexLastElementOrigin)){
                    return true;
                }

                if (Objects.equals(row, indexLastElementOrigin)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean validSlice(int row, int col, int indexLastElement){
        //We verify if we can move the whole slice, in order to do that, we have to verify that it's
        //ordered and all from the same suit.
        Card card1;
        Card card2;

        card1 = tableau.get(col).get(row);
        for (int i=row+1; i <= indexLastElement; i++){
            card2 = tableau.get(col).get(i);

            if ( !Objects.equals(card1.getSuit(),card2.getSuit()) || !rightOrder(card1,card2) ){
                return false;
            }
            card1 = card2;
        }

        return true;
    }

    public boolean rightOrder(Card cardOrigin, Card cardDestination){
        //Reminder: two cards are stackable even tho they are not from the same suit.
        ArrayList<String> orderedDeck = new ArrayList<>(
                Arrays.asList("K","Q","J","10","9","8","7","6","5","4","3","2","A")
        );

        return Objects.equals(orderedDeck.indexOf(cardOrigin.getNum())-1, orderedDeck.indexOf(cardDestination.getNum()));
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
