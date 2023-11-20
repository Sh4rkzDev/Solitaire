import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        var label = new Label("Hola mundo!");
        var scene = new Scene(new StackPane(label), 1200, 700);
        stage.setScene(scene);
        stage.show();
    }
}
