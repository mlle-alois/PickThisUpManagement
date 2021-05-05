package CLIInterface.Controllers;

import CLIInterface.Menu.GeneralMenu;
import Models.Board;
import Requete.Body;
import Requete.TicketsService;
import Requete.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.stage.Stage;

import java.io.IOException;

public class BoardController {

    private static String errorConnection = "La connexion a échoué";

    private final User user;

    public BoardController(User user) {
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

    public void createBoard(String name, Stage window, User user) throws IOException {

        var loginBody = new Body();
        loginBody.addValueToBody("name", name);

        if (user.login(loginBody)) {
            GeneralMenu.printGeneralMenu(window, user);
        } else {
            System.out.println(errorConnection);
        }
    }
}
