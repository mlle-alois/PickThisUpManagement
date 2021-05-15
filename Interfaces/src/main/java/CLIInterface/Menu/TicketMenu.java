package CLIInterface.Menu;

import CLIInterface.Controllers.TicketController;
import CLIInterface.Models.TicketsModel;
import Models.Ticket;
import Services.Body;
import Services.TicketService;
import Services.UserService;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TicketMenu {

    public static Scanner clavier = new Scanner(System.in);

    private String[] ticketsStatus;

    /**
     * affichage du menu des tickets (leur statut)
     * @param window
     * @param user
     * @throws IOException
     */
    public void printTicketsStatusMenu(Stage window, UserService user) throws IOException {
        int value = -1;
        TicketController menuController = new TicketController(user);

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
        this.switchTicketsStatusMenu(value, window, user);
    }

    public void switchTicketsStatusMenu(int value, Stage window, UserService user) throws IOException {
        TicketService ticketService = new TicketService(user);

        if(value == ticketsStatus.length + 1) {
            GeneralMenu.printGeneralMenu(window, user);
        }
        else if (value == 1){
            Ticket[] tickets = ticketService.getTickets(new Body());
            TicketsModel.printTickets(tickets, window, user, "");
        }
        else {
            Body body = new Body();
            String status = ticketsStatus[value - 1];
            body.addValueToBody("status", status);

            Ticket[] tickets = ticketService.getTicketsByStatus(body);

            TicketsModel.printTickets(tickets, window, user, status);
        }
    }
}
