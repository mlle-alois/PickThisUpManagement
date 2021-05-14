package javaFXInterface.controllers;

import Models.StatusModel;
import Models.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddTicketController {
    @FXML
    private Label titleLabel;
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
    private int status;
    private Label[] members;
    private String clotureDate;
    private Stage newStage;
    private boolean isValidate = false;


    @FXML
    private void addTicket(ActionEvent actionEvent) {
        name = nameTextField.getText();
        description = descriptionTextArea.getText();
        status =  statusChoiceBox.getSelectionModel().getSelectedIndex() ;

        isValidate = true;
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

    public void setName(String name) { this.nameTextField.setText(name) ; }

    public void setDescription(String description) { this.descriptionTextArea.setText(description); }

    public boolean isValidate(){return isValidate;}
    public int getStatus() {
        return status;
    }
    public void setStatusChoiceBox(StatusModel[] statusModels,int currentStatus){
        statusChoiceBox.setValue(statusModels[currentStatus-1].statusLibelle);
        for (int i = 0; i < 3;i++){
            statusChoiceBox.getItems().add(statusModels[i].statusLibelle);
        }
    };

    public Label[] getMembers() {
        return members;
    }

    public void setMembers(UserModel[] members) {
        for(int i = 0; i < members.length;i++){
            TextField label = new TextField(members[i].firstname);
            membersVbox.getChildren().add(label);
        }

    }

    public String getClotureDate() {
        return clotureDate;
    }
    public void setTitleLabel(String title){titleLabel.setText(title);}
    public void setClotureDate(String clotureDate) {
        this.clotureDateTextField.setText(clotureDate);
    }
}
