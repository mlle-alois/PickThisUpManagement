package CLIInterface.Controllers;

import Models.Task;
import Models.User;
import Services.Body;
import Services.TaskService;
import Services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TaskController {

    private final UserService user;
    private final TaskService taskService;

    public TaskController(UserService user) {
        this.user = user;
        this.taskService = new TaskService(user);
    }

    /**
     * récupération des tâches d'une liste
     *
     * @param listId
     * @return
     * @throws JsonProcessingException
     */
    public Task[] getTasksFromList(Integer listId) throws JsonProcessingException {
        Body body = new Body();
        body.addValueToBody("", String.valueOf(listId));
        return taskService.getTasksFromList(body);
    }

    /**
     * récupération des membres d'une tâche
     *
     * @param taskId
     * @return
     * @throws JsonProcessingException
     */
    public User[] getMembersByTaskId(Integer taskId) throws JsonProcessingException {
        return taskService.getMembersByTaskId(new Body(), taskId);
    }

    /**
     * ajout d'une tâche
     * @param name
     * @param desc
     * @param deadline
     * @param listId
     * @return
     * @throws JsonProcessingException
     */
    public Task addTask(String name, String desc, Date deadline, Integer listId) throws JsonProcessingException {
        Body body = new Body();
        body.addValueToBody("name", name);
        body.addValueToBody("description", desc);
        if (deadline != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.FRANCE);
            body.addValueToBody("deadline", sdf.format(deadline));
        }
        body.addValueToBody("listId", listId + "");
        return taskService.addTask(body);
    }

    /**
     * assigner un développeur à une tache
     * @param taskId
     * @param mail
     * @return
     * @throws JsonProcessingException
     */
    public Task assignUserToTask(Integer taskId, String mail) throws JsonProcessingException {
        Body assignBody = new Body();
        assignBody.addValueToBody("taskId", String.valueOf(taskId));
        assignBody.addValueToBody("userMail", mail);
        return taskService.assignUserToTask(assignBody);
    }

    /**
     * désassigner un développeur d'une tâche
     * @param taskId
     * @param mail
     * @return
     */
    public boolean unassignUserToTask(Integer taskId, String mail) {
        Body unassignBody = new Body();
        unassignBody.addValueToBody("taskId", String.valueOf(taskId));
        unassignBody.addValueToBody("userMail", mail);
        return taskService.unassignUserToTask(unassignBody);
    }

    /**
     * modifier une tâche
     * @param taskId
     * @param name
     * @param desc
     * @param deadline
     * @return
     * @throws JsonProcessingException
     */
    public Task updateTask(Integer taskId, String name, String desc, Date deadline) throws JsonProcessingException {
        Body body = new Body();
        body.addValueToBody("", String.valueOf(taskId));
        if (!name.equals(""))
            body.addValueToBody("name", name);
        if (!desc.equals(""))
            body.addValueToBody("description", desc);
        if (deadline != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.FRANCE);
            body.addValueToBody("deadline", sdf.format(deadline));
        }
        return taskService.updateTask(body);
    }

    /**
     * supprimer une tâche
     * @param taskId
     * @return
     */
    public boolean deleteTask(Integer taskId) {
        Body body = new Body();
        body.addValueToBody("", String.valueOf(taskId));
        return taskService.deleteTask(body);
    }
}
