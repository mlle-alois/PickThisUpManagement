package CLIInterface.Menu;

import Requete.Body;
import Requete.UserService;
import javaFXInterface.controllers.ContentPanelController;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static Enum.InterfaceCode.BOARD;

public class GeneralMenu {

    public static Scanner clavier = new Scanner(System.in);

    public static void printGeneralMenu(Stage window, UserService user) throws IOException {
        int value = -1;
        do {
            try {
                List<String> menu = new ArrayList<>();
                menu.add("1. Tableaux");
                menu.add("2. Tickets");
                menu.add("3. Passer en UML");
                menu.add("4. Déconnexion");
                menu.add("5. Quitter");
                for (String chaine : menu) {
                    System.out.println(chaine);
                }
                value = Integer.parseInt(clavier.next());
                if (value < 1 || value > 5) {
                    System.out.println("Veuillez saisir un nombre présent dans le menu");
                    value = -1;
                }
            } catch (Exception e) {
                System.out.println("Veuillez saisir un numérique");
            }
        } while (value == -1);
        GeneralMenu.switchGeneralMenu(value, window, user);
    }

    public static void switchGeneralMenu(int value, Stage window, UserService user) throws IOException {
        BoardMenu boardMenu = new BoardMenu();
        TicketMenu ticketMenu = new TicketMenu();
        switch (value) {
            case 1 -> {
                boardMenu.printBoardMenu(window, user);
            }
            case 2 -> {
                ticketMenu.printTicketsStatusMenu(window, user);
            }
            case 4 -> {
                user.logout(new Body());
                ConnectionMenu.printMenu(window, user);
            }
            case 5 -> {
                System.exit(0);
            }
            default -> {
                ContentPanelController.setContentPaneByInterfaceCode(BOARD, window);
            }
        }
    }
}
