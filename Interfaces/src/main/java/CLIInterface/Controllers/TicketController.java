package CLIInterface.Controllers;

import Models.Status;
import Models.Task;
import Models.Ticket;
import Models.User;
import Services.Body;
import Services.StatusService;
import Services.TicketService;
import Services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;

public class TicketController {

    private final UserService user;
    private final TicketService ticketService;

    public TicketController(UserService user) {
        this.ticketService = new TicketService(user);
        this.user = user;
    }

    /**
     * récupération des libellés qui seront présents dans le menu des tickets
     *
     * @return
     * @throws JsonProcessingException
     */
    public String[] parseTickets() throws JsonProcessingException {
        Status[] Tickets = getTicketsStatus(user);
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
    public Status[] getTicketsStatus(UserService user) throws JsonProcessingException {
        Body body = new Body();
        body.addValueToBody("limit", "3");
        StatusService userService = new StatusService(user);
        return userService.getStatus(body);
    }

    /**
     * récupération des membres d'un ticket
     *
     * @param ticketId
     * @return
     * @throws JsonProcessingException
     */
    public User[] getMembersByTicketId(Integer ticketId) throws JsonProcessingException {
        Body body = new Body();
        return ticketService.getMembersByTicketId(body, ticketId);
    }

    /**
     * ajout d'un ticket
     *
     * @param name
     * @param desc
     */
    public Ticket addTicket(String name, String desc) throws JsonProcessingException {
        Body body = new Body();
        body.addValueToBody("name", name);
        body.addValueToBody("description", desc);
        return ticketService.addTicket(body);
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
            tickets = ticketService.getTicketsByStatus(statusBody);
        } else {
            tickets = ticketService.getTickets(statusBody);
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
        body.addValueToBody("", String.valueOf(ticketId));

        ticketService.closeTicket(body);
    }

    public void archiveTicket(Integer ticketId) throws JsonProcessingException {
        Body body = new Body();
        body.addValueToBody("",String.valueOf(ticketId));
        ticketService.archiveTicket(body);
    }

    public void reopenTicket(Integer ticketId) throws JsonProcessingException {
        Body body = new Body();
        body.addValueToBody("",String.valueOf(ticketId));
        ticketService.reopenTicket(body);
    }

    public Ticket updateTicket(Integer ticketId, String name, String desc) throws JsonProcessingException {
        Body body = new Body();
        if(!name.equals(""))
            body.addValueToBody("name", name);
        if(!desc.equals(""))
            body.addValueToBody("description", desc);
        body.addValueToBody("", String.valueOf(ticketId));
        return ticketService.updateTicket(body);
    }

    public Ticket assignUserToTicket(Integer ticketId, String mail) throws JsonProcessingException {
        Body assignBody = new Body();
        assignBody.addValueToBody("ticketId", String.valueOf(ticketId));
        assignBody.addValueToBody("userMail", mail);
        return ticketService.assignUserToTicket(assignBody);
    }

    public boolean unassignUserToTicket(Integer ticketId, String mail) {
        Body unassignBody = new Body();
        unassignBody.addValueToBody("ticketId", String.valueOf(ticketId));
        unassignBody.addValueToBody("userMail", mail);
        return ticketService.unassignUserToTicket(unassignBody);
    }
}
