package javaFXInterface.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateBoardNameController {

    @FXML
    private Button validateButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField updatedNameTextField;

    private String text;

    private Stage newStage;


    @FXML
    private void updateBoardName(ActionEvent actionEvent) {
        text = updatedNameTextField.getText();

        newStage = (Stage) validateButton.getScene().getWindow();
        newStage.close();
    }
    @FXML
    private void cancel(ActionEvent actionEvent) {
        newStage = (Stage) cancelButton.getScene().getWindow();
        newStage.close();
    }

    public String getText() {
        return text;
    }
}
