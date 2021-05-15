package javaFXInterface.controllers;


import Models.Status;
import Models.Ticket;
import Models.User;
import Services.Body;
import Services.TicketService;
import Services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

 import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.io.IOException;
import java.text.DateFormat;
import java.util.List;

public class HboxforTicket {
    private Ticket ticket;
    private UserService user;
    private HBox hbox;
    private List<GridPane> gridPanes;
    private BorderPaneController borderPaneController;
    private Status[] status;
    private TicketService ticketService;

    @SneakyThrows
    public HboxforTicket(Ticket ticket, UserService user, BorderPaneController borderPaneController){
        this.ticket = ticket;
        this.user = user;
        this.borderPaneController = borderPaneController;
        this.hbox = new HBox();
        this.ticketService = new TicketService(user);
        fetchStatus();
    }

    public HBox getFilledHbox(){

        fillHbox();
        return this.hbox;
    }

    private void fillHbox(){
        setHboxShape();
        addName();
        addCreationDate();
        addMember();
        addStatus();
        addDetailsButton();
        addArchiveAndClotureButtons();

    }

    private void addName(){
        Label label = new Label(ticket.ticketName);
        setStrictSizeToLabel(label,120);
        hbox.getChildren().add(label);
        addSeparator();
    }

    private void addDetailsButton(){
        Button button = new Button("Details");

        EventHandler<ActionEvent> buttonDetailsHandler = event -> {

            if(event.getSource() == button) {
                Stage newStage;
                Parent root = null;
                Body body;
                newStage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddTicketToBoard.fxml"));

                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                AddTicketController popupController = loader.getController();
                prepareScreenForTicketModification(popupController);
                newStage.setScene(new Scene(root));
                newStage.initModality(Modality.APPLICATION_MODAL);
                newStage.initOwner(button.getScene().getWindow());
                newStage.setTitle("PickThisUp");
                newStage.getIcons().add(new Image("/logo.PNG"));
                newStage.showAndWait();
                // add the task to the database
                if(!popupController.isValidate())
                    return;
                body = new Body();
                body.addValueToBody("",String.valueOf(ticket.ticketId));
                body.addValueToBody("name",popupController.getName());
                body.addValueToBody("description",popupController.getDescription());
            //    body.addValueToBody("listId",String.valueOf(liste.listId));

                try {
                    ticketService.updateTicket(body);
                    updateStatus (popupController);
                    borderPaneController.addTicketGridToCenter();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                event.consume();
            }
        };

        button.setOnAction(buttonDetailsHandler);
        hbox.getChildren().add(button);
        addSeparator();
    }

    private void updateStatus(AddTicketController popupController) throws JsonProcessingException {
        Body body = new Body();
        body.addValueToBody("",String.valueOf(ticket.ticketId));
        switch (popupController.getStatus()){
            case 0:
                ticketService.reopenTicket(body);
                break;
            case 1:
                ticketService.closeTicket(body);
                break;
            case 2:
                ticketService.archiveTicket(body);
                break;
        }
    }

    @SneakyThrows
    private void prepareScreenForTicketModification(AddTicketController popupController) {
        popupController.setTitleLabel("Details du ticket");
        popupController.setName(ticket.ticketName);
        popupController.setDescription(ticket.ticketDescription);
        popupController.setStatusChoiceBox(status,ticket.statusId);
        popupController.setMembers(getuserModelsMembers());
        popupController.setClotureDate(getClosureDate());
    }

    private void addArchiveAndClotureButtons(){
        EventHandler<ActionEvent> buttonArchiverHandler = event -> {
            try {
                Body body = new Body();
                body.addValueToBody("",String.valueOf(ticket.ticketId));
                      ticketService.archiveTicket(body);
                    borderPaneController.addTicketGridToCenter();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            event.consume();
        };


        Button archiveButton = new Button("Archiver");
        archiveButton.setOnAction(buttonArchiverHandler);

        EventHandler<ActionEvent> buttonclotureHandler = event -> {
            try {
                Body body = new Body();
                body.addValueToBody("",String.valueOf(ticket.ticketId));
                ticketService.closeTicket(body);
                borderPaneController.addTicketGridToCenter();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            event.consume();
        };

        Button clotureButton = new Button("Cloturer");
        clotureButton.setOnAction(buttonclotureHandler);

        hbox.getChildren().addAll(archiveButton,clotureButton);
    }

    private void addSeparator() {
        Separator separator = new Separator();
        separator.setOrientation(Orientation.VERTICAL);
        separator.setStyle("-fx-max-width: 2;");
        hbox.getChildren().add(separator);
    }

    private void addStatus() {
        Label statusLibelle = new Label("Statut :");

        Label status = new Label(getCurrentStatusLibelle());
        status.setMinWidth(60);
        status.setStyle("-fx-font-weight: bold");
        hbox.getChildren().addAll(statusLibelle,status);
        addSeparator();

    }
    @SneakyThrows
    private void addMember(){
        Label membersLibelle = new Label("Membres :");

        Label finalMemberLabel = new Label(getMembersString());
        setStrictSizeToLabel(finalMemberLabel,120);

        hbox.getChildren().addAll(membersLibelle,finalMemberLabel);
        addSeparator();
    }
    @SneakyThrows
    private String getMembersString(){
        StringBuilder membersString = new StringBuilder();

        User[] members = getuserModelsMembers();
        for (int i = 0; i < members.length; i++){
            membersString.append(members[i].firstname);
            if(i != members.length-1)
                membersString.append(",");
        }
        return membersString.toString();
    }

    private User[] getuserModelsMembers() throws JsonProcessingException {
        Body body = new Body();
        User[] members = ticketService.getMembersByTicketId(body, ticket.ticketId);
        return members;
    }

    private String getCurrentStatusLibelle() {
        String currentStatus = null;
        for(int i = 0; i < status.length; i++){
            if (status[i].statusId == ticket.statusId)
                currentStatus = status[i].statusLibelle;
        }
        return currentStatus;
    }

    private void fetchStatus() throws JsonProcessingException {
        Body body = new Body();
        status = ticketService.getTicketsStatus(body);
    }

    private void addCreationDate(){
        Label dateOuverture = new Label("Date d'ouverture :");

        DateFormat shortDateFormat = getDateFormat();

        Label label = new Label(shortDateFormat.format(ticket.ticketCreationDate));
        hbox.getChildren().addAll(dateOuverture,label);
        addSeparator();
    }

    private DateFormat getDateFormat() {
        DateFormat shortDateFormat = DateFormat.getDateTimeInstance(
                DateFormat.SHORT,
                DateFormat.SHORT);
        return shortDateFormat;
    }

    private String getClosureDate(){
        if(ticket.ticketClosingDate == null)
            return "";
        DateFormat shortDateFormat = getDateFormat();
        return shortDateFormat.format(ticket.ticketClosingDate);
    }
    private void setHboxShape(){
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10,10,10,10));
        hbox.setStyle("-fx-border-color: black; -fx-background-color: lightgray;");

    }

    private void setStrictSizeToLabel(Label label,int size){
        label.setMinWidth(size);
    }


}


