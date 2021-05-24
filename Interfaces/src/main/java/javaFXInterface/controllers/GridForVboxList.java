package javaFXInterface.controllers;

import Models.Liste;
import Models.Task;
import Models.User;
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
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GridForVboxList {
    private List<GridPane> gridPanes;
    private Task[] tasks;
    private BorderPaneController borderPaneController;
    private Liste liste;
    private TaskService taskService;

    public GridForVboxList(Task[] tasks, BorderPaneController borderPaneController, Liste liste){
        this.gridPanes = new ArrayList<>();
        this.tasks = tasks;
        this.borderPaneController = borderPaneController;
        this.liste = liste;
        this.taskService = new TaskService(borderPaneController.getUser());
    }

    public List<GridPane> getGridPanes() {
        for (Task task : tasks) {
            GridPaneForTask gridPaneForTask = new GridPaneForTask(task,borderPaneController,liste);
            gridPaneForTask.setGridShape();
            gridPaneForTask.addTaskToGrid();
            gridPanes.add(gridPaneForTask.getNewGrid());
        }
        return gridPanes;
    }
// How to get object from event
  /*  private String getClickedLabelName(ActionEvent event) {
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
*/


}
