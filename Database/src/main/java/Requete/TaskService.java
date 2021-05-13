package Requete;

import Models.Task;
import Models.UserModel;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.net.http.HttpResponse;

public class TaskService {

    private final DatabaseService databaseService;

    private static String getTask = "task";
    private static String getTasksFromList ="task/list";
    private static String addTask ="task/add";
    private static final String getMembersByTaskId = "task/getMembers/";

    public TaskService(User user) {
        this.databaseService = new DatabaseService(user);
    }

    public Task[] getTasksFromList (Body body) throws JsonProcessingException {
        HttpResponse<String> result = databaseService.GetRequest(body,getTasksFromList);
        if (result.statusCode() < 300) {
            return body.objectMapper.readValue(result.body(), Task[].class);
        }
        return new Task[0];
    }

    public void addTask(Body body) throws JsonProcessingException {
        databaseService.PostRequest(body, addTask);
    }

    public boolean deleteTask(Body body) {
        return databaseService.DeleteRequest(body,getTask);
    }

    public UserModel[] getMembersByTaskId(Body body, Integer taskId) throws JsonProcessingException {
        HttpResponse<String> result = databaseService.GetRequest(body, getMembersByTaskId + taskId);
        if (result.statusCode() < 300) {
            return body.objectMapper.readValue(result.body(), UserModel[].class);
        }
        return new UserModel[0];
    }
}
