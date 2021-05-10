package Requete;

import Models.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class User {
    public HttpClient clientUser;
    public String token;
    public DatabaseService databaseService;

    private static String Authorization = "Authorization";
    private static String Login = "auth/login";
    private static String Bearer = "Bearer ";
    private static String ContentType = "Content-Type";
    private static String app_json = "application/json";
    private static String getBoards = "board";
    private static String getList = "list";
    private static String Logout ="auth/logout";
    private static String getTasksFromList ="task/list";

    public User() {
        this.clientUser = Client.getInstance();
        this.databaseService = new DatabaseService(this);
    }

    public String getToken() {
        return this.token;
    }

    public boolean login(Body body) throws JsonProcessingException {
        HttpResponse<String> response = databaseService.PostRequest(body,Login);
        if(response.statusCode() < 300){
            Map<String, Object> result = body.objectMapper.readValue(response.body(), new TypeReference<>(){
            });
            token = String.valueOf(result.get("token"));
            return true;
        }
        return false;
    }

    public boolean logout(Body body) throws JsonProcessingException {
        if (databaseService.DeleteRequest(body,Logout)){
            token = "";
            return true;
        };
        return false;
    }
    // Get methods : If there's a positive reponse, => return a table of it
                        // else return a empty table;

    public Task[] getTasksFromList (Body body) throws JsonProcessingException {
        HttpResponse<String> result = databaseService.GetRequest(body,getTasksFromList);
        if (result.statusCode() < 300) {
            return body.objectMapper.readValue(result.body(), Task[].class);
        }
        return new Task[0];
    }

    public Board getBoard (Body body) throws JsonProcessingException {
        HttpResponse<String> result = databaseService.GetRequest(body,getBoards);
        if (result.statusCode() < 300) {
            return body.objectMapper.readValue(result.body(), Board.class);
        }
        return new Board();
    }

    public Board[] getBoards (Body body) throws JsonProcessingException {
        HttpResponse<String> result = databaseService.GetRequest(body,getBoards);
        if (result.statusCode() < 300){
            return body.objectMapper.readValue(result.body(), Board[].class);
        }
        return new Board[0];
    }
}

