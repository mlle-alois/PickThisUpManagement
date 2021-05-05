package CLIInterface.Models;

import CLIInterface.Controllers.ConnectionController;
import Requete.User;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class ConnectionModel {

    public static Scanner clavier = new Scanner(System.in);

    public static void connectionTreatment(Stage window, User user) throws IOException {
        System.out.println("Mail :");
        String mail = clavier.next();
        System.out.println("Mot de passe :");
        //TODO Trouver comment masquer le mot de passe
        String password = clavier.next();

        ConnectionController connectionController = new ConnectionController();
        connectionController.connection(mail, password, window, user);
    }

}
