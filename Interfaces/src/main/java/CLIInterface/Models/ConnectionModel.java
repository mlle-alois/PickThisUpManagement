package CLIInterface.Models;

import java.util.Scanner;

public class ConnectionModel {

    public static Scanner clavier = new Scanner(System.in);

    public static void connectionTreatment() {
        System.out.println("Login :");
        String login = clavier.next();
        System.out.println("Mot de passe :");
        //TODO Trouver comment masquer le mot de passe
        String password = clavier.next();
        //TODO connexion
    }

}
