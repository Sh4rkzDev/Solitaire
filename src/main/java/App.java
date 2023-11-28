import controller.KlondikeController;
import controller.SpiderController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Klondike;
import model.Spider;
import view.KlondikeUI;
import view.SpiderUI;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        Klondike kld = new Klondike((byte) 1);
        KlondikeUI view = new KlondikeUI(stage, kld);
        KlondikeController controller = new KlondikeController(view, kld);
    }

    /*public void start(Stage stage) {
        Spider spd = new Spider((byte) 1);
        SpiderUI view = new SpiderUI(stage, spd);
        SpiderController controller = new SpiderController(view, spd);
    }
     */

}
