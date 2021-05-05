package javaFXInterface.controllers;

import Models.Ticket;
import Requete.Body;
import Requete.TicketsService;
import Requete.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import lombok.SneakyThrows;

public class TicketsController {
    @FXML
    private TreeView treeTickets;

    private final TicketsService ticketsService;
    private String currentBoard;

    public TicketsController(User user) {
        this.ticketsService = new TicketsService(user);
    }

    @SneakyThrows
    public void initialize(User user) {

        TreeItem<String> rootBoard = new TreeItem<>("Tickets");

        TreeItem<String>[] allChildTickets = getBranchs(parseTickets());

        rootBoard.getChildren().addAll(allChildTickets);

        treeTickets.setRoot(rootBoard);
    }

    public Ticket[] getTickets() throws JsonProcessingException {
        Body body = new Body();

        return ticketsService.getTickets(body);
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

    public void selectItem() {
        TreeItem<String> item = (TreeItem<String>) treeTickets.getSelectionModel().getSelectedItem();
        if (item != null) {
            System.out.println(item.getValue());
        }
    }
}
