package javaFXInterface.controllers;

import Models.Liste;
import Models.Task;
import Requete.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VboxForList {
    private Liste liste;
    private UserService user;
    private VBox vbox;
    private Task[] tasks;
    private List<GridPane> gridPanes;
    private BorderPaneController borderPaneController;

    public VboxForList(Liste liste, UserService user, BorderPaneController borderPaneController){
        this.liste = liste;
        this.user = user;
        this.vbox = new VBox();
        this.gridPanes = new ArrayList<>();
        this.borderPaneController = borderPaneController;
    }


    public VBox getFilledVbox() throws JsonProcessingException {
        this.tasks = getTasksFromListe();
        setVboxShape(tasks.length * 200 + 100);
        addTitleToVbox();
        GridForVboxList gridForVboxList = new GridForVboxList(tasks,borderPaneController,liste);
        this.gridPanes = gridForVboxList.getGridPanes();
        addPanesToVbox();
        createAddListButton();
        return this.vbox;
    }

    private Task[] getTasksFromListe() throws JsonProcessingException {
        // Get all the tasks linked to the list
        TaskService tasksService = new TaskService(user);
        Body body = new Body();
        body.addValueToBody("", String.valueOf(liste.listId));
        return tasksService.getTasksFromList(body);
    }

    private void setVboxShape(int size) {
        vbox.setSpacing(20);
        vbox.setPrefSize(150, size);
        vbox.setStyle("-fx-background-color: #d3d4cb");
    }


    private void addTitleToVbox() {
        GridPane newGrid = getTitleListWithButtonEvents();

        vbox.getChildren().add(newGrid);
    }

    private GridPane getTitleListWithButtonEvents() {
        GridPane newGrid = new GridPane();
        // add task's description
        TextArea lblTextArea = getTitleWithEvent();

        newGrid.add(lblTextArea,0,0);
        // Initialisation requete
        ListService listService = new ListService(user);

        // Set Event when clicked from buttons
        EventHandler<ActionEvent> buttonModifHandler = event -> {
            System.out.println("Modifier");
            lblTextArea.setEditable(true);
            lblTextArea.setStyle("-fx-control-inner-background: white;");
            event.consume();
        };

        EventHandler<ActionEvent> buttonEraseHandler = event -> {
            try {
                Body body = new Body();
                body.addValueToBody("",String.valueOf(liste.listId));
               if( listService.deleteListe(body))
                borderPaneController.setBorderPane();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            event.consume();
        };

        Button modifButton = new Button("Modifier");
        modifButton.setOnAction(buttonModifHandler);

        Button eraseButton = new Button("Supprimer");
        eraseButton.setOnAction(buttonEraseHandler);

        VerticalButtonBar bar = new VerticalButtonBar();
        bar.addButton(modifButton);
        bar.addButton(eraseButton);

        // Set Column constraints
        ColumnConstraints column1 = new ColumnConstraints();
        ColumnConstraints column2 = new ColumnConstraints();
        column1.setPrefWidth(150);
        column2.setPrefWidth(150);

        newGrid.getColumnConstraints().add(column1);
        newGrid.getColumnConstraints().add(column2);
        newGrid.add(bar, 1, 0, 1, 1);
        return newGrid;
    }

    private TextArea getTitleWithEvent() {
        TextArea lblTextArea = new TextArea(liste.listName);
        lblTextArea.setWrapText(true);
        lblTextArea.setEditable(false);
        lblTextArea.setStyle("-fx-control-inner-background: lightgray;");
        lblTextArea.setPrefSize(50,50);

        // Event
        lblTextArea.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @SneakyThrows
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER)  {
                    String text = lblTextArea.getText().replaceAll("\\s+","");
                    // If new entry isn't different
                   if(text.equals(liste.listName)) {
                       setEditableFalseWithStyle(lblTextArea);
                       return;
                   }
                   // If new entry is different, update the database
                   ListService listService = new ListService(user);
                   Body body = new Body();
                   body.addValueToBody("",String.valueOf(liste.listId));
                   body.addValueToBody("name",text);
                   body.addValueToBody("boardId",String.valueOf(borderPaneController.currentBoard.boardId));
                   Liste updatedList = listService.updateListe(body);

                    liste.listName = updatedList.listName;
                    lblTextArea.setText(text);

                    setEditableFalseWithStyle(lblTextArea);

                }
            }
        });
        return lblTextArea;
    }

    private void setEditableFalseWithStyle(TextArea lblTextArea) {
        lblTextArea.setEditable(false);
        lblTextArea.setStyle("-fx-control-inner-background: lightgray;");
    }

    private void addPanesToVbox() {
        for (GridPane gridPane : gridPanes) {
            vbox.getChildren().add(gridPane);
        }
    }

    private void createAddListButton() {

        Button addTaskButton = new Button("Ajouter une tâche");
        EventHandler<ActionEvent> buttonAddListHandler = event -> {
            if(event.getSource() == addTaskButton) {
                Stage newStage;
                Parent root = null;
                Body body;
                newStage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddTaskToList.fxml"));

                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                AddTaskController popupController = loader.getController();
                newStage.setScene(new Scene(root));
                newStage.initModality(Modality.APPLICATION_MODAL);
                newStage.initOwner(addTaskButton.getScene().getWindow());
                newStage.setTitle("PickThisUp");
                newStage.getIcons().add(new Image("/logo.PNG"));
                newStage.showAndWait();
                // add the task to the database
                if(!popupController.isValidate())
                    return;
                body = new Body();
                body.addValueToBody("name",popupController.getName());
                body.addValueToBody("description",popupController.getDescription());
                body.addValueToBody("listId",String.valueOf(liste.listId));
                TaskService taskService = new TaskService(user);
                try {
                    taskService.addTask(body);
                    borderPaneController.setBorderPane();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                event.consume();
            }
        };

        addTaskButton.setOnAction(buttonAddListHandler);
        this.vbox.getChildren().add(addTaskButton);
    }



}
