import model.Card;
import model.Foundation;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class FoundationTest {
    @Test
    public void testAddStacks() {
        ArrayList<Card> stack = new ArrayList<>();
        Foundation foundation = new Foundation(8);
        foundation.addStack(stack);
        assertEquals(1, foundation.size());
    }
}