public class Card {
    Suit suit;
    String num;
    boolean visible = false;

    public Card(Suit suit, String num) {
        this.suit = suit;
        this.num = num;
    }

    public String getNum() {
        return num;
    }

    public Suit getSuit() {
        return suit;
    }
}
