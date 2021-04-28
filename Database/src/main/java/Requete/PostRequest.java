package Requete;

import Models.Board;
import Models.Ticket;

import java.io.IOException;

public class PostRequest {

    public static void main(String[] args) throws IOException {


        User user = new User();

        var testBody = new Body();
        testBody.addValueToBody("mail","IchaiDev");
        testBody.addValueToBody("password","non");

        user.Login(testBody);

        var ticketParameters = new Body();
        ticketParameters.addValueToBody("id","1");
       // Board[] boards =  user.getBoards(testBody);
       // Board board =  user.getBoard(boardParameters);
      //  Ticket[] tickets = user.getTickets(boardParameters);
        Ticket ticket = user.getTicket(ticketParameters);
    }
}