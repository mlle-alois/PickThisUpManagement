package CLIInterface.Models;

import CLIInterface.Controllers.TicketController;
import CLIInterface.Menu.TicketMenu;
import Models.StatusModel;
import Models.Ticket;
import Models.UserModel;
import Requete.User;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TicketsModel {

    public static Scanner clavier = new Scanner(System.in);

    public static void printTickets(Ticket[] tickets, Stage window, User user) throws IOException {
        TicketController ticketController = new TicketController(user);
        int i = 1;
        for (Ticket ticket : tickets) {
            UserModel[] members = ticketController.getMembersByTicketId(ticket.ticketId);
            StringBuilder membersName = new StringBuilder();

            for(UserModel member : members) {
                membersName.append(member.name).append(" ").append(member.firstname).append(", ");
            }
            System.out.println(i + "/ Nom : " + ticket.ticketName + " - Date d'ouverture : " + ticket.ticketCreationDate +
                    " - Membres : " + membersName + " - Statut : " + ticket.statusLibelle);
            i += 1;
        }

        printActionsMenu(window, user, tickets);
        TicketMenu ticketMenu = new TicketMenu();
        ticketMenu.printTicketsMenu(window, user);
    }

    public static void printActionsMenu(Stage window, User user, Ticket[] tickets) throws IOException {
        int value = -1;

        do {
            try {
                List<String> menu = new ArrayList<>();
                menu.add("1. Détails");
                menu.add("2. Retour");
                for (String chaine : menu) {
                    System.out.println(chaine);
                }
                value = Integer.parseInt(clavier.next());
                if (value < 1 || value > 2) {
                    System.out.println("Veuillez saisir un nombre présent dans le menu");
                    value = -1;
                }
            } catch (Exception e) {
                System.out.println("Veuillez saisir un numérique");
            }
        } while (value == -1);
        switchActionMenu(value, window, user, tickets);
    }

    public static void switchActionMenu(int value, Stage window, User user, Ticket[] tickets) throws IOException {
        TicketMenu ticketMenu = new TicketMenu();
        if (value == 1) {
            selectTicketTreatment(window, user, tickets);
        } else {
            ticketMenu.printTicketsMenu(window, user);
        }
    }

    public static void selectTicketTreatment(Stage window, User user, Ticket[] tickets) throws IOException {
        int value = -1;
        do {
            try {
                System.out.println("Lequel des tickets voulez-vous détailler ?");

                value = Integer.parseInt(clavier.next());
                if (value < 1 || value > tickets.length) {
                    System.out.println("Veuillez saisir un nombre présent dans le menu");
                    value = -1;
                }
            } catch (Exception e) {
                System.out.println("Veuillez saisir un numérique");
            }
        } while (value == -1);
        TicketMenu ticketMenu = new TicketMenu();
        ticketMenu.printTicketsMenu(window, user);
    }
}
