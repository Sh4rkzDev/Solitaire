import controller.CardController;
import controller.DeckController;
import controller.KlondikeController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Klondike;
import view.KlondikeUI;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        CardController cardController = new CardController();
        Klondike kld = new Klondike((byte) 1);
        KlondikeUI view = new KlondikeUI(stage, kld, cardController);
        KlondikeController controller = new KlondikeController(view, kld);
    }
}
