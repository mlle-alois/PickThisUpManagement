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
        Parameters.addValueToBody("name","TacheTest03");
        Parameters.addValueToBody("description","Ceci est une merveilleuse description");
        Parameters.addValueToBody("listId","3");
     ///   Parameters.addValueToBody("boardId",String.valueOf(1));
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
     //   ListeService listeService = new ListeService(user);
        TaskService taskService = new TaskService(user);
       // Liste liste = listeService.addListe(Parameters);
       // Liste[] listes = listeService.getListesFromBoard(Parameters);
       // boolean result = listeService.deleteListe(Parameters);
       // boolean result = taskService.deleteTask(Parameters);
     //   Liste liste = listeService.updateListe(Parameters);
        //BoardService boardService = new BoardService(user);
        //boardService.updateBoard(Parameters);
        taskService.addTask(Parameters);
        Parameters.clear();
        user.logout(Parameters);

    }
}
