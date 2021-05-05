package javaFXInterface.controllers;

import Models.Board;
import Models.Ticket;
import Requete.Body;
import Requete.TicketsService;
import Requete.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import lombok.SneakyThrows;

public class BoardController  {
    @FXML
    private TreeView treeBoard;
    @FXML
    private TreeView treeTickets;

    private String currentBoard;
    private final TicketsService ticketsService;

    public BoardController(User user) {
        this.ticketsService = new TicketsService(user);
    }


    public Board[] getBoards(User user) throws JsonProcessingException {
        Body body = new Body();

        return user.getBoards(body);
    }

    @SneakyThrows
    public void initialize(User user) {
        initializeBoards(user);
        initializeTickets(user);
    }

    private void initializeTickets(User user) throws JsonProcessingException {
        TreeItem<String> rootBoard = new TreeItem<>("Tickets");
        TreeItem<String>[] allChildTickets = getBranchsTickets(parseTickets(user));
        rootBoard.getChildren().addAll(allChildTickets);
        treeTickets.setRoot(rootBoard);
    }

    private void initializeBoards(User user) throws JsonProcessingException {
        TreeItem<String> rootBoard = new TreeItem<>("Tableaux");
        TreeItem<String>[] allChildBoards = getBranchs(parseBoards(user));
        rootBoard.getChildren().addAll(allChildBoards);
        treeBoard.setFixedCellSize(20);
        treeBoard.setRoot(rootBoard);


    }

    private String[] parseBoards(User user) throws JsonProcessingException {
        Board[] boards = getBoards(user);
        String[] allBoars = new String[boards.length];
        for (int i = 0; i < boards.length;i++){
           allBoars[i] = boards[i].boardName;
        }

    return  allBoars;
    }

    private TreeItem<String>[] getBranchs(String[] boards){
        TreeItem<String>[] treeItems = new TreeItem[boards.length];

        for(int i = 0; i < boards.length;i++){
            treeItems[i] = new TreeItem<>(boards[i]);
        }

        return treeItems;
    }

    public void selectItem(){
        TreeItem<String> item = (TreeItem<String>) treeBoard.getSelectionModel().getSelectedItem();
        if(item != null){
            System.out.println(item.getValue());
        }
    }


    // Ticket Parts
    private String[] parseTickets(User user) throws JsonProcessingException {
        Ticket[] Tickets = getTickets(user);
        String[] allTickets = new String[Tickets.length];
        for (int i = 0; i < Tickets.length;i++){
            allTickets[i] = Tickets[i].ticketName;
        }

        return  allTickets;
    }

    public Ticket[] getTickets(User user) throws JsonProcessingException {
        Body body = new Body();

        return ticketsService.getTickets(body);
    }
    private TreeItem<String>[] getBranchsTickets(String[] Tickets){
        TreeItem<String>[] treeItems = new TreeItem[Tickets.length];

        for(int i = 0; i < Tickets.length;i++){
            treeItems[i] = new TreeItem<>(Tickets[i]);
        }

        return treeItems;
    }
}
