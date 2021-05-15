package CLIInterface.Controllers;

import Models.StatusModel;
import Models.Ticket;
import Models.UserModel;
import Requete.Body;
import Requete.TicketsService;
import Requete.User;
import com.fasterxml.jackson.core.JsonProcessingException;

public class TicketController {

    private final User user;
    private final TicketsService ticketsService;

    public TicketController(User user) {
        this.ticketsService = new TicketsService(user);
        this.user = user;
    }

    /**
     * récupération des libellés qui seront présents dans le menu des tickets
     *
     * @return
     * @throws JsonProcessingException
     */
    public String[] parseTickets() throws JsonProcessingException {
        StatusModel[] Tickets = getTicketsStatus(user);
        String[] allTickets = new String[Tickets.length + 1];

        allTickets[0] = "Tous les tickets";

        for (int i = 0; i < Tickets.length; i++) {
            allTickets[i + 1] = Tickets[i].statusLibelle;
        }
        return allTickets;
    }

    /**
     * récupération de tous les statuts réservés aux tickets
     *
     * @param user
     * @return
     * @throws JsonProcessingException
     */
    public StatusModel[] getTicketsStatus(User user) throws JsonProcessingException {
        Body body = new Body();
        body.addValueToBody("limit", "3");
        return ticketsService.getTicketsStatus(body);
    }

    /**
     * récupération des membres d'un ticket
     *
     * @param ticketId
     * @return
     * @throws JsonProcessingException
     */
    public UserModel[] getMembersByTicketId(Integer ticketId) throws JsonProcessingException {
        Body body = new Body();
        return ticketsService.getMembersByTicketId(body, ticketId);
    }

    /**
     * ajout d'un ticket
     *
     * @param name
     * @param desc
     */
    public void addTicket(String name, String desc) throws JsonProcessingException {
        Body body = new Body();
        body.addValueToBody("name", name);
        body.addValueToBody("description", desc);
        ticketsService.addTicket(body);
    }

    /**
     * récupération des tickets par statut
     *
     * @param status
     * @return
     */
    public Ticket[] getTicketsByStatus(String status) throws JsonProcessingException {
        Ticket[] tickets;
        Body statusBody = new Body();
        if (!status.equals("")) {
            statusBody.addValueToBody("status", status);
            tickets = ticketsService.getTicketsByStatus(statusBody);
        } else {
            tickets = ticketsService.getTickets(statusBody);
        }
        return tickets;
    }

    /**
     * fermer un ticket
     * @param ticketId
     * @throws JsonProcessingException
     */
    public void closeTicket(Integer ticketId) throws JsonProcessingException {
        Body body = new Body();
        body.addValueToBody("",String.valueOf(ticketId));

        ticketsService.closeTicket(body);
    }

    public void archiveTicket(Integer ticketId) throws JsonProcessingException {
        Body body = new Body();
        body.addValueToBody("",String.valueOf(ticketId));
        ticketsService.archiveTicket(body);
    }

    public void reopenTicket(Integer ticketId) throws JsonProcessingException {
        Body body = new Body();
        body.addValueToBody("",String.valueOf(ticketId));
        ticketsService.reopenTicket(body);
    }

    public void updateTicket(Integer ticketId, String name, String desc) throws JsonProcessingException {
        Body body = new Body();
        if(!name.equals(""))
            body.addValueToBody("name", name);
        if(!desc.equals(""))
            body.addValueToBody("description", desc);
        body.addValueToBody("",String.valueOf(ticketId));
        ticketsService.updateTicket(body);
    }
}
