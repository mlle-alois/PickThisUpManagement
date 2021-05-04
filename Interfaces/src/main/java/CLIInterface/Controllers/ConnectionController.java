package CLIInterface.Controllers;

import CLIInterface.Menu.GeneralMenu;
import Requete.Body;
import Requete.User;
import javaFXInterface.controllers.BorderPaneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static Enum.InterfaceCode.CONNECTION;


public class ConnectionController {

    private static String errorConnection = "La connexion a échoué";

    public void connection(String mail, String password, Stage window) throws IOException {

        User user = new User();

        var loginBody = new Body();
        loginBody.addValueToBody("mail", mail);
        loginBody.addValueToBody("password", password);

        if (user.login(loginBody)) {
            GeneralMenu.printMenu(window);
        } else {
            System.out.println(errorConnection);
        }
    }
}
