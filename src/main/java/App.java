import javafx.application.Application;
import javafx.stage.Stage;
import model.Klondike;
import view.KlondikeUI;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        Klondike kld = new Klondike((byte) 1);
        KlondikeUI view = new KlondikeUI(stage, kld);
    }
}
