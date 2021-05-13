package CLIInterface.Menu;

import CLIInterface.Models.TaskModel;
import Models.Liste;
import Models.Task;
import Requete.TaskService;
import Requete.Body;
import Requete.User;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskMenu {

    private String[] tasksNames;

    public static Scanner clavier = new Scanner(System.in);

    /*public void printTaskMenu(Stage window, User user) throws IOException {
        int value = -1;
        TaskController taskController = new TaskController(user);

        this.tasksNames = taskController.parseTasks();
        do {
            try {
                List<String> menu = new ArrayList<>();
                for (int i = 0; i < tasksNames.length; i += 1) {
                    menu.add((i + 1) + ". " + tasksNames[i]);
                }
                menu.add((tasksNames.length + 1) + ". Retour");
                for (String chaine : menu) {
                    System.out.println(chaine);
                }
                value = Integer.parseInt(clavier.next());
                if (value < 1 || value > tasksNames.length + 1) {
                    System.out.println("Veuillez saisir un nombre présent dans le menu");
                    value = -1;
                }
            } catch (Exception e) {
                System.out.println("Veuillez saisir un numérique");
            }
        } while (value == -1);
        this.switchTaskMenu(value, window, user);
    }

    public void switchTaskMenu(int value, Stage window, User user, Liste list) throws IOException {
        TaskService taskService = new TaskService(user);
        Task[] tasks = taskService.getTasks(new Body());

        if (value == tasks.length + 2) {
            GeneralMenu.printGeneralMenu(window, user);
        } else if (value == tasks.length + 1) {
            TaskModel.addTaskTreatment(window, user, list);
        } else {
            Task task = tasks[value - 1];

            TaskModel.printTaskListsAndActionMenu(task, window, user);
        }
    }*/
}
