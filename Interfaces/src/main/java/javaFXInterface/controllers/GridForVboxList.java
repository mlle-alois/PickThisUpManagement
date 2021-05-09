package javaFXInterface.controllers;

import Models.Task;
import Requete.Body;
import Requete.TaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;
import java.util.List;

public class GridForVboxList {
    private List<GridPane> gridPanes;
    private Task[] tasks;
    private BorderPaneController borderPaneController;

    public GridForVboxList(Task[] tasks,BorderPaneController borderPaneController){
        this.gridPanes = new ArrayList<>();
        this.tasks = tasks;
        this.borderPaneController = borderPaneController;
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

        EventHandler<ActionEvent> buttonModifHandler = event -> {
            System.out.println("Modifier");
            event.consume();
        };

        EventHandler<ActionEvent> buttonEraseHandler = event -> {
            try {
                Body body = new Body();

                String labelName = getClickedLabelName(event);

                addLabelNameToBody(body, labelName);
                if(taskService.deleteTask(body))
                    borderPaneController.setBorderPane();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            event.consume();
        };

        Button modifButton = new Button("Modifer");
        modifButton.setOnAction(buttonModifHandler);

        Button eraseButton = new Button("Supprimer");
        eraseButton.setOnAction(buttonEraseHandler);

        ButtonBar buttonBar = new ButtonBar();
        buttonBar.getButtons().addAll(modifButton, eraseButton);
        newGrid.add(buttonBar, 0, 2, 2, 1);
    }

    private void addLabelNameToBody(Body body, String labelName) {
        for (Task task:tasks) {
            if(task.taskName.equals(labelName)){
                body.addValueToBody("",String.valueOf(task.taskId));
                break;
            }
        }
    }

    private String getClickedLabelName(ActionEvent event) {
        Button buttonTemp = (Button) event.getSource();
        GridPane parent =(GridPane) buttonTemp.getParent().getParent().getParent();
        var childrenArray =  parent.getChildrenUnmodifiable();
        Label label = (Label) childrenArray.get(0);
        String labelName = label.getText();
        return labelName;
    }

}
