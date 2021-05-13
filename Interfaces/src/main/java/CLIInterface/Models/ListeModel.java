package CLIInterface.Models;

import Models.Board;
import Models.Liste;
import Models.Task;
import Requete.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class ListeModel {

    public static Scanner clavier = new Scanner(System.in);

    public static void printTaskListsAndActionMenu(Liste liste, Stage window, User user, Board board) throws IOException {
        TaskService taskService = new TaskService(user);

        Body body = new Body();
        body.addValueToBody("", String.valueOf(liste.listId));
        Task[] tasks = taskService.getTasksFromList(body);

        int value = -1;

        do {
            try {
                List<String> menu = new ArrayList<>();
                menu.add("Liste : " + liste.listName);
                int i = 1;
                for (Task task : tasks) {
                    menu.add(i + ". " + task.taskName);
                    i += 1;
                }
                menu.add(i + ". Modifier la liste");
                i += 1;
                menu.add(i + ". Supprimer la liste");
                i += 1;
                menu.add(i + ". Ajouter une tâche");
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
        switchTaskListsAndActionMenu(value, window, user, liste, tasks, board);
    }

    public static void switchTaskListsAndActionMenu(int value, Stage window, User user, Liste list, Task[] tasks, Board board) throws IOException {
        if (value == tasks.length + 4) {
            BoardModel.printBoardListsAndActionMenu(board, window, user);
        } else if (value == tasks.length + 1) {
            ListeModel.updateListTreatment(window, user, list, board);
        } else if (value == tasks.length + 2) {
            ListeModel.deleteListTreatment(window, user, list, board);
        } else if (value == tasks.length + 3) {
            TaskModel.addTaskTreatment(window, user, list, board);
        } else {
            Task task = tasks[value - 1];
            TaskModel.printTask(task, window, user, list, board);
        }
    }

    public static void addListTreatment(Stage window, User user, Board board) throws IOException {
        String name = "";
        do {
            System.out.println("Nom de la liste :");
            name = clavier.nextLine();

            if (name.equals("")) {
                System.out.println("Veuillez saisir un nom");
            }
        } while (name.equals(""));

        ListService listService = new ListService(user);

        Body body = new Body();
        body.addValueToBody("boardId", board.boardId + "");
        body.addValueToBody("name", name);
        listService.addListe(body);

        BoardModel.printBoardListsAndActionMenu(board, window, user);
    }

    public static void updateListTreatment(Stage window, User user, Liste list, Board board) throws IOException {

        System.out.println("Nom de la liste :");
        clavier.nextLine();
        String name = clavier.nextLine();

        if (!name.equals("")) {
            ListService listService = new ListService(user);

            Body body = new Body();
            body.addValueToBody("", list.listId + "");
            body.addValueToBody("name", name);
            listService.updateListe(body);
        }

        list.listName = name;

        ListeModel.printTaskListsAndActionMenu(list, window, user, board);
    }

    public static void deleteListTreatment(Stage window, User user, Liste list, Board board) throws IOException {
        String validation = "";
        do {
            System.out.println("Voulez-vous vraiment supprimer cette liste (o/n) :");
            clavier.nextLine();
            validation = clavier.nextLine();

            if (!validation.toLowerCase(Locale.ROOT).equals("o") && !validation.toLowerCase(Locale.ROOT).equals("n")) {
                System.out.println("Veuillez saisir une valeur valide (o/n)");
                validation = "";
            }
        } while (validation.equals(""));

        if (validation.toLowerCase(Locale.ROOT).equals("o")) {
            ListService listService = new ListService(user);

            Body body = new Body();
            body.addValueToBody("", list.listId + "");
            listService.deleteListe(body);

            BoardModel.printBoardListsAndActionMenu(board, window, user);
        } else {
            ListeModel.printTaskListsAndActionMenu(list, window, user, board);
        }
    }
}
