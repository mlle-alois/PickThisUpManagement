package Requete;

import Models.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.Map;

public class User {
    public HttpClient clientUser;
    public String token;
    public DatabaseService databaseService;


    private static String Login = "auth/login";
    private static String Logout = "auth/logout";
    private static String getAllDevelopers = "user/getAllDevelopers";

    public User() {
        this.clientUser = Client.getInstance();
        this.databaseService = new DatabaseService(this);
    }

    public String getToken() {
        return this.token;
    }

    public boolean login(Body body) throws JsonProcessingException {
        HttpResponse<String> response = databaseService.PostRequest(body, Login);
        if (response.statusCode() < 300) {
            Map<String, Object> result = body.objectMapper.readValue(response.body(), new TypeReference<>() {
            });
            token = String.valueOf(result.get("token"));
            return true;
        }
        return false;
    }

    public boolean logout(Body body) {
        if (databaseService.DeleteRequest(body, Logout)) {
            token = "";
            return true;
        }
        ;
        return false;
    }

    public UserModel[] getDevelopers(Body body) throws JsonProcessingException {
        HttpResponse<String> result = databaseService.GetRequest(body, getAllDevelopers);
        if (result.statusCode() < 300) {
            return body.objectMapper.readValue(result.body(), UserModel[].class);
        }
        return new UserModel[0];
    }
}

