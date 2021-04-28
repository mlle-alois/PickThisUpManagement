package Requete;

import Models.Board;
import Models.Ticket;
import Requete.Body;
import Requete.Client;
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

    private static String Authorization = "Authorization";
    private static String Login = "auth/login";
    private static String Bearer = "Bearer ";
    private static String ContentType = "Content-Type";
    private static String app_json = "application/json";
    private static String getBoards = "board";
    private static String getTickets = "ticket";
    private static String getTicket = "ticket/get";

    public User() {
        this.clientUser = Client.getInstance();
    }

    public void Login(Body body) throws JsonProcessingException {
        Map<String, Object> map = PostRequest(body,Login);
        token = String.valueOf(map.get("token"));
    }

    public Ticket getTicket (Body body) throws JsonProcessingException {
        return  body.objectMapper.readValue(GetRequest(body,getTicket).body(), Ticket.class);
    }

    public Ticket[] getTickets (Body body) throws JsonProcessingException {
        return  body.objectMapper.readValue(GetRequest(body,getTickets).body(), Ticket[].class);
    }


    public Board getBoard (Body body) throws JsonProcessingException {
        return  body.objectMapper.readValue(GetRequest(body,getBoards).body(), Board.class);
    }

    public Board[] getBoards (Body body) throws JsonProcessingException {
        return  body.objectMapper.readValue(GetRequest(body,getBoards).body(), Board[].class);
    }

    private Map<String, Object> PostRequest(Body body,String route) throws JsonProcessingException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/" + route))
                .setHeader(Authorization,Bearer + token)
                .header(ContentType, app_json)
                .POST(HttpRequest.BodyPublishers.ofString(body.getStringAsJSon()))
                .build();

        return getBodyMapResponse(body, request);

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

    private Map<String, Object> getBodyMapResponse(Body body, HttpRequest request) throws JsonProcessingException {
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

