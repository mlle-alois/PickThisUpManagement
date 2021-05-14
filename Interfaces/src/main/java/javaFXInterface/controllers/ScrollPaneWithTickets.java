package javaFXInterface.controllers;

import Models.Liste;
import Models.Ticket;
import Requete.User;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ScrollPaneWithTickets {
    private ScrollPane scrollPane;
    private GridPane mainPane;
    private User user;
    private BorderPaneController borderPaneController;
    private Ticket[] tickets;

    public ScrollPaneWithTickets(Ticket[] tickets, User user, BorderPaneController borderPaneController){
        this.tickets = tickets;
        this.scrollPane = new ScrollPane();
        this.mainPane = new GridPane();
        this.user = user;
        this.borderPaneController = borderPaneController;
    }

    public ScrollPane getFullScrollPane(){
        setMainGridPaneShape();
        createOneHBoxPerTask(this.tickets);
        this.scrollPane.setContent(mainPane);
        return this.scrollPane;
    }

    private void setMainGridPaneShape() {
        mainPane.setPadding(new Insets(10, 10, 10, 10));
        mainPane.setHgap(20);
        mainPane.setVgap(20);
    }

    public void createOneHBoxPerTask(Ticket[] tickets){
        for(int i = 0; i < tickets.length;i++){
            HboxforTicket hboxforTicket = new HboxforTicket(tickets[i],user,borderPaneController);
            HBox hbox = hboxforTicket.getFilledHbox();
            mainPane.add(hbox,0,i);
        }
    }

}
