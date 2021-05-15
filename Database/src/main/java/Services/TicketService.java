package Services;

import Models.*;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.net.http.HttpResponse;

public class TicketService {

    private final DatabaseService databaseService;

    private static final String getTickets = "ticket";
    private static final String addTicket = "ticket/add";
    private static final String getStatus = "ticket/status";
    private static final String getTicketsForStatus = "ticket/getByStatus";
    private static final String getMembersByTicketId = "ticket/getMembers/";
    private static final String closeTicket = "ticket/close";
    private static final String archiveTicket = "ticket/archive";
    private static final String reopenTicket = "ticket/open";
    private static final String updateTicket = "ticket/update";
    private static final String assignUserToTicket = "ticket/assign";
    private static final String unassignUserToTicket = "ticket/unassign";

    public TicketService(UserService user) {
        this.databaseService = new DatabaseService(user);
    }

    public Ticket[] getTickets(Body body) throws JsonProcessingException {
        HttpResponse<String> result = databaseService.GetRequest(body, getTickets);
        if (result.statusCode() < 300) {
            return body.objectMapper.readValue(result.body(), Ticket[].class);
        }
        return new Ticket[0];
    }

    public Ticket addTicket(Body body) throws JsonProcessingException {
        HttpResponse<String> response = databaseService.PostRequest(body, addTicket);
        if (response.statusCode() < 300) {
            return body.objectMapper.readValue(response.body(), Ticket.class);
        }
        return new Ticket();
    }

    public void closeTicket(Body body) throws JsonProcessingException {
        databaseService.PutRequest(body, closeTicket);
    }

    public void archiveTicket(Body body) throws JsonProcessingException {
        databaseService.PutRequest(body, archiveTicket);
    }

    public void reopenTicket(Body body) throws JsonProcessingException {
        databaseService.PutRequest(body, reopenTicket);
    }

    public Ticket updateTicket(Body body) throws JsonProcessingException {
        HttpResponse<String> result = databaseService.PutRequest(body, updateTicket);if (result.statusCode() < 300) {
            return body.objectMapper.readValue(result.body(), Ticket.class);
        }
        return new Ticket();
    }

    public Status[] getTicketsStatus(Body body) throws JsonProcessingException {
        HttpResponse<String> result = databaseService.GetRequest(body, getStatus);
        if (result.statusCode() < 300) {
            return body.objectMapper.readValue(result.body(), Status[].class);
        }
        return new Status[0];
    }

    public User[] getMembersByTicketId(Body body, Integer ticketId) throws JsonProcessingException {
        HttpResponse<String> result = databaseService.GetRequest(body, getMembersByTicketId + ticketId);
        if (result.statusCode() < 300) {
            return body.objectMapper.readValue(result.body(), User[].class);
        }
        return new User[0];
    }

    public Ticket[] getTicketsByStatus(Body body) throws JsonProcessingException {
        HttpResponse<String> result = databaseService.GetRequest(body, getTicketsForStatus);
        if (result.statusCode() < 300) {
            return body.objectMapper.readValue(result.body(), Ticket[].class);
        }
        return new Ticket[0];
    }

    public Ticket assignUserToTicket(Body body) throws JsonProcessingException {
        HttpResponse<String> response = databaseService.PostRequest(body, assignUserToTicket);
        if (response.statusCode() < 300) {
            return body.objectMapper.readValue(response.body(), Ticket.class);
        }
        return new Ticket();
    }

    public boolean unassignUserToTicket(Body body) {
        return databaseService.DeleteRequest(body, unassignUserToTicket);
    }
}
