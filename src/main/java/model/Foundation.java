package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Foundation implements Serializable {
    protected ArrayList<ArrayList<Card>> foundation;

    public Foundation(int cols) {
        foundation = new ArrayList<>(cols);
    }

    public void addStack(ArrayList<Card> stack) {
        foundation.add(stack);
    }

    public int size() {
        return foundation.size();
    }
}