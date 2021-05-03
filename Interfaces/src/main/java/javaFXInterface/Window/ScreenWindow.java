package javaFXInterface.Window;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.swing.*;

public class ScreenWindow extends javafx.application.Application{
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader= new FXMLLoader(getClass().getResource("/Connection.fxml"));
        Stage root = loader.load();
        root.setTitle("PickThis");
        root.getIcons().add(new Image("/logo.PNG"));
        root.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
