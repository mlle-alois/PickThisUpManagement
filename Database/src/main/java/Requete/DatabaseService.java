package Requete;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DatabaseService {

    private final HttpClient httpClient;
    private final User user;

    private static final String Authorization = "Authorization";
    private static final String Bearer = "Bearer ";
    private static final String ContentType = "Content-Type";
    private static final String app_json = "application/json";

    public DatabaseService(User user) {
        this.httpClient = Client.getInstance();
        this.user = user;
    }

    public HttpResponse<String> PostRequest(Body body, String route) throws JsonProcessingException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/" + route))
                .setHeader(Authorization, Bearer + user.getToken())
                .header(ContentType, app_json)
                .POST(HttpRequest.BodyPublishers.ofString(body.getStringAsJSon()))
                .build();

        return getBodyMapResponse(body, request);
    }

    public HttpResponse<String> PutRequest(Body body, String route) throws JsonProcessingException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(body.getPutUrl(route)))
                .setHeader(Authorization, Bearer + user.getToken())
                .header(ContentType, app_json)
                .PUT(HttpRequest.BodyPublishers.ofString(body.getStringAsJSon()))
                .build();

        return getBodyMapResponse(body, request);
    }

    public boolean DeleteRequest(Body body, String route)  {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(body.getUrlWithParametersInMap(route)))
                .setHeader(Authorization, Bearer + user.getToken())
                .header(ContentType, app_json)
                .DELETE()
                .build();

        HttpResponse<String> response = getGetMapResponse(body, request);

        return response.statusCode() == 204;
    }

    public HttpResponse<String> GetRequest(Body body, String route)  {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(body.getUrlWithParametersInMap(route)))
                .setHeader(Authorization, Bearer + user.getToken())
                .header(ContentType, app_json)
                .GET()
                .build();

        return getGetMapResponse(body, request);
    }


    public HttpResponse<String> getBodyMapResponse(Body body, HttpRequest request) throws JsonProcessingException {

        HttpResponse<String> response = null;
        try {
            response = this.httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return response;

    }

    public HttpResponse<String> getGetMapResponse(Body body, HttpRequest request) {
        HttpResponse<String> response = null;
        try {
            response = this.httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return response;

    }
}
