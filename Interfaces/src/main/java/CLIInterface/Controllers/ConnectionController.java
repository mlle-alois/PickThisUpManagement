package CLIInterface.Controllers;

import CLIInterface.Menu.ConnectionMenu;
import CLIInterface.Menu.GeneralMenu;
import Requete.Body;
import Requete.UserService;
import javafx.stage.Stage;

import java.io.IOException;


public class ConnectionController {

    private static String errorConnection = "La connexion a échoué";

    /**
     * Connexion d'un utilisateur
     * @param mail
     * @param password
     * @param window
     * @param user
     * @throws IOException
     */
    public void connection(String mail, String password, Stage window, UserService user) throws IOException {

        user = new UserService();

        var loginBody = new Body();
        loginBody.addValueToBody("mail", mail);
        loginBody.addValueToBody("password", password);

        if (user.login(loginBody)) {
            GeneralMenu.printGeneralMenu(window, user);
        } else {
            System.out.println(errorConnection);
            ConnectionMenu.printMenu(window, user);
        }
    }
}
