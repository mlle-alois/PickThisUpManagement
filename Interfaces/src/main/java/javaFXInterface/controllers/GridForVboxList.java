package javaFXInterface.controllers;

import Models.Liste;
import Models.Task;
import Services.Body;
import Services.TaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GridForVboxList {
    private List<GridPane> gridPanes;
    private Task[] tasks;
    private BorderPaneController borderPaneController;
    private Liste liste;

    public GridForVboxList(Task[] tasks, BorderPaneController borderPaneController, Liste liste){
        this.gridPanes = new ArrayList<>();
        this.tasks = tasks;
        this.borderPaneController = borderPaneController;
        this.liste = liste;
    }

    public List<GridPane> getGridPanes() {
        for (Task task : tasks) {
            GridPane newGrid = new GridPane();
            setGridShape(newGrid);

            addTaskToGrid(task, newGrid);

            gridPanes.add(newGrid);
        }
        return gridPanes;
    }

    private void setGridShape(GridPane newGrid) {
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

    private void addTaskToGrid(Task task, GridPane newGrid) {
        // add title of task
        Label lblTaskName = new Label(task.taskName);
        newGrid.add(lblTaskName, 0, 0);
        // add task's description
        TextArea lblTextArea = new TextArea(task.taskDescription);
        lblTextArea.setWrapText(true);
        lblTextArea.setEditable(false);
        lblTextArea.setStyle("-fx-control-inner-background: lightgray;");
        newGrid.add(lblTextArea, 0, 1);

        addButtonBarToGrid(newGrid);
    }
    private void addButtonBarToGrid(GridPane newGrid) {
        // Set Event when clicked from buttons
        TaskService taskService = new TaskService(borderPaneController.getUser());
        Button modifButton = new Button("Modifier");
        EventHandler<ActionEvent> buttonModifHandler = event -> {
            if(event.getSource() == modifButton) {
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
                prepareScreenForTaskModification(event, popupController);
                newStage.setScene(new Scene(root));
                newStage.initModality(Modality.APPLICATION_MODAL);
                newStage.initOwner(modifButton.getScene().getWindow());
                newStage.setTitle("PickThisUp");
                newStage.getIcons().add(new Image("/logo.PNG"));
                newStage.showAndWait();
                if(!popupController.isValidate())
                    return;
                // add the task to the database
                body = new Body();
                addLabelIdToBody(body,getClickedLabelName(event));
                body.addValueToBody("name",popupController.getName());
                body.addValueToBody("description",popupController.getDescription());
                body.addValueToBody("listId",String.valueOf(liste.listId));

                try {
                    taskService.updateTask(body);
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
                String labelName = getClickedLabelName(event);
                addLabelIdToBody(body, labelName);
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

    private void prepareScreenForTaskModification(ActionEvent event, AddTaskController popupController) {
        popupController.setName(getClickedLabelName(event));
        popupController.setDescription(getClickedDescriptionName(event));
        popupController.setMainLabelText("Modifier une tâche");
    }

    private void addLabelIdToBody(Body body, String labelName) {
        for (Task task:tasks) {
            if(task.taskName.equals(labelName)){
                body.addValueToBody("",String.valueOf(task.taskId));
                break;
            }
        }
    }

    private String getClickedLabelName(ActionEvent event) {
        ObservableList<Node> parents = getParents(event);
        Label label = (Label) parents.get(0);
        String labelName = label.getText();
        return labelName;
    }

    private String getClickedDescriptionName(ActionEvent event) {
        ObservableList<Node> parents = getParents(event);
        TextArea textArea = (TextArea) parents.get(1);
        String description = textArea.getText();
        return description;
    }

    private ObservableList<Node> getParents(ActionEvent event) {
        Button buttonTemp = (Button) event.getSource();
        GridPane parent =(GridPane) buttonTemp.getParent().getParent().getParent();
        var childrenArray =  parent.getChildrenUnmodifiable();
        return childrenArray;
    }

}
