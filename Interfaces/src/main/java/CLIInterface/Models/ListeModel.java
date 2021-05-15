package CLIInterface.Models;

import CLIInterface.Controllers.ListController;
import CLIInterface.Controllers.TaskController;
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

    /**
     * affichage des tâches et des différentes actions possibles
     * @param liste
     * @param window
     * @param user
     * @param board
     * @throws IOException
     */
    public static void printTaskListsAndActionMenu(Liste liste, Stage window, User user, Board board) throws IOException {
        TaskController taskController = new TaskController(user);
        Task[] tasks = taskController.getTasksFromList(liste.listId);

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

    /**
     * dirige vers la bonne action selon la valeur choisie
     * @param value
     * @param window
     * @param user
     * @param list
     * @param tasks
     * @param board
     * @throws IOException
     */
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

    /**
     * affiche le traitement d'un ajout de liste
     * @param window
     * @param user
     * @param board
     * @throws IOException
     */
    public static void addListTreatment(Stage window, User user, Board board) throws IOException {
        String name = "";
        do {
            System.out.println("Nom de la liste :");
            name = clavier.nextLine();

            if (name.equals("")) {
                System.out.println("Veuillez saisir un nom");
            }
        } while (name.equals(""));

        ListController listController = new ListController(user);
        listController.addListe(board.boardId, name);

        BoardModel.printBoardListsAndActionMenu(board, window, user);
    }

    /**
     * affiche le traitement d'une mise à jour de liste
     * @param window
     * @param user
     * @param list
     * @param board
     * @throws IOException
     */
    public static void updateListTreatment(Stage window, User user, Liste list, Board board) throws IOException {

        System.out.println("Nom de la liste :");
        clavier.nextLine();
        String name = clavier.nextLine();

        if (!name.equals("")) {
            ListController listController = new ListController(user);
            listController.updateListe(list.listId, name);
        }

        list.listName = name;

        ListeModel.printTaskListsAndActionMenu(list, window, user, board);
    }

    /**
     * affiche le traitement d'une délétion de liste
     * @param window
     * @param user
     * @param list
     * @param board
     * @throws IOException
     */
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
            ListController listController = new ListController(user);
            listController.deleteListe(list.listId);

            BoardModel.printBoardListsAndActionMenu(board, window, user);
        } else {
            ListeModel.printTaskListsAndActionMenu(list, window, user, board);
        }
    }
}
