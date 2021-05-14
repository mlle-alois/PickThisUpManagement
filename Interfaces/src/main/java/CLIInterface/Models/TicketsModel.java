package CLIInterface.Models;

import CLIInterface.Controllers.TicketController;
import CLIInterface.Menu.TicketMenu;
import Models.Ticket;
import Models.UserModel;
import Requete.Body;
import Requete.TicketsService;
import Requete.User;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TicketsModel {

    public static Scanner clavier = new Scanner(System.in);

    /**
     * affichage de la liste des tickets avec quelques infos
     *
     * @param tickets
     * @param window
     * @param user
     * @throws IOException
     */
    public static void printTickets(Ticket[] tickets, Stage window, User user, String status) throws IOException {
        TicketController ticketController = new TicketController(user);
        int i = 1;
        for (Ticket ticket : tickets) {
            UserModel[] members = ticketController.getMembersByTicketId(ticket.ticketId);
            StringBuilder membersName = new StringBuilder();

            for (UserModel member : members) {
                membersName.append(member.name).append(" ").append(member.firstname).append(", ");
            }
            System.out.println(i + "/ Nom : " + ticket.ticketName + " - Date d'ouverture : " + ticket.ticketCreationDate +
                    " - Membres : " + membersName + " - Statut : " + ticket.statusLibelle);
            i += 1;
        }

        System.out.println();

        printTicketsActionsMenu(window, user, tickets, status);
    }

    /**
     * affichage d'un ticket en détail
     *
     * @param tickets
     * @param value
     * @param window
     * @param user
     * @throws IOException
     */
    public static void printTicket(Ticket[] tickets, int value, Stage window, User user, String status) throws IOException {
        TicketController ticketController = new TicketController(user);

        Ticket ticket = tickets[value - 1];

        UserModel[] members = ticketController.getMembersByTicketId(ticket.ticketId);
        StringBuilder membersName = new StringBuilder();

        for (UserModel member : members) {
            membersName.append(member.name).append(" ").append(member.firstname).append(", ");
        }
        System.out.println("Nom : " + ticket.ticketName + " - Description : " + ticket.ticketDescription +
                " - Date d'ouverture : " + ticket.ticketCreationDate + " - Créateur : " + ticket.creatorId +
                " - Membres : " + membersName + " - Statut : " + ticket.statusLibelle + " - Date de clôture : " +
                ticket.ticketClosingDate);

        printTicketActionsMenu(window, user, tickets, ticket, status);
    }

    /**
     * affichage des actions possibles d'effectuer sur un ticket
     *
     * @param window
     * @param user
     * @param tickets
     * @throws IOException
     */
    public static void printTicketActionsMenu(Stage window, User user, Ticket[] tickets, Ticket ticket, String status) throws IOException {
        int value = -1;

        do {
            try {
                List<String> menu = new ArrayList<>();
                menu.add("1. Modifier");
                //cloturer réouvrir archiver
                int i = 2;
                if (ticket.statusId == 1) {
                    menu.add(i + ". Clôturer");
                    i += 1;
                    menu.add(i + ". Archiver");
                } else {
                    menu.add(i + ". Réouvrir");
                }
                i += 1;
                menu.add(i + ". Retour");
                for (String chaine : menu) {
                    System.out.println(chaine);
                }
                value = Integer.parseInt(clavier.next());
                if (value < 1 || value > i) {
                    System.out.println("Veuillez saisir un nombre présent dans le menu");
                    value = -1;
                }
            } catch (Exception e) {
                System.out.println("Veuillez saisir un numérique");
            }
        } while (value == -1);
        switchTicketActionMenu(value, window, user, tickets, ticket, status);
    }

    /**
     * action selon ce qui a été sélectionné par l'utilisateur
     *
     * @param value
     * @param window
     * @param user
     * @param tickets
     * @throws IOException
     */
    public static void switchTicketActionMenu(int value, Stage window, User user, Ticket[] tickets, Ticket ticket, String status) throws IOException {
        TicketMenu ticketMenu = new TicketMenu();
        switch (value) {
            case 1:
                updateTicketTreatment(window, user, tickets, ticket, status);
                break;
            case 2, 3:
                if (ticket.statusId == 1) {
                    if (value == 2) {
                        closureTicketTreatment(window, user, tickets, ticket, status);
                    } else {
                        archiveTicketTreatment(window, user, tickets, ticket, status);
                    }
                } else {
                    if (value == 2) {
                        reopenTicketTreatment(window, user, tickets, ticket, status);
                    } else {
                        printTickets(tickets, window, user, status);
                    }
                }
                break;
            default:
                printTickets(tickets, window, user, status);
        }
    }

    /**
     * affichage des actions réalisables sur les tickets
     *
     * @param window
     * @param user
     * @param tickets
     * @throws IOException
     */
    public static void printTicketsActionsMenu(Stage window, User user, Ticket[] tickets, String status) throws IOException {
        int value = -1;

        do {
            try {
                List<String> menu = new ArrayList<>();
                menu.add("1. Détails");
                menu.add("2. Ajouter un ticket");
                menu.add("3. Retour");
                for (String chaine : menu) {
                    System.out.println(chaine);
                }
                value = Integer.parseInt(clavier.next());
                if (value < 1 || value > 3) {
                    System.out.println("Veuillez saisir un nombre présent dans le menu");
                    value = -1;
                }
            } catch (Exception e) {
                System.out.println("Veuillez saisir un numérique");
            }
        } while (value == -1);
        switchTicketsActionMenu(value, window, user, tickets, status);
    }

    /**
     * action selon ce qui a été choisi par l'utilisateur
     *
     * @param value
     * @param window
     * @param user
     * @param tickets
     * @throws IOException
     */
    public static void switchTicketsActionMenu(int value, Stage window, User user, Ticket[] tickets, String status) throws IOException {
        TicketMenu ticketMenu = new TicketMenu();
        switch (value) {
            case 1:
                selectTicketTreatment(window, user, tickets, status);
                break;
            case 2:
                addTicketTreatment(window, user, tickets, status);
                break;
            default:
                ticketMenu.printTicketsStatusMenu(window, user);
        }
    }

    /**
     * traitement de l'ajout d'un ticket
     *
     * @param window
     * @param user
     * @param tickets
     * @throws IOException
     */
    public static void addTicketTreatment(Stage window, User user, Ticket[] tickets, String status) throws IOException {
        String name = "";
        String desc = "";
        do {
            System.out.println("Nom du ticket :");
            name = clavier.nextLine();

            if (name.equals("")) {
                System.out.println("Veuillez saisir un nom");
            }
        } while (name.equals(""));

        System.out.println("Description du ticket :");
        desc = clavier.nextLine();

        TicketsService ticketsService = new TicketsService(user);

        Body body = new Body();
        body.addValueToBody("name", name);
        body.addValueToBody("description", desc);
        ticketsService.addTicket(body);

        Body statusBody = new Body();
        if (!status.equals("")) {
            statusBody.addValueToBody("status", status);
            tickets = ticketsService.getTicketsByStatus(statusBody);
        } else {
            tickets = ticketsService.getTickets(statusBody);
        }
        TicketsModel.printTickets(tickets, window, user, status);
    }

    /**
     * traitement de la sélection d'un ticket
     *
     * @param window
     * @param user
     * @param tickets
     * @throws IOException
     */
    public static void selectTicketTreatment(Stage window, User user, Ticket[] tickets, String status) throws IOException {
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
        TicketsModel.printTicket(tickets, value, window, user, status);
    }

    /**
     * cloturer un ticket
     *
     * @param window
     * @param user
     * @param tickets
     * @param ticket
     * @throws IOException
     */
    public static void closureTicketTreatment(Stage window, User user, Ticket[] tickets, Ticket ticket, String status) throws IOException {
        TicketsService ticketsService = new TicketsService(user);
        ticketsService.closeTicket(new Body(), ticket.ticketId);

        Body statusBody = new Body();
        if (!status.equals("")) {
            statusBody.addValueToBody("status", status);
            tickets = ticketsService.getTicketsByStatus(statusBody);
        } else {
            tickets = ticketsService.getTickets(statusBody);
        }
        TicketsModel.printTickets(tickets, window, user, status);
    }

    /**
     * réouvrir un ticket
     *
     * @param window
     * @param user
     * @param tickets
     * @param ticket
     * @throws IOException
     */
    public static void reopenTicketTreatment(Stage window, User user, Ticket[] tickets, Ticket ticket, String status) throws IOException {
        TicketsService ticketsService = new TicketsService(user);
        ticketsService.reopenTicket(new Body(), ticket.ticketId);

        Body statusBody = new Body();
        if (!status.equals("")) {
            statusBody.addValueToBody("status", status);
            tickets = ticketsService.getTicketsByStatus(statusBody);
        } else {
            tickets = ticketsService.getTickets(statusBody);
        }
        TicketsModel.printTickets(tickets, window, user, status);
    }

    /**
     * archiver un ticket
     *
     * @param window
     * @param user
     * @param tickets
     * @param ticket
     * @throws IOException
     */
    public static void archiveTicketTreatment(Stage window, User user, Ticket[] tickets, Ticket ticket, String status) throws IOException {
        TicketsService ticketsService = new TicketsService(user);
        ticketsService.archiveTicket(new Body(), ticket.ticketId);

        Body statusBody = new Body();
        if (!status.equals("")) {
            statusBody.addValueToBody("status", status);
            tickets = ticketsService.getTicketsByStatus(statusBody);
        } else {
            tickets = ticketsService.getTickets(statusBody);
        }
        TicketsModel.printTickets(tickets, window, user, status);
    }

    /**
     * modifier un ticket
     *
     * @param window
     * @param user
     * @param tickets
     * @param ticket
     * @throws IOException
     */
    public static void updateTicketTreatment(Stage window, User user, Ticket[] tickets, Ticket ticket, String status) throws IOException {
        String name = "";
        String desc = "";

        clavier.nextLine();

        System.out.println("Nom du ticket :");
        name = clavier.nextLine();

        System.out.println("Description du ticket :");
        desc = clavier.nextLine();

        TicketsService ticketsService = new TicketsService(user);

        Body body = new Body();
        if(!name.equals(""))
            body.addValueToBody("name", name);
        if(!desc.equals(""))
            body.addValueToBody("description", desc);
        ticketsService.updateTicket(body, ticket.ticketId);

        Body statusBody = new Body();
        if (!status.equals("")) {
            statusBody.addValueToBody("status", status);
            tickets = ticketsService.getTicketsByStatus(statusBody);
        } else {
            tickets = ticketsService.getTickets(statusBody);
        }
        TicketsModel.printTickets(tickets, window, user, status);
    }
}
