import Enum.InterfaceCode;
import javaFXInterface.Window.MainWindow;
import javaFXInterface.Window.ScreenWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Application1 {


 /*   @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("samples.fxml"));
        Stage root = loader.load();
        root.setTitle("WSHH");

        root.show();

       /* Parent root = FXMLLoader.load(getClass().getResource("samples.fxml"));
        stage.setTitle("WSHH");
       // stage.setScene(new Scene(root));
        stage.show();*/


    public static void main(String[] args) throws IOException {
       ScreenWindow.main(args);

       // MainWindow applicationInterface = new MainWindow(InterfaceCode.CONNECTION);

    }


}
