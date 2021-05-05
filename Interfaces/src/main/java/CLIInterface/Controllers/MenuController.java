package CLIInterface.Controllers;

import Models.Board;
import Models.Liste;
import Models.Status;
import Requete.Body;
import Requete.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.io.IOException;

import static Enum.InterfaceCode.BOARD;

public class MenuController {

    private User user;

    @SneakyThrows
    public void initialize(User user) {
        this.user = user;
    }

    public Board[] getBoards(User user) throws JsonProcessingException {
        Body body = new Body();

        return user.getBoards(body);
    }

    public String[] parseBoards() throws JsonProcessingException {
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

    public void disconnect(ActionEvent event) throws IOException {
        if (!doesUserWantToDisconnect())
            return;
        Body body = new Body();
        //if (user.logout(body))
            //switchToConnexionScene(event, "/Connection.fxml");
    }

    private boolean doesUserWantToDisconnect() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Déconnexion");
        alert.setHeaderText("Vous êtes sur le point de vous déconnecter! ");
        alert.setContentText("Est-ce vous sur de vouloir vous déconnecter? ");

        return alert.showAndWait().get() == ButtonType.OK;
    }
}
