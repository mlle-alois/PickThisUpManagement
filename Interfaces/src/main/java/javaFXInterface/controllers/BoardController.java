package javaFXInterface.controllers;

import Models.Board;
import Requete.Body;
import Requete.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import lombok.SneakyThrows;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class BoardController {
    @FXML
    private ListView<String> boardsView;

    String currentBoard;

    public Board[] getBoards(User user) throws JsonProcessingException {
        Body body = new Body();

        return user.getBoards(body);
    }
    @SneakyThrows
    public void initialize(User user) {
        // Display all boards
        boardsView.getItems().addAll(parseBoards(user));
        // Activate listener on the boardList
        boardsView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                currentBoard = boardsView.getSelectionModel().getSelectedItem();
            }
        });
    }
    private String[] parseBoards(User user) throws JsonProcessingException {
        Board[] boards = getBoards(user);
        String[] allBoars = new String[boards.length];
        for (int i = 0; i < boards.length;i++){
           allBoars[i] = boards[i].boardName;
        }

    return  allBoars;
    }
}
