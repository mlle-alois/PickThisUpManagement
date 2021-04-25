package Requete;

import Models.Board;

import java.io.IOException;

public class PostRequest {

    public static void main(String[] args) throws IOException {


        User user = new User();

        var testBody = new Body();
        testBody.addValueToBody("mail","IchaiDev");
        testBody.addValueToBody("password","non");

        user.Login(testBody);

        var boardParameters = new Body();
        boardParameters.addValueToBody("id","1");
       // Board[] boards =  user.getBoards(testBody);
        Board board =  user.getBoard(boardParameters);
    }
}