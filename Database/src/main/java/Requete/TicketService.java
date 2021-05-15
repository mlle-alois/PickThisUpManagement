package Requete;

import Models.*;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.net.http.HttpResponse;

public class TicketService {

    private final DatabaseService databaseService;

    private static final String getTickets = "ticket";
    private static final String addTicket = "ticket/add";
    private static final String getStatus = "ticket/status";
    private static final String getTicketsForStatus = "ticket/getByStatus";
    private static final String getMembersByTicketId = "ticket/getMembers";
    private static final String closeTicket = "ticket/close";
    private static final String archiveTicket = "ticket/archive";
    private static final String reopenTicket = "ticket/open";
    private static final String updateTicket = "ticket/update";

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

    public void addTicket(Body body) throws JsonProcessingException {
        databaseService.PostRequest(body, addTicket);
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

    public void updateTicket(Body body) throws JsonProcessingException {
        databaseService.PutRequest(body, updateTicket);
    }

    public StatusModel[] getTicketsStatus(Body body) throws JsonProcessingException {
        HttpResponse<String> result = databaseService.GetRequest(body, getStatus);
        if (result.statusCode() < 300) {
            return body.objectMapper.readValue(result.body(), StatusModel[].class);
        }
        return new StatusModel[0];
    }

    public UserModel[] getMembersByTicketId(Body body, Integer ticketId) throws JsonProcessingException {
        HttpResponse<String> result = databaseService.GetRequest(body, getMembersByTicketId + ticketId);
        if (result.statusCode() < 300) {
            return body.objectMapper.readValue(result.body(), UserModel[].class);
        }
        return new UserModel[0];
    }

    public Ticket[] getTicketsByStatus(Body body) throws JsonProcessingException {
        HttpResponse<String> result = databaseService.GetRequest(body, getTicketsForStatus);
        if (result.statusCode() < 300) {
            return body.objectMapper.readValue(result.body(), Ticket[].class);
        }
        return new Ticket[0];
    }
}
