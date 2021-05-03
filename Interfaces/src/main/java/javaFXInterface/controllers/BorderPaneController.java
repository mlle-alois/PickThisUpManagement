package javaFXInterface.controllers;

import Models.Board;
import Models.Status;
import Requete.Body;
import Requete.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.io.IOException;

public class BorderPaneController {
    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem deconnexion;
    @FXML
    private MenuItem goCli;
    @FXML
    private Menu boardMenu;
    @FXML
    private Menu ticketMenu;
    @FXML
    private BorderPane scenePane;
    Stage root;
    Stage stage;
    Scene scene;

    String currentBoard;

    private User user;

    public Board[] getBoards(User user) throws JsonProcessingException {
        Body body = new Body();

        return user.getBoards(body);
    }

    @SneakyThrows
    public void initialize(User user) {
        this.user = user;
        initializeBoards();
        initializeTickets();

    }

    private void initializeTickets() throws JsonProcessingException {
        ticketMenu.getItems().addAll(getBranchsTickets(parseTickets()));
    }

    private void initializeBoards() throws JsonProcessingException {
        boardMenu.getItems().addAll(getBranchs(parseBoards()));
    }

    private String[] parseBoards() throws JsonProcessingException {
        Board[] boards = getBoards(user);
        String[] allBoars = new String[boards.length + 1];
        for (int i = 0; i < boards.length; i++) {
            allBoars[i] = boards[i].boardName;
        }
        allBoars[boards.length] = "Ajouter un tableau";
        return allBoars;
    }

    private MenuItem[] getBranchs(String[] boards) {
        MenuItem[] menuItems = new MenuItem[boards.length];

        for (int i = 0; i < boards.length; i++) {
            menuItems[i] = new MenuItem(boards[i]);
        }
        return menuItems;
    }

    public void selectItem() {
      /*  TreeItem<String> item = (TreeItem<String>) treeBoard.getSelectionModel().getSelectedItem();
        if(item != null){
            System.out.println(item.getValue());
        }*/
    }


    // Ticket Parts

    private String[] parseTickets() throws JsonProcessingException {
        Status[] Tickets = getTickets(user);
        String[] allTickets = new String[Tickets.length + 1];

        allTickets[0] = "Tous les tickets";

        for (int i = 0; i < Tickets.length; i++) {
            allTickets[i + 1] = Tickets[i].statusLibelle;
        }
        return allTickets;
    }

    public Status[] getTickets(User user) throws JsonProcessingException {
        Body body = new Body();
        body.addValueToBody("limit", "3");
        return user.getStatus(body);
    }

    private MenuItem[] getBranchsTickets(String[] Tickets) {
        MenuItem[] tasksItems = new MenuItem[Tickets.length];

        for (int i = 0; i < Tickets.length; i++) {
            tasksItems[i] = new MenuItem(Tickets[i]);
        }

        return tasksItems;
    }

    // Déconnexion part
    public void disconnect(ActionEvent event) throws IOException {
        if(!doesUserWantToDisconnect())
            return;
        Body body = new Body();
       if(user.Logout(body))
        switchToConnexionScene(event,"/Connection.fxml");
    }

    public void switchToConnexionScene(ActionEvent event,String ScenePath) throws IOException {

       stage = (Stage) menuBar.getScene().getWindow();
        stage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource(ScenePath));
        root = loader.load();
        root.setTitle("PickThis");
        root.getIcons().add(new Image("/logo.PNG"));
        root.show();

    }

    private boolean doesUserWantToDisconnect(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Déconnexion");
        alert.setHeaderText("Vous êtes sur le point de vous déconnecter! ");
        alert.setContentText("Est-ce vous sur de vouloir vous déconnecter? ");

        return alert.showAndWait().get() == ButtonType.OK;
    }
}
