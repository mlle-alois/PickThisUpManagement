package javaFXInterface.controllers;

import Models.Board;
import Requete.Body;
import Requete.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import lombok.SneakyThrows;

public class BoardUpgradedController {
    @FXML
    private TreeView treeBoard;

    String currentBoard;

    public Board[] getBoards(User user) throws JsonProcessingException {
        Body body = new Body();

        return user.getBoards(body);
    }
    @SneakyThrows
    public void initialize(User user) {

            TreeItem<String> rootBoard = new TreeItem<>("Tableaux");

           TreeItem<String>[] allChildBoards = getBranchs(parseBoards(user));

            rootBoard.getChildren().addAll(allChildBoards);

            treeBoard.setRoot(rootBoard);


    }
    private String[] parseBoards(User user) throws JsonProcessingException {
        Board[] boards = getBoards(user);
        String[] allBoars = new String[boards.length];
        for (int i = 0; i < boards.length;i++){
           allBoars[i] = boards[i].boardName;
        }

    return  allBoars;
    }

    private TreeItem<String>[] getBranchs(String[] boards){
        TreeItem<String>[] treeItems = new TreeItem[boards.length];

        for(int i = 0; i < boards.length;i++){
            treeItems[i] = new TreeItem<>(boards[i]);
        }

        return treeItems;
    }

    public void selectItem(){
        TreeItem<String> item = (TreeItem<String>) treeBoard.getSelectionModel().getSelectedItem();
        if(item != null){
            System.out.println(item.getValue());
        }
    }
}
