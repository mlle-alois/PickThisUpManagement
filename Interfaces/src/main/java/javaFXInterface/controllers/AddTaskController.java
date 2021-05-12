package javaFXInterface.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddTaskController {
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private Button cancelButton;
    @FXML
    private Button validateButton;
    @FXML
    private TextField taskNameTextField;


    private String name;
    private String description;

    private Stage newStage;


    @FXML
    private void addNewTask(ActionEvent actionEvent) {
        name = taskNameTextField.getText();
        description = descriptionTextArea.getText();

        newStage = (Stage) validateButton.getScene().getWindow();
        newStage.close();
    }
    @FXML
    private void cancel(ActionEvent actionEvent) {
        newStage = (Stage) cancelButton.getScene().getWindow();
        newStage.close();
    }

    public String getName() {
        return name;
    }
    public String getDescription() { return description; }

}
