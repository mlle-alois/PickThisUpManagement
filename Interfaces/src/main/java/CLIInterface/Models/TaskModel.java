package CLIInterface.Models;

import Models.*;
import Requete.TaskService;
import Requete.Body;
import Requete.User;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TaskModel {

    public static Scanner clavier = new Scanner(System.in);

    public static void printTask(Task task, Stage window, User user, Liste liste, Board board) throws IOException {
        TaskService taskService = new TaskService(user);

        UserModel[] members = taskService.getMembersByTaskId(new Body(), task.taskId);
        StringBuilder membersName = new StringBuilder();

        for (UserModel member : members) {
            membersName.append(member.name).append(" ").append(member.firstname).append(", ");
        }
        System.out.println("Nom : " + task.taskName + " - Description : " + task.taskDescription +
                " - Date de création : " + task.taskCreationDate + " - Deadline : " + task.taskDeadline +
                " - Liste : " + liste.listName + " - Créateur : " + task.creatorId +
                " - Membres : " + membersName);

        printTaskActionsMenu(window, user, task, liste, board);
    }

    public static void printTaskActionsMenu(Stage window, User user, Task task, Liste liste, Board board) throws IOException {
        int value = -1;

        do {
            try {
                List<String> menu = new ArrayList<>();
                menu.add("1. Modifier");
                menu.add("2. Supprimer");
                menu.add("3. Retour");
                for (String chaine : menu) {
                    System.out.println(chaine);
                }
                value = Integer.parseInt(clavier.next());
                if (value < 1 || value > 3) {
                    System.out.println("Veuillez saisir un nombre présent dans le menu");
                    value = -1;
                }
            } catch (Exception e) {
                System.out.println("Veuillez saisir un numérique");
            }
        } while (value == -1);
        switchTaskActionMenu(value, window, user, task, liste, board);
    }

    public static void switchTaskActionMenu(int value, Stage window, User user, Task task, Liste liste, Board board) throws IOException {
        switch (value) {
            case 1:
                //updateTaskTreatment(window, user, task, liste);
                break;
            case 2:
                //deleteTaskTreatment(window, user, task, liste);
                break;
            default:
                ListeModel.printTaskListsAndActionMenu(liste, window, user, board);
        }
    }

    public static void addTaskTreatment(Stage window, User user, Liste liste, Board board) throws IOException {
        String name = "";
        do {
            System.out.println("Nom de la tâche :");
            name = clavier.nextLine();

            if (name.equals("")) {
                System.out.println("Veuillez saisir un nom");
            }
        } while (name.equals(""));

        System.out.println("Description de la tâche :");
        String desc = clavier.nextLine();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        Date deadline = null;
        boolean isDate = true;
        do {
            try {
                System.out.println("Deadline de la tâche :");
                String saisie = clavier.nextLine();
                if (!saisie.equals("")) {
                    deadline = simpleDateFormat.parse(saisie);
                }
                isDate = true;
            } catch (ParseException e) {
                System.out.println("Veuillez saisir une date au format dd/MM/yyyy");
                isDate = false;
            }
        } while (!isDate);

        //TODO membres

        TaskService taskService = new TaskService(user);

        Body body = new Body();
        body.addValueToBody("name", name);
        body.addValueToBody("description", desc);
        if (deadline != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.FRANCE);
            body.addValueToBody("deadline", sdf.format(deadline));
        }
        body.addValueToBody("listId", liste.listId + "");
        taskService.addTask(body);

        ListeModel.printTaskListsAndActionMenu(liste, window, user, board);
    }

    /*public static void updateTaskTreatment(Stage window, User user, Task task) throws IOException {

        System.out.println("Nom du tableau :");
        clavier.nextLine();
        String name = clavier.nextLine();

        if (!name.equals("")) {
            TaskService taskService = new TaskService(user);

            Body body = new Body();
            body.addValueToBody("", task.taskId + "");
            body.addValueToBody("name", name);
            taskService.updateTask(body);
        }

        task.taskName = name;

        TaskModel.printTaskListsAndActionMenu(task, window, user);
    }

    public static void deleteTaskTreatment(Stage window, User user, Task task) throws IOException {

        String validation = "";
        do {
            System.out.println("Voulez-vous vraiment supprimer ce tableau (o/n) :");
            clavier.nextLine();
            validation = clavier.nextLine();

            if (!validation.toLowerCase(Locale.ROOT).equals("o") && !validation.toLowerCase(Locale.ROOT).equals("n")) {
                System.out.println("Veuillez saisir une valeur valide (o/n)");
                validation = "";
            }
        } while (validation.equals(""));

        if (validation.toLowerCase(Locale.ROOT).equals("o")) {
            TaskService taskService = new TaskService(user);

            Body body = new Body();
            body.addValueToBody("", task.taskId + "");
            taskService.deleteTask(body);

            TaskMenu taskMenu = new TaskMenu();
            taskMenu.printTaskMenu(window, user);
        } else {
            TaskModel.printTaskListsAndActionMenu(task, window, user);
        }
    }*/
}
