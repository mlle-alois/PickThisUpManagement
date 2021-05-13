package javaFXInterface.controllers;

import Models.Liste;
import Requete.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ScrollPaneWithList {
    private Liste[] listes;
    private ScrollPane scrollPane;
    private GridPane mainPane;
    private User user;
    private BorderPaneController borderPaneController;

    public ScrollPaneWithList(Liste[] listes, User user,BorderPaneController borderPaneController){
        this.listes = listes;
        this.scrollPane = new ScrollPane();
        this.mainPane = new GridPane();
        this.user = user;
        this.borderPaneController = borderPaneController;
    }

    public ScrollPane getFullScrollPane() throws JsonProcessingException {
        setMainGridPaneShape();
        create1VboxPerListe();

        this.scrollPane.setContent(mainPane);
        return this.scrollPane;
    }

    private void createAddListButton() {

        Button addTaskButton = new Button("Ajouter une tâche");
        EventHandler<ActionEvent> buttonAddListHandler = event -> {
            System.out.println("Modifier");

            if(event.getSource() == addTaskButton) {
                Stage newStage;
                Parent root = null;
                Body body;
                newStage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddListToBoard.fxml"));

                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                AddListController popupController = loader.getController();
                newStage.setScene(new Scene(root));
                newStage.initModality(Modality.APPLICATION_MODAL);
                newStage.initOwner(addTaskButton.getScene().getWindow());
                newStage.setTitle("PickThisUp");
                newStage.getIcons().add(new Image("/logo.PNG"));
                newStage.showAndWait();
                // lblTextArea.setEditable(true);
                // lblTextArea.setStyle("-fx-control-inner-background: white;");
                event.consume();
            }
        };

            addTaskButton.setOnAction(buttonAddListHandler);
            mainPane.add(addTaskButton,1,1);
    }


    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    private void setMainGridPaneShape() {
        mainPane.setPadding(new Insets(10, 10, 10, 10));
        mainPane.setHgap(50);
        mainPane.setVgap(50);
    }

    private void create1VboxPerListe() throws JsonProcessingException {
        for (int i = 0; i < listes.length; i++) {
            VboxForList vboxForList = new VboxForList(listes[i],this.user,this.borderPaneController);
            VBox vbox = vboxForList.getFilledVbox();

            mainPane.add(vbox, i, 0);
        }

    }



}
