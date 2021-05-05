package CLIInterface.Controllers;

import CLIInterface.Menu.GeneralMenu;
import Requete.Body;
import Requete.User;
import javafx.stage.Stage;

import java.io.IOException;

public class BoardController {

    private static String errorConnection = "La connexion a échoué";

    public void createBoard(String name, Stage window, User user) throws IOException {

        var loginBody = new Body();
        loginBody.addValueToBody("name", name);

        if (user.login(loginBody)) {
            GeneralMenu.printGeneralMenu(window, user);
        } else {
            System.out.println(errorConnection);
        }
    }
}
