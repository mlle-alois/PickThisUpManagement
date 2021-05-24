package javaFXInterface.controllers;

import Models.Liste;
import Models.Task;
import Models.User;
import Services.Body;
import Services.TaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.List;

public class GridPaneForTask {
    private Task task;
    private BorderPaneController borderPaneController;
    private Liste liste;
    private GridPane newGrid;
    private TaskService taskService;
    private User[] users;

    public GridPaneForTask(Task task, BorderPaneController borderPaneController, Liste liste) {
        this.task = task;
        this.borderPaneController = borderPaneController;
        this.liste = liste;
        this.newGrid = new GridPane();
        this.taskService = new TaskService(borderPaneController.getUser());
    }


    public GridPane getNewGrid() {
        return newGrid;
    }

    public void setGridShape() {
        newGrid.setPadding(new Insets(10, 10, 10, 10));
        //   newGrid.setGridLinesVisible(true);

        newGrid.setPrefSize(100, 180);
        // set style
        newGrid.setStyle("-fx-background-color: blue, lightgray;-fx-border-color:black; -fx-border-width: 1; -fx-border-style: solid;");
        // set Gap between row and column
        newGrid.setHgap(10);
        newGrid.setVgap(5);

        // Set Column constraints
        ColumnConstraints column1 = new ColumnConstraints();
        ColumnConstraints column2 = new ColumnConstraints();
        column1.setPrefWidth(100);
        column2.setPrefWidth(200);

        newGrid.getColumnConstraints().add(column1);
        newGrid.getColumnConstraints().add(column2);

        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints();
        row1.setPrefHeight(100);
        row2.setPrefHeight(200);

        newGrid.getRowConstraints().add(row1);
        newGrid.getRowConstraints().add(row2);
    }

    @SneakyThrows
    public void addTaskToGrid() {
        // add title of task
        Label lblTaskName = new Label(task.taskName);
        newGrid.add(lblTaskName, 0, 0);
        // add task's description
        TextArea lblTextArea = new TextArea(task.taskDescription);
        lblTextArea.setWrapText(true);
        lblTextArea.setEditable(false);
        lblTextArea.setStyle("-fx-control-inner-background: lightgray;");
        newGrid.add(lblTextArea, 0, 1);
        // add Label for members
        Label memberLabel = new Label("Membres :");
        newGrid.add(memberLabel, 1, 0);
        // add members
         users = taskService.getMembersByTaskId(new Body(), task.taskId);
        VBox vbox = new VBox();
        for (User user : users) {
            Label label = new Label(user.mail);
            vbox.getChildren().add(label);
        }
        newGrid.add(vbox, 1, 1);

        addButtonBarToGrid();
    }

    private void addButtonBarToGrid() {
        // Set Event when clicked from buttons
        TaskService taskService = new TaskService(borderPaneController.getUser());
        Button modifButton = new Button("Modifier");
        EventHandler<ActionEvent> buttonModifHandler = event -> {
            if (event.getSource() == modifButton) {
                Stage newStage;
                Parent root = null;
                Body body;
                newStage = new Stage();
             //   FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddTaskToList.fxml"));
                  FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddTicketToBoard.fxml"));

                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                AddTicketController popupController = loader.getController();
                prepareScreenForTaskModification(event, popupController);
                newStage.setScene(new Scene(root));
                newStage.initModality(Modality.APPLICATION_MODAL);
                newStage.initOwner(modifButton.getScene().getWindow());
                newStage.setTitle("PickThisUp");
                newStage.getIcons().add(new Image("/logo.PNG"));
                newStage.showAndWait();
                if (!popupController.isValidate())
                    return;
                // add the task to the database
                body = new Body();
                //  addLabelIdToBody(body,getClickedLabelName(event));
                body.addValueToBody("", String.valueOf(task.taskId));
                body.addValueToBody("name", popupController.getName());
                body.addValueToBody("description", popupController.getDescription());
                body.addValueToBody("listId", String.valueOf(liste.listId));

                try {
                    taskService.updateTask(body);
                    assignNewMember(body,popupController);
                    unassignNewMember(body,popupController);
                    borderPaneController.setBorderPane();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                event.consume();
            }
            event.consume();
        };

        EventHandler<ActionEvent> buttonEraseHandler = event -> {

            Body body = new Body();
            body.addValueToBody("", String.valueOf(task.taskId));
            taskService.deleteTask(body);

            try {
                borderPaneController.setBorderPane();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            event.consume();
        };


        modifButton.setOnAction(buttonModifHandler);

        Button eraseButton = new Button("Supprimer");
        eraseButton.setOnAction(buttonEraseHandler);

        ButtonBar buttonBar = new ButtonBar();
        buttonBar.getButtons().addAll(modifButton, eraseButton);
        newGrid.add(buttonBar, 0, 2, 2, 1);
    }

    private void prepareScreenForTaskModification(ActionEvent event, AddTicketController popupController) {
        popupController.setTitleLabel("Modifier une tâche");
        popupController.setName(task.taskName);
        popupController.setDescription(task.taskDescription);
        popupController.setDevs(getDevUsers());
        popupController.setMembers(users);
        hideUnusedFilled(popupController);
    }

    private void hideUnusedFilled(AddTicketController popupController) {
        popupController.setClotureDateTextFieldInvisible();
        popupController.setClotureDateLabelInvisible();
        popupController.setStatusInvisible();
        popupController.setStatusLabelInvisible();
    }

    private void assignNewMember(Body body, AddTicketController popupController) {
        List<String> newMembers = popupController.getNewMembers();
        newMembers.forEach(newMember -> {
            body.clear();
            body.addValueToBody("taskId", String.valueOf(task.taskId));
            body.addValueToBody("userMail", newMember);
            try {
                taskService.assignUserToTask(body);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }

    private void unassignNewMember(Body body, AddTicketController popupController) {
        List<String> deletedMembers = popupController.getDeletedMembers();
        deletedMembers.forEach(deletedMember -> {
            body.clear();
            body.addValueToBody("taskId", String.valueOf(task.taskId));
            body.addValueToBody("userMail", deletedMember);
            taskService.unassignUserToTask(body);
        });
    }
    @SneakyThrows
    private User[] getDevUsers(){
        Body body = new Body();
        User[] members = borderPaneController.getUser().getDevelopers(body);
        return members;
    }

}
