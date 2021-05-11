package javaFXInterface.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddListController  {

    @FXML
    private Button validateAddListButton;
    @FXML
    private Button cancelAddListButton;
    @FXML
    private TextField addListTextField;

    private String text;

    private Stage newStage;


    @FXML
    private void addNewList(ActionEvent actionEvent) {
        text = addListTextField.getText();

        newStage = (Stage) validateAddListButton.getScene().getWindow();
        newStage.close();
    }
    @FXML
    private void cancel(ActionEvent actionEvent) {
        newStage = (Stage) validateAddListButton.getScene().getWindow();
        newStage.close();
    }

    public String getText() {
        return text;
    }
}
