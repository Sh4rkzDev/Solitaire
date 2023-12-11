import controller.KlondikeController;
import controller.SpiderController;
import javafx.application.Application;
import javafx.stage.Stage;
import controller.MenuController;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        MenuController controller = new MenuController(stage);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        KlondikeController.save();
        SpiderController.save();
    }
}



