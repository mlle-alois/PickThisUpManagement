package JavaFXInterface.Window;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class ScreenWindow extends javafx.application.Application{
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader= new FXMLLoader(getClass().getResource("/sample.fxml"));
        Stage root = loader.load();
        root.setTitle("WSHH");

        root.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
