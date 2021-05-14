package javaFXInterface.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddTaskController {
    @FXML
    private Label mainLabel;
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
    private boolean isValidate = false;
    private Stage newStage;


    @FXML
    private void addNewTask(ActionEvent actionEvent) {
        name = taskNameTextField.getText();
        description = descriptionTextArea.getText();
        this.isValidate = true;
        newStage = (Stage) validateButton.getScene().getWindow();
        newStage.close();
    }
    @FXML
    private void cancel(ActionEvent actionEvent) {
        this.isValidate = false;
        newStage = (Stage) cancelButton.getScene().getWindow();
        newStage.close();
    }

    public String getName() {
        return name;
    }
    public String getDescription() { return description; }
    public boolean isValidate(){return isValidate;}

    public void setName(String name){this.taskNameTextField.setText(name);}
    public void setDescription(String description){this.descriptionTextArea.setText(description);}
    public void setMainLabelText(String mainLabel){this.mainLabel.setText(mainLabel);}

}
