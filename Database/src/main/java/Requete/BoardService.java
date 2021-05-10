package Requete;

import Models.Board;
import Models.Liste;
import Models.Task;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.net.http.HttpResponse;

public class BoardService {

    private final DatabaseService databaseService;

    private static String getBoards = "board";

    public BoardService(User user) {
        this.databaseService = new DatabaseService(user);
    }


    public Board[] getBoards (Body body) throws JsonProcessingException {
        HttpResponse<String> result = databaseService.GetRequest(body,getBoards);
        if (result.statusCode() < 300){
            return body.objectMapper.readValue(result.body(), Board[].class);
        }
        return new Board[0];
    }
    public Board updateBoard(Body body) throws JsonProcessingException {
        HttpResponse<String> response = databaseService.PutRequest(body,getBoards);
        if(response.statusCode() < 300){
            return body.objectMapper.readValue(response.body(), Board.class);
        }
        return new Board();
    }


    public boolean deleteBoard(Body body) {
        return databaseService.DeleteRequest(body,getBoards);
    }
}

