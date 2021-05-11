package CLIInterface.Models;

import CLIInterface.Menu.BoardMenu;
import Models.Liste;
import Requete.BoardService;
import Requete.Body;
import Requete.User;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class ListeModel {

    public static Scanner clavier = new Scanner(System.in);

    public static void printTaskListsAndActionMenu(Liste liste, Stage window, User user) throws IOException {
        /*BoardController boardController = new BoardController(user);
        BoardService boardService = new BoardService(user);
        ListService listService = new ListService(user);

        Body body = new Body();
        body.addValueToBody("", String.valueOf(board.boardId));
        Liste[] lists = listService.getListesFromBoard(body);

        int value = -1;

        do {
            try {
                List<String> menu = new ArrayList<>();
                int i = 1;
                for (Liste list : lists) {
                    menu.add(i + ". " + list.listName);
                    i += 1;
                }
                menu.add(i + ". Modifier le tableau");
                i += 1;
                menu.add(i + ". Supprimer le tableau");
                i += 1;
                menu.add(i + ". Retour");

                for (String chaine : menu) {
                    System.out.println(chaine);
                }
                value = Integer.parseInt(clavier.next());
                if (value < 1 || value > i) {
                    System.out.println("Veuillez saisir un nombre présent dans le menu");
                    value = -1;
                }
            } catch (Exception e) {
                System.out.println("Veuillez saisir un numérique");
            }
        } while (value == -1);
        switchBoardListsAndActionMenu(value, window, user, board, lists);*/
    }

    /*public static void switchBoardListsAndActionMenu(int value, Stage window, User user, Board board, Liste[] lists) throws IOException {
        BoardService boardService = new BoardService(user);

        if (value == lists.length + 2) {
            BoardMenu boardMenu = new BoardMenu();
            boardMenu.printBoardMenu(window, user);
        } else if (value == lists.length) {
            ListeModel.updateBoardTreatment(window, user, board);
        } else if (value == lists.length + 1) {
            ListeModel.deleteBoardTreatment(window, user, board);
        } else {
            Liste list = lists[value - 1];

            ListeModel.printTaskListsAndActionMenu(list, window, user);
        }
    }*/

    public static void addBoardTreatment(Stage window, User user) throws IOException {
        String name = "";
        String desc = "";
        do {
            System.out.println("Nom du nouveau tableau :");
            name = clavier.nextLine();

            if (name.equals("")) {
                System.out.println("Veuillez saisir un nom");
            }
        } while (name.equals(""));

        BoardService boardService = new BoardService(user);

        Body body = new Body();
        body.addValueToBody("name", name);
        boardService.addBoard(body);

        BoardMenu boardMenu = new BoardMenu();
        boardMenu.printBoardMenu(window, user);
    }
}
