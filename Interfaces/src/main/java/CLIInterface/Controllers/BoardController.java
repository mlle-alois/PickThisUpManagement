package CLIInterface.Controllers;

import Models.Board;
import Requete.BoardService;
import Requete.Body;
import Requete.User;
import com.fasterxml.jackson.core.JsonProcessingException;

public class BoardController {

    private final User user;
    private final BoardService boardService;

    public BoardController(User user) {
        this.user = user;
        this.boardService = new BoardService(user);
    }

    /**
     * récupération de tous les tableaux
     *
     * @return
     * @throws JsonProcessingException
     */
    public Board[] getBoards() throws JsonProcessingException {
        return boardService.getBoards(new Body());
    }

    /**
     * récupération de tous les champs qui seront présents dans le menu des tableaux
     *
     * @return
     * @throws JsonProcessingException
     */
    public String[] parseBoards() throws JsonProcessingException {
        Board[] boards = getBoards();
        String[] allBoars = new String[boards.length + 1];
        for (int i = 0; i < boards.length; i++) {
            allBoars[i] = boards[i].boardName;
        }
        allBoars[boards.length] = "Ajouter un tableau";
        return allBoars;
    }

    /**
     * ajouter un tableau
     *
     * @param name
     * @throws JsonProcessingException
     */
    public void addBoard(String name) throws JsonProcessingException {
        Body body = new Body();
        body.addValueToBody("name", name);
        boardService.addBoard(body);
    }

    /**
     * mettre à jour un tableau
     *
     * @param boardId
     * @param name
     * @return
     * @throws JsonProcessingException
     */
    public Board updateBoard(Integer boardId, String name) throws JsonProcessingException {
        Body body = new Body();
        body.addValueToBody("", String.valueOf(boardId));
        body.addValueToBody("name", name);
        return boardService.updateBoard(body);
    }

    /**
     * supprimer un tableau
     *
     * @param boardId
     * @return
     */
    public boolean deleteBoard(int boardId) {
        Body body = new Body();
        body.addValueToBody("", String.valueOf(boardId));
        return boardService.deleteBoard(body);
    }
}
