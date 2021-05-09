package Requete;

import Models.Liste;
import Models.StatusModel;
import Models.Ticket;
import Models.UserModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.net.http.HttpResponse;
import java.util.Map;

public class ListeService {

    private final DatabaseService databaseService;

    private static String getList = "list";
    private static String addList = "list/add";
    private static String getListsFromBoard = "list/board";

    public ListeService(User user) {
        this.databaseService = new DatabaseService(user);
    }


    public Liste[] getListes (Body body) throws JsonProcessingException {
        HttpResponse<String> result = databaseService.GetRequest(body,getList);
        if (result.statusCode() < 300) {
            return body.objectMapper.readValue(result.body(), Liste[].class);
        }
        return new Liste[0];
    }

    public Liste addListe(Body body) throws JsonProcessingException {
        HttpResponse<String> response = databaseService.PostRequest(body,addList);
        if(response.statusCode() < 300){
            return body.objectMapper.readValue(response.body(), Liste.class);
        }
        return new Liste();
    }

    public Liste[] getListesFromBoard (Body body) throws JsonProcessingException {
        HttpResponse<String> result = databaseService.GetRequest(body,getListsFromBoard);
        if (result.statusCode() < 300) {
            return body.objectMapper.readValue(result.body(), Liste[].class);
        }
        return new Liste[0];
    }

    public boolean deleteListe(Body body) throws JsonProcessingException {
        return databaseService.DeleteRequest(body,getList);
    }
}
