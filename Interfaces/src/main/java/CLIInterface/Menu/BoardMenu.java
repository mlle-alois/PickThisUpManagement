package CLIInterface.Menu;

import CLIInterface.Controllers.BoardController;
import CLIInterface.Models.BoardModel;
import Models.Board;
import Requete.User;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BoardMenu {

    private String[] boardsNames;

    public static Scanner clavier = new Scanner(System.in);

    public void printBoardMenu(Stage window, User user) throws IOException {
        int value = -1;
        BoardController boardController = new BoardController(user);

        this.boardsNames = boardController.parseBoards();
        do {
            try {
                List<String> menu = new ArrayList<>();
                for (int i = 0; i < boardsNames.length; i += 1) {
                    menu.add((i + 1) + ". " + boardsNames[i]);
                }
                menu.add((boardsNames.length + 1) + ". Retour");
                for (String chaine : menu) {
                    System.out.println(chaine);
                }
                value = Integer.parseInt(clavier.next());
                if (value < 1 || value > boardsNames.length + 1) {
                    System.out.println("Veuillez saisir un nombre présent dans le menu");
                    value = -1;
                }
            } catch (Exception e) {
                System.out.println("Veuillez saisir un numérique");
            }
        } while (value == -1);
        this.switchBoardMenu(value, window, user);
    }

    public void switchBoardMenu(int value, Stage window, User user) throws IOException {
        BoardController boardController = new BoardController(user);
        Board[] boards = boardController.getBoards();

        if (value == boards.length + 2) {
            GeneralMenu.printGeneralMenu(window, user);
        } else if (value == boards.length + 1) {
            BoardModel.addBoardTreatment(window, user);
        } else {
            Board board = boards[value - 1];

            BoardModel.printBoardListsAndActionMenu(board, window, user);
        }
    }
}
