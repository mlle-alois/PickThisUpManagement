package Requete;

import java.io.IOException;

public class PostRequest {

    public static void main(String[] args) throws IOException {


        User user = new User();

        var testBody = new Body();
        testBody.addValueToBody("mail", "IchaiDev");
        testBody.addValueToBody("password", "non");

        user.login(testBody);

        var Parameters = new Body();
        //  Parameters.addValueToBody("limit","3");
        Parameters.addValueToBody("id", "2");
        //   Board[] boards =  user.getBoards(Parameters);
        //   Board board =  user.getBoard(Parameters);
        //  Ticket[] tickets = user.getTickets(Parameters);
        //  Ticket ticket = user.getTicket(ticketParameters);
        // Liste[] listes = user.getListes(Parameters);
        // Liste liste = user.getListe(ticketParameters);
        // Status[] status = user.getStatus(Parameters);
        user.logout(Parameters);

    }
}
