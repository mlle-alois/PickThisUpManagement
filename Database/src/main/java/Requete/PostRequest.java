package Requete;

import Models.Board;
import Models.Liste;
import Models.Status;
import Models.Ticket;

import java.io.IOException;

public class PostRequest {

    public static void main(String[] args) throws IOException {


        User user = new User();

        var testBody = new Body();
        testBody.addValueToBody("mail","IchaiDev");
        testBody.addValueToBody("password","non");

        user.Login(testBody);

        var Parameters = new Body();
        Parameters.addValueToBody("limit","3");
      //  ticketParameters.addValueToBody("id","2");
       // Board[] boards =  user.getBoards(testBody);
       // Board board =  user.getBoard(boardParameters);
      //  Ticket[] tickets = user.getTickets(boardParameters);
      //  Ticket ticket = user.getTicket(ticketParameters);
       // Liste[] listes = user.getListes(ticketParameters);
       // Liste liste = user.getListe(ticketParameters);
        Status[] status = user.getStatus(Parameters);

    }
}