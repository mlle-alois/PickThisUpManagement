package CLIInterface.Models;

import CLIInterface.Controllers.ConnectionController;
import Services.UserService;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class ConnectionModel {

    public static Scanner clavier = new Scanner(System.in);

    /**
     * affichage du traitement de connexion
     * @param window
     * @param user
     * @throws IOException
     */
    public static void connectionTreatment(Stage window, UserService user) throws IOException {
        System.out.println("Mail :");
        String mail = clavier.next();
        System.out.println("Mot de passe :");
        //TODO Trouver comment masquer le mot de passe
        String password = clavier.next();

        ConnectionController connectionController = new ConnectionController();
        connectionController.connection(mail, password, window, user);
    }

}
