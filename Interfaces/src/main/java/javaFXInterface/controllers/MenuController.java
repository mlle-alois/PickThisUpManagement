package javaFXInterface.controllers;

import Models.Board;
import Models.Liste;
import Models.Status;
import Models.Ticket;
import Requete.Body;
import Requete.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import lombok.SneakyThrows;

public class MenuController {
    @FXML
    private MenuItem deconnexion;
    @FXML
    private MenuItem goCli;
    @FXML
    private Menu boardMenu;
    @FXML
    private Menu ticketMenu;

    String currentBoard;

    private User user;

    public Board[] getBoards(User user) throws JsonProcessingException {
        Body body = new Body();

        return user.getBoards(body);
    }
    @SneakyThrows
    public void initialize(User user) {
        this.user = user;
        initializeBoards();
        initializeTickets();

    }

    private void initializeTickets() throws JsonProcessingException {
        ticketMenu.getItems().addAll(getBranchsTickets(parseTickets()));
    }

    private void initializeBoards() throws JsonProcessingException {
        boardMenu.getItems().addAll(getBranchs(parseBoards()));
    }

    private String[] parseBoards() throws JsonProcessingException {
        Board[] boards = getBoards(user);
        String[] allBoars = new String[boards.length+1];
        for (int i = 0; i < boards.length;i++){
           allBoars[i] = boards[i].boardName;
        }
        allBoars[boards.length] = "Ajouter un tableau";
    return  allBoars;
    }

    private MenuItem[] getBranchs(String[] boards){
        MenuItem[] menuItems = new MenuItem[boards.length];

        for(int i = 0; i < boards.length;i++){
            menuItems[i] = new MenuItem(boards[i]);
        }
        return menuItems;
    }

    public void selectItem(){
      /*  TreeItem<String> item = (TreeItem<String>) treeBoard.getSelectionModel().getSelectedItem();
        if(item != null){
            System.out.println(item.getValue());
        }*/
    }


    // Ticket Parts

    private String[] parseTickets() throws JsonProcessingException {
        Status[] Tickets = getTickets(user);
        String[] allTickets = new String[Tickets.length+1];

        allTickets[0] = "Tous les tickets";

        for (int i = 0; i < Tickets.length;i++){
            allTickets[i+1] = Tickets[i].statusLibelle;
        }
        return allTickets;
    }

    public Status[] getTickets(User user) throws JsonProcessingException {
        Body body = new Body();
        body.addValueToBody("limit","3");
        return user.getStatus(body);
    }
    private MenuItem[] getBranchsTickets(String[] Tickets){
        MenuItem[] tasksItems = new MenuItem[Tickets.length];

        for(int i = 0; i < Tickets.length;i++){
            tasksItems[i] = new MenuItem(Tickets[i]);
        }

        return tasksItems;
    }

    // Déconnexion part
    public boolean disconnect(){

        return true;
    }
}
