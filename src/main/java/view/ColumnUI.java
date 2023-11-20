package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.VBox;
import java.util.List;

public class ColumnUI {
    private final VBox col = new VBox(-10);

    public ColumnUI(List<Canvas> cards) {
        col.getChildren().addAll(cards);
    }

    public VBox getCol() {
        return col;
    }
}
