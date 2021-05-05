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

    private String[] boards;

    public static Scanner clavier = new Scanner(System.in);

    public void printBoardMenu(Stage window, User user) throws IOException {
        int value = -1;
        MenuController menuController = new MenuController(user);

        this.boards = menuController.parseBoards();
        do {
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
                if (value < 1 || value > boards.length + 1) {
                    System.out.println("Veuillez saisir un nombre présent dans le menu");
                    value = -1;
                }
            } catch (Exception e) {
                System.out.println("Veuillez saisir un numérique");
            }
        } while (value == -1);
        this.switchBoardMenu(value, window, user);
    }

    public void switchBoardMenu(int value, Stage window, User user) throws IOException {
        if(value == boards.length + 1) {
            GeneralMenu.printGeneralMenu(window, user);
        }
        //TODO permettre de naviguer sur le bon tableau selon la valeur saisie
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
