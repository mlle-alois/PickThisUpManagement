package Requete;

import Models.Board;
import Models.Task;
import Models.UserModel;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.net.http.HttpResponse;

public class TaskService {

    private final DatabaseService databaseService;

    private static String getTask = "task/get";
    private static String deleteTask = "task/delete";
    private static String updateTask = "task/update";
    private static String getTasksFromList = "task/list";
    private static String addTask = "task/add";
    private static final String getMembersByTaskId = "task/getMembers/";
    private static final String assignUserToTask = "task/assign";
    private static final String unassignUserToTask = "task/unassign";

    public TaskService(User user) {
        this.databaseService = new DatabaseService(user);
    }

    public Task[] getTasksFromList(Body body) throws JsonProcessingException {
        HttpResponse<String> result = databaseService.GetRequest(body, getTasksFromList);
        if (result.statusCode() < 300) {
            return body.objectMapper.readValue(result.body(), Task[].class);
        }
        return new Task[0];
    }

    public Task addTask(Body body) throws JsonProcessingException {
        HttpResponse<String> response = databaseService.PostRequest(body, addTask);
        if (response.statusCode() < 300) {
            return body.objectMapper.readValue(response.body(), Task.class);
        }
        return new Task();
    }

    public Task updateTask(Body body) throws JsonProcessingException {
        HttpResponse<String> response = databaseService.PutRequest(body,updateTask);
        if(response.statusCode() < 300){
            return body.objectMapper.readValue(response.body(), Task.class);
        }
        return new Task();
    }

    public boolean deleteTask(Body body) {
        return databaseService.DeleteRequest(body, deleteTask);
    }

    public UserModel[] getMembersByTaskId(Body body, Integer taskId) throws JsonProcessingException {
        HttpResponse<String> result = databaseService.GetRequest(body, getMembersByTaskId + taskId);
        if (result.statusCode() < 300) {
            return body.objectMapper.readValue(result.body(), UserModel[].class);
        }
        return new UserModel[0];
    }

    public Task assignUserToTask(Body body) throws JsonProcessingException {
        HttpResponse<String> response = databaseService.PostRequest(body, assignUserToTask);
        if (response.statusCode() < 300) {
            return body.objectMapper.readValue(response.body(), Task.class);
        }
        return new Task();
    }

    public boolean unassignUserToTask(Body body) {
        return databaseService.DeleteRequest(body, unassignUserToTask);
    }
}
