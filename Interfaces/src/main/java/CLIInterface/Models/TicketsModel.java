package CLIInterface.Models;

import CLIInterface.Controllers.BoardController;
import CLIInterface.Menu.TicketMenu;
import Models.Ticket;
import Requete.User;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class TicketsModel {

    public static Scanner clavier = new Scanner(System.in);

    public static void printBoards(Ticket[] tickets, Stage window, User user) throws IOException {
        for (Ticket ticket : tickets) {
            System.out.println(ticket.ticketName);
        }

        TicketMenu ticketMenu = new TicketMenu();
        ticketMenu.printTicketsMenu(window, user);
    }
}
