package Requete;

import Models.Board;
import Models.Liste;
import Models.Status;
import Models.Ticket;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class User {
    public HttpClient clientUser;
    public String token;

    private static String Authorization = "Authorization";
    private static String Login = "auth/login";
    private static String Bearer = "Bearer ";
    private static String ContentType = "Content-Type";
    private static String app_json = "application/json";
    private static String getBoards = "board";
    private static String getTickets = "ticket";
    private static String getTicket = "ticket/get";
    private static String getList = "list";
    private static String getListsFromBoard = "list/board";
    private static String getStatus ="ticket/status";
    private static String Logout ="auth/logout";

    public User() {
        this.clientUser = Client.getInstance();
    }

    public boolean Login(Body body) throws JsonProcessingException {
        HttpResponse<String> response = PostRequest(body,Login);
        if(response.statusCode() < 300){
            Map<String, Object> result = body.objectMapper.readValue(response.body(), new TypeReference<>(){
            });
            token = String.valueOf(result.get("token"));
            return true;
        }
        return false;
    }

    public boolean Logout(Body body) throws JsonProcessingException {
        if (DeleteRequest(body,Logout)){
            token = "";
            return true;
        };
        return false;
    }
    // Get methods : If there's a positive reponse, => return a table of it
                        // else return a empty table;

    public Status[] getStatus (Body body) throws JsonProcessingException {
        HttpResponse<String> result = GetRequest(body,getStatus);
        if (result.statusCode() < 300) {
            return body.objectMapper.readValue(result.body(), Status[].class);
        }
        return new Status[0];
    }

    public Liste[] getListes (Body body) throws JsonProcessingException {
        HttpResponse<String> result = GetRequest(body,getList);
        if (result.statusCode() < 300) {
            return body.objectMapper.readValue(result.body(), Liste[].class);
        }
        return new Liste[0];
    }

    public Ticket[] getTickets (Body body) throws JsonProcessingException {
        HttpResponse<String> result = GetRequest(body,getTickets);
        if (result.statusCode() < 300) {
            return body.objectMapper.readValue(result.body(), Ticket[].class);
        }
        return new Ticket[0];
    }

    public Board getBoard (Body body) throws JsonProcessingException {
        HttpResponse<String> result = GetRequest(body,getBoards);
        if (result.statusCode() < 300) {
            return body.objectMapper.readValue(result.body(), Board.class);
        }
        return new Board();
    }

    public Board[] getBoards (Body body) throws JsonProcessingException {
        HttpResponse<String> result = GetRequest(body,getBoards);
        if (result.statusCode() < 300){
            return body.objectMapper.readValue(result.body(), Board[].class);
        }
        return new Board[0];
    }
  /*  public Board[] getBoards (Body body) throws JsonProcessingException {

        return  body.objectMapper.readValue(GetRequest(body,getBoards).body(), Board[].class);
    }*/

   /* private Map<String, Object> PostRequest(Body body,String route) throws JsonProcessingException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/" + route))
                .setHeader(Authorization,Bearer + token)
                .header(ContentType, app_json)
                .POST(HttpRequest.BodyPublishers.ofString(body.getStringAsJSon()))
                .build();

        return getBodyMapResponse(body, request);

    }*/

    private HttpResponse<String> PostRequest(Body body,String route) throws JsonProcessingException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/" + route))
                .setHeader(Authorization,Bearer + token)
                .header(ContentType, app_json)
                .POST(HttpRequest.BodyPublishers.ofString(body.getStringAsJSon()))
                .build();

        return getBodyMapResponse(body, request);

    }

    private boolean DeleteRequest(Body body,String route) throws JsonProcessingException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/" + route))
                .setHeader(Authorization,Bearer + token)
                .header(ContentType, app_json)
                .DELETE()
                .build();

        HttpResponse<String> response = getGetMapResponse(body,request);

        return response.statusCode() == 204;

    }

    private HttpResponse<String> GetRequest(Body body,String route) {


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(body.getUrlWithParametersInMap(route)))
                .setHeader(Authorization,Bearer + token)
                .header(ContentType, app_json)
                .GET()
                .build();

        return getGetMapResponse(body, request);

    }

   /* private Map<String, Object> getBodyMapResponse(Body body, HttpRequest request) throws JsonProcessingException {
        HttpResponse<String> response = null;
        try {
            response = this.clientUser.send(request,
                    HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        //Map<String, Object> map =
        return  body.objectMapper.readValue(response.body(), new TypeReference<>() {
        });
    }*/
    private HttpResponse<String> getBodyMapResponse(Body body, HttpRequest request) throws JsonProcessingException {
        HttpResponse<String> response = null;
        try {
            response = this.clientUser.send(request,
                    HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        //Map<String, Object> map =
        return  response;

    }

    private HttpResponse<String> getGetMapResponse(Body body, HttpRequest request) {
        HttpResponse<String> response = null;
        try {
            response = this.clientUser.send(request,
                    HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return response;

    }


}

