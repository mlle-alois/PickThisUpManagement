package javaFXInterface.controllers;

import Models.Board;
import Models.Ticket;
import Requete.Body;
import Requete.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import lombok.SneakyThrows;

public class TicketsController {
    @FXML
    private TreeView treeTickets;

    String currentBoard;

    public Ticket[] getTickets(User user) throws JsonProcessingException {
        Body body = new Body();

        return user.getTickets(body);
    }
    @SneakyThrows
    public void initialize(User user) {

            TreeItem<String> rootBoard = new TreeItem<>("Tickets");

           TreeItem<String>[] allChildTickets = getBranchs(parseTickets(user));

            rootBoard.getChildren().addAll(allChildTickets);

            treeTickets.setRoot(rootBoard);


    }
    private String[] parseTickets(User user) throws JsonProcessingException {
        Ticket[] Tickets = getTickets(user);
        String[] allTickets = new String[Tickets.length];
        for (int i = 0; i < Tickets.length;i++){
           allTickets[i] = Tickets[i].ticketName;
        }

    return  allTickets;
    }

    private TreeItem<String>[] getBranchs(String[] Tickets){
        TreeItem<String>[] treeItems = new TreeItem[Tickets.length];

        for(int i = 0; i < Tickets.length;i++){
            treeItems[i] = new TreeItem<>(Tickets[i]);
        }

        return treeItems;
    }

    public void selectItem(){
        TreeItem<String> item = (TreeItem<String>) treeTickets.getSelectionModel().getSelectedItem();
        if(item != null){
            System.out.println(item.getValue());
        }
    }
}
