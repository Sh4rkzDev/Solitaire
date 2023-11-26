import controller.CardController;
import controller.KlondikeController;
import controller.SpdCardController;
import controller.SpiderController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Klondike;
import model.Spider;
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

    public void start(Stage stage) {
        Spider spd = new Spider((byte) 1);
        SpdCardController cardController = new SpdCardController(spd);
        SpiderUI view = new SpiderUI(stage, spd, spdCardController);
        cardController.setUi(view);
        SpiderController controller = new SpiderController(view, spd, spdCardController);
        cardController.setKldController(controller);
    }

}
