package CLIInterface.Menu;

import CLIInterface.Controllers.MenuController;
import CLIInterface.Models.TicketsModel;
import Models.Ticket;
import Requete.Body;
import Requete.TicketsService;
import Requete.User;
import javaFXInterface.controllers.TicketsController;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TicketMenu {

    public static Scanner clavier = new Scanner(System.in);

    private String[] ticketsStatus;

    public void printTicketsMenu(Stage window, User user) throws IOException {
        int value = -1;
        MenuController menuController = new MenuController(user);

        this.ticketsStatus = menuController.parseTickets();
        do {
            try {
                List<String> menu = new ArrayList<>();
                for(int i = 0; i < ticketsStatus.length ; i+= 1) {
                    menu.add((i + 1) + ". " + ticketsStatus[i]);
                }
                menu.add((ticketsStatus.length + 1) + ". Retour");
                for (String chaine : menu) {
                    System.out.println(chaine);
                }
                value = Integer.parseInt(clavier.next());
                if (value < 1 || value > ticketsStatus.length + 1) {
                    System.out.println("Veuillez saisir un nombre présent dans le menu");
                    value = -1;
                }
            } catch (Exception e) {
                System.out.println("Veuillez saisir un numérique");
            }
        } while (value == -1);
        this.switchTicketMenu(value, window, user);
    }

    public void switchTicketMenu(int value, Stage window, User user) throws IOException {
        TicketsService ticketsService = new TicketsService(user);

        if(value == ticketsStatus.length + 1) {
            GeneralMenu.printGeneralMenu(window, user);
        }
        else if (value == 1){
            Ticket[] tickets = ticketsService.getTickets(new Body());
            TicketsModel.printBoards(tickets, window, user);
        }
        else {
            Body body = new Body();
            body.addValueToBody("status", ticketsStatus[value - 1]);

            Ticket[] selectedTickets = ticketsService.getTicketsByStatus(body);

            this.printTicketsMenu(window, user);
        }
        //TODO permettre de naviguer sur le bon type de tickets selon la valeur saisie
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
