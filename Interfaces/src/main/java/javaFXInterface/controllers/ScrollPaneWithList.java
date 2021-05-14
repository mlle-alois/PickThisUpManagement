package javaFXInterface.controllers;

import Models.Liste;
import Requete.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

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
