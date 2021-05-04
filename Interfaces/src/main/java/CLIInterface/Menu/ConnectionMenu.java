package CLIInterface.Menu;

import CLIInterface.Controllers.CLIInterfaceController;
import CLIInterface.Models.ConnectionModel;
import javaFXInterface.controllers.ContentPanelController;
import Enum.InterfaceCode;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConnectionMenu {

    public static Scanner clavier = new Scanner(System.in);

    public static void printMenu(Stage window) throws IOException {
        int value = -1;
        do {
            do {
                try {
                    List<String> menu = new ArrayList<>();
                    menu.add("1. Connexion");
                    /*menu.add("2. Inscription");
                    menu.add("3. Mot de passe oublié");*/
                    menu.add("4. Passer en UML");
                    menu.add("5. Quitter");
                    for (String chaine : menu) {
                        System.out.println(chaine);
                    }
                    value = Integer.parseInt(clavier.next());
                    //if (value < 1 || value > 5) {
                    if(value < 1 || value == 2 || value == 3 || value > 5) {
                        System.out.println("Veuillez saisir un nombre présent dans le menu");
                        value = -1;
                    }
                } catch (Exception e) {
                    System.out.println("Veuillez saisir un numérique");
                }
            } while (value == -1);
            ConnectionMenu.switchMenu(value, window);
        } while (value != 4);
    }

    public static void switchMenu(int value, Stage window) throws IOException {
        switch (value) {
            case 1 -> {
                ConnectionModel.connectionTreatment(window);
            }
            /*case 2 -> {
                CLIInterfaceController.setContentPaneByInterfaceCode(InterfaceCode.INSCRIPTION, window);
            }
            case 3 -> {
                CLIInterfaceController.setContentPaneByInterfaceCode(InterfaceCode.FORGOT_PASSWORD, window);
            }*/
            case 5 -> {
                System.exit(0);
            }
            default -> {
                ContentPanelController.setContentPaneByInterfaceCode(InterfaceCode.CONNECTION, window);
            }
        }
    }
}
