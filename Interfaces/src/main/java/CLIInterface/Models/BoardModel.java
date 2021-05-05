package CLIInterface.Models;

import CLIInterface.Controllers.BoardController;
import CLIInterface.Controllers.ConnectionController;
import Requete.User;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class BoardModel {

    public static Scanner clavier = new Scanner(System.in);

    public static void addBoardTreatment(Stage window, User user) throws IOException {
        System.out.println("Nom du nouveau tableau :");
        String name = clavier.next();

        BoardController boardController = new BoardController();
        boardController.createBoard(name, window, user);
    }
}
