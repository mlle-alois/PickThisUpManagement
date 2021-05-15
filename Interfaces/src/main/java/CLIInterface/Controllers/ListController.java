package CLIInterface.Controllers;

import Models.Liste;
import Requete.Body;
import Requete.ListService;
import Requete.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;

public class ListController {

    private final UserService user;
    private final ListService listService;

    public ListController(UserService user) {
        this.user = user;
        this.listService = new ListService(user);
    }

    /**
     * ajout d'une liste
     * @param boardId
     * @param name
     * @return
     * @throws JsonProcessingException
     */
    public Liste addListe(Integer boardId, String name) throws JsonProcessingException {
        Body body = new Body();
        body.addValueToBody("boardId", String.valueOf(boardId));
        body.addValueToBody("name", name);
        return listService.addListe(body);
    }

    /**
     * modifier une liste
     * @param listId
     * @param name
     * @return
     * @throws JsonProcessingException
     */
    public Liste updateListe(Integer listId, String name) throws JsonProcessingException {
        Body body = new Body();
        body.addValueToBody("", String.valueOf(listId));
        body.addValueToBody("name", name);
        return listService.updateListe(body);
    }

    /**
     * suppression de liste
     * @param listId
     * @return
     * @throws JsonProcessingException
     */
    public boolean deleteListe(Integer listId) throws JsonProcessingException {
        Body body = new Body();
        body.addValueToBody("", String.valueOf(listId));
        return listService.deleteListe(body);
    }
}
