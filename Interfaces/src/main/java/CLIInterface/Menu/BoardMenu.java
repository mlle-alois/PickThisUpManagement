package CLIInterface.Menu;

import CLIInterface.Controllers.MenuController;
import Requete.User;
import javaFXInterface.controllers.BorderPaneController;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BoardMenu {

    public static Scanner clavier = new Scanner(System.in);

    public static void printBoardMenu(Stage window, User user) throws IOException {
        int value = -1;
        MenuController menuController = new MenuController();
        menuController.initialize(user);

        String[] boards = menuController.parseBoards();
        System.out.println(boards.length);
        //do {
            try {

                List<String> menu = new ArrayList<>();
                for(int i = 0 ; i < boards.length ; i+= 1) {
                    menu.add((i + 1) + ". " + boards[i]);
                }
                menu.add((boards.length + 1) + ". Retour");
                for (String chaine : menu) {
                    System.out.println(chaine);
                }
                value = Integer.parseInt(clavier.next());
                if (value < 1 || value > boards.length) {
                    System.out.println("Veuillez saisir un nombre présent dans le menu");
                    value = -1;
                }
            } catch (Exception e) {
                System.out.println("Veuillez saisir un numérique");
            }
        //} while (value == -1);
        BoardMenu.switchBoardMenu(boards.length, value, window, user);
    }

    public static void switchBoardMenu(int length, int value, Stage window, User user) throws IOException {
        if(value == length + 1) {
            GeneralMenu.printGeneralMenu(window, user);
        }
        /*switch (value) {
            case 1 -> {
                BoardMenu.printBoardMenu(window);
            }
            case 2 -> {
                TicketMenu.printTicketsMenu(window);
            }
            case 4 -> {
                User user = new User();
                user.logout(new Body());
                ConnectionMenu.printMenu(window);
            }
            case 5 -> {
                System.exit(0);
            }
            default -> {
                ContentPanelController.setContentPaneByInterfaceCode(BOARD, window);
            }
        }*/
    }
}
