package javaFXInterface.controllers;

import Models.Status;
import Models.User;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AddTicketController {
    @FXML
    private Label closureDateLabel;
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
    private User[] devs;
    private String clotureDate;
    private Stage newStage;
    private boolean isValidate = false;
    private List<User> membersBeforeUpdate;
    private List<String> newMembers;
    private List<String> deletedMembers = new ArrayList<>();


    @FXML
    private void addTicket(ActionEvent actionEvent) {
        name = nameTextField.getText();
        description = descriptionTextArea.getText();
        status = statusChoiceBox.getSelectionModel().getSelectedIndex();
        setNewMembers();
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

    /*   @FXML
       private void addMembersToVbox(){
           membersVbox.getChildren().add(new TextField());
       }*/
    @FXML
    private void addMembersToVbox() {
        membersVbox.getChildren().add(setDevChoices(devs));
    }

    public List<String> getNewMembers() {
        return newMembers;
    }

    public void setNewMembers() {

        ObservableList<Node> choicesBoxesNode = membersVbox.getChildren();
        ChoiceBox[] choiceBoxes = new ChoiceBox[choicesBoxesNode.size()];
        int[] choiceBoxesSelectedIndex = new int[choicesBoxesNode.size()];

        newMembers = new ArrayList<>();
        for (int i = 0; i < choicesBoxesNode.size(); i++) {
            choiceBoxes[i] = (ChoiceBox) choicesBoxesNode.get(i);
            choiceBoxesSelectedIndex[i] = choiceBoxes[i].getSelectionModel().getSelectedIndex();
        }

// adding a new ticket
        if(membersBeforeUpdate == null){
            for (int choice : choiceBoxesSelectedIndex) {
                if(devs[choice].mail != " ")
                newMembers.add(devs[choice].mail);
            }
            if(!newMembers.isEmpty()){
                newMembers.stream().distinct().collect(Collectors.toList());}
            return;
        }
// modifying ticket
        List<String> membersBeforeUpdateString = membersBeforeUpdate.stream().map(mem -> mem.mail).collect(Collectors.toList());
        List<String> actualMember = new ArrayList<>();


        for (int choice : choiceBoxesSelectedIndex) {
            actualMember.add(devs[choice].mail);

            if (!membersBeforeUpdateString.contains(devs[choice].mail)) {
                if(devs[choice].mail != " ")
                newMembers.add(devs[choice].mail);
            }
        }

        for (String member : membersBeforeUpdateString) {
            if (!actualMember.contains(member)) {
                deletedMembers.add(member);
            }
        }

        for (String member : deletedMembers) {
            if (newMembers.contains(member)) {
                newMembers.remove(member);
            }
        }


    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.nameTextField.setText(name);
    }

    public void setDescription(String description) {
        this.descriptionTextArea.setText(description);
    }

    public boolean isValidate() {
        return isValidate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatusChoiceBox(Status[] statuses, int currentStatus) {
        statusChoiceBox.setValue(statuses[currentStatus - 1].statusLibelle);
        for (int i = 0; i < 3; i++) {
            statusChoiceBox.getItems().add(statuses[i].statusLibelle);
        }
    }

    ;

 /*   public void setMembers(User[] members) {
        for(int i = 0; i < members.length;i++){
            TextField label = new TextField(members[i].mail);
            label.setEditable(false);
            membersVbox.getChildren().add(label);
        }

    }
*/

    public void setMembers(User[] members) {
        membersBeforeUpdate = Arrays.asList(members);
        for (int i = 0; i < members.length; i++) {
            ChoiceBox choiceBox = setDevChoices(devs);
            choiceBox.setValue(members[i].mail);
            membersVbox.getChildren().add(choiceBox);
        }
    }

    public void setDevs(User[] devs) {
        this.devs = Arrays.copyOf(devs,devs.length+1);
        this.devs[devs.length] = new User();
        this.devs[devs.length].mail = " ";
    }

    public ChoiceBox setDevChoices(User[] devs) {
        ChoiceBox devChoiceBox = new ChoiceBox();
        devChoiceBox.getItems().addAll(Arrays.stream(devs).map(dev -> dev.mail).collect(Collectors.toList()));
        devChoiceBox.setValue(devs[0].mail);
        return devChoiceBox;
    }

    public String getClotureDate() {
        return clotureDate;
    }

    public void setTitleLabel(String title) {
        titleLabel.setText(title);
    }

    public void setClotureDate(String clotureDate) {
        this.clotureDateTextField.setText(clotureDate);
    }

    public List<String> getDeletedMembers() {
        return deletedMembers;
    }
    public void setStatusInvisible(){
        statusChoiceBox.setVisible(false);
    }
    public void setStatusLabelInvisible(){
        statusLabel.setVisible(false);
    }
    public void setClotureDateTextFieldInvisible(){
        clotureDateTextField.setVisible(false);
    }
    public void setClotureDateLabelInvisible(){
        closureDateLabel.setVisible(false);
    }
}
