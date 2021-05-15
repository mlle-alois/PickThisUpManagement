package Services;

import Models.Liste;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.net.http.HttpResponse;

public class ListService {

    private final DatabaseService databaseService;

    private static String getList = "list";
    private static String addList = "list/add";
    private static String getListsFromBoard = "list/board";
    private static String updateList = "list/update";
    private static String deleteList = "list/";

    public ListService(UserService user) {
        this.databaseService = new DatabaseService(user);
    }


    public Liste[] getListes(Body body) throws JsonProcessingException {
        HttpResponse<String> result = databaseService.GetRequest(body, getList);
        if (result.statusCode() < 300) {
            return body.objectMapper.readValue(result.body(), Liste[].class);
        }
        return new Liste[0];
    }

    public Liste addListe(Body body) throws JsonProcessingException {
        HttpResponse<String> response = databaseService.PostRequest(body, addList);
        if (response.statusCode() < 300) {
            return body.objectMapper.readValue(response.body(), Liste.class);
        }
        return new Liste();
    }

    public Liste[] getListesFromBoard(Body body) throws JsonProcessingException {
        HttpResponse<String> result = databaseService.GetRequest(body, getListsFromBoard);
        if (result.statusCode() < 300) {
            return body.objectMapper.readValue(result.body(), Liste[].class);
        }
        return new Liste[0];
    }

    public boolean deleteListe(Body body) throws JsonProcessingException {
        return databaseService.DeleteRequest(body, deleteList);
    }

    public Liste updateListe(Body body) throws JsonProcessingException {
        HttpResponse<String> response = databaseService.PutRequest(body, updateList);
        if (response.statusCode() < 300) {
            return body.objectMapper.readValue(response.body(), Liste.class);
        }
        return new Liste();
    }
}
