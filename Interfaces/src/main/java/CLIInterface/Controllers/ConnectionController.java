package CLIInterface.Controllers;

import CLIInterface.Menu.GeneralMenu;
import Requete.Body;
import Requete.User;
import javafx.stage.Stage;

import java.io.IOException;


public class ConnectionController {

    private static String errorConnection = "La connexion a échoué";

    public void connection(String mail, String password, Stage window) throws IOException {

        User user = new User();

        var loginBody = new Body();
        loginBody.addValueToBody("mail", mail);
        loginBody.addValueToBody("password", password);

        if (user.login(loginBody)) {
            GeneralMenu.printGeneralMenu(window);
        } else {
            System.out.println(errorConnection);
        }
    }
}
