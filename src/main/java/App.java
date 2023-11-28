import javafx.application.Application;
import javafx.stage.Stage;
import controller.MenuController;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        MenuController controller = new MenuController(stage);
    }
}



