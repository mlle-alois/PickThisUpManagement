package javaFXInterface.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddTicketController {
    @FXML
    private ChoiceBox statusChoiceBox;
    @FXML
    private Button addMemberButton;
    @FXML
    private Label statusLabel;
    @FXML
    private TextField clotureDateTextField;
    @FXML
    private VBox membersVbox;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private Button cancelButton;
    @FXML
    private Button validateButton;
    @FXML
    private TextField nameTextField;


    private String name;
    private String description;
    private String status;
    private Label[] members;
    private String clotureDate;
    private Stage newStage;


    @FXML
    private void addTicket(ActionEvent actionEvent) {
        setName(nameTextField.getText());
        setDescription(descriptionTextArea.getText());

        newStage = (Stage) validateButton.getScene().getWindow();
        newStage.close();
    }
    @FXML
    private void cancel(ActionEvent actionEvent) {
        newStage = (Stage) cancelButton.getScene().getWindow();
        newStage.close();
    }

    @FXML
    private void addMembersToVbox(){
        membersVbox.getChildren().add(new TextField());
    }
    public String getName() {
        return name;
    }
    public String getDescription() { return description; }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Label[] getMembers() {
        return members;
    }

    public void setMembers(Label[] members) {
        this.members = members;
    }

    public String getClotureDate() {
        return clotureDate;
    }

    public void setClotureDate(String clotureDate) {
        this.clotureDate = clotureDate;
    }
}
