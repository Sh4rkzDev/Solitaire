import controller.CardController;
import controller.KlondikeController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Klondike;
import view.KlondikeUI;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        Klondike kld = new Klondike((byte) 1);
        CardController cardController = new CardController(kld);
        KlondikeUI view = new KlondikeUI(stage, kld, cardController);
        cardController.setUi(view);
        KlondikeController controller = new KlondikeController(view, kld, cardController);
        cardController.setKldController(controller);
    }
}
