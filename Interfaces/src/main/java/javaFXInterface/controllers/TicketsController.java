package javaFXInterface.controllers;

import Models.Ticket;
import Services.Body;
import Services.TicketService;
import Services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import lombok.SneakyThrows;

public class TicketsController {
    @FXML
    private TreeView treeTickets;

    private final TicketService ticketService;
    private String currentBoard;

    public TicketsController(UserService user) {
        this.ticketService = new TicketService(user);
    }

    @SneakyThrows
    public void initialize(UserService user) {

        TreeItem<String> rootBoard = new TreeItem<>("Tickets");

        TreeItem<String>[] allChildTickets = getBranchs(parseTickets());

        rootBoard.getChildren().addAll(allChildTickets);

        treeTickets.setRoot(rootBoard);
    }

    public Ticket[] getTickets() throws JsonProcessingException {
        Body body = new Body();

        return ticketService.getTickets(body);
    }

    private String[] parseTickets() throws JsonProcessingException {
        Ticket[] Tickets = getTickets();
        String[] allTickets = new String[Tickets.length];
        for (int i = 0; i < Tickets.length; i++) {
            allTickets[i] = Tickets[i].ticketName;
        }

        return allTickets;
    }

    private TreeItem<String>[] getBranchs(String[] Tickets) {
        TreeItem<String>[] treeItems = new TreeItem[Tickets.length];

        for (int i = 0; i < Tickets.length; i++) {
            treeItems[i] = new TreeItem<>(Tickets[i]);
        }

        return treeItems;
    }
}
