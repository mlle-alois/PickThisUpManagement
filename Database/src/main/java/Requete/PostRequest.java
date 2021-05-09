package Requete;

import Models.Liste;
import Models.Task;

import java.io.IOException;

public class PostRequest {

    public static void main(String[] args) throws IOException {


        User user = new User();

        var testBody = new Body();
        testBody.addValueToBody("mail", "IchaiDev");
        testBody.addValueToBody("password", "non");

        user.login(testBody);

        var Parameters = new Body();
      //  Parameters.addValueToBody("name","NListe2");
       // Parameters.addValueToBody("boardId","1");
        Parameters.addValueToBody("","2");
        //  Parameters.addValueToBody("limit","3");
     //   Parameters.addValueToBody("", "2");
        //   Board[] boards =  user.getBoards(Parameters);
        //   Board board =  user.getBoard(Parameters);
        //  Ticket[] tickets = user.getTickets(Parameters);
        //  Ticket ticket = user.getTicket(ticketParameters);
        // Liste[] listes = user.getListes(Parameters);
        // Liste liste = user.getListe(ticketParameters);
        // Status[] status = user.getStatus(Parameters);
         //Task[] task = user.getTasksFromList(Parameters);
        ListeService listeService = new ListeService(user);
       // Liste liste = listeService.addListe(Parameters);
        Liste[] listes = listeService.getListesFromBoard(Parameters);

        user.logout(Parameters);

    }
}
