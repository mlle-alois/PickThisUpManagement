package CLIInterface.Controllers;

import Models.StatusModel;
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

    public String[] parseTickets() throws JsonProcessingException {
        StatusModel[] Tickets = getTicketsStatus(user);
        String[] allTickets = new String[Tickets.length + 1];

        allTickets[0] = "Tous les tickets";

        for (int i = 0; i < Tickets.length; i++) {
            allTickets[i + 1] = Tickets[i].statusLibelle;
        }
        return allTickets;
    }

    public StatusModel[] getTicketsStatus(User user) throws JsonProcessingException {
        Body body = new Body();
        body.addValueToBody("limit", "3");
        return ticketsService.getTicketsStatus(body);
    }

    public UserModel[] getMembersByTicketId(Integer ticketId) throws JsonProcessingException {
        Body body = new Body();
        return ticketsService.getMembersByTicketId(body, ticketId);
    }
}
