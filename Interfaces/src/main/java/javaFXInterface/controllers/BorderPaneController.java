package javaFXInterface.controllers;

import CLIInterface.Controllers.CLIInterfaceController;
import Models.Board;
import Models.Liste;
import Models.Status;
import Models.Ticket;
import Services.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.io.IOException;

import static Enum.InterfaceCode.*;

public class BorderPaneController {
    public static final String TOUS_LES_TICKETS = "Tous les tickets";
    public static final String AJOUTER_UN_TABLEAU = "Ajouter un tableau";
    @FXML
    private Button deleteBoardButton;
    @FXML
    private Button addListeButton;
    @FXML
    private Button updateBoardName;

    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem switchCLI;
    @FXML
    private MenuItem deconnexion;
    @FXML
    private MenuItem options;
    @FXML
    private Menu boardMenu;
    @FXML
    private Menu ticketMenu;
    @FXML
    private BorderPane borderPane;


    private Stage root;
    private Stage stage;
    private Scene scene;

    public Board currentBoard;
    public String currentStatusTicket;
    private TicketService ticketService;
    private ListService listService;
    private BoardService boardService;
    private Node boardPaneRight;



    private UserService user;
    public UserService getUser() {
        return user;
    }

    private Board[] boards;

    public Board[] getBoards() throws JsonProcessingException {
        Body body = new Body();
        return boardService.getBoards(body);
    }

    @SneakyThrows
    public void initialize(UserService user) {
        this.user = user;
        this.ticketService = new TicketService(user);
        this.listService = new ListService(user);
        this.boardService = new BoardService(user);

        initializeBoards();
        initializeTickets();

        currentBoard = boards[0];
        boardPaneRight = borderPane.getRight();
        setBorderPane();


    }

    public void setBorderPane() throws JsonProcessingException {
        addGridPaneToCenter();
        borderPane.setLeft(new Label(currentBoard.boardName));
        borderPane.setRight(boardPaneRight);
    }

    private void initializeTickets() throws JsonProcessingException {
        ticketMenu.getItems().addAll(getBranchsTickets(parseTickets()));
    }

    private void initializeBoards() throws JsonProcessingException {
        boardMenu.getItems().addAll(getBranchs(parseBoards()));
    }
    private void refreshBoards() throws JsonProcessingException {
        boardMenu.getItems().clear();
        initializeBoards();
    }

    public String[] parseBoards() throws JsonProcessingException {
        boards = getBoards();
        String[] allBoars = new String[boards.length + 1];
        for (int i = 0; i < boards.length; i++) {
            allBoars[i] = boards[i].boardName;
        }
       // allBoars[boards.length] = "Ajouter un tableau";
        return allBoars;
    }

    private MenuItem[] getBranchs(String[] boards) {
        MenuItem[] menuItems = new MenuItem[boards.length];

        for (int i = 0; i < boards.length; i++) {
            // Create event for switching boards
            EventHandler<ActionEvent> menuItemHandler = event -> {
                MenuItem menuItemTemp = (MenuItem) event.getSource();
                for (Board board:this.boards) {
                    if(board.boardName.equals(menuItemTemp.getText())){
                        currentBoard = board;
                    }
                }
                try {
                    setBorderPane();

                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                event.consume();
            };
            menuItems[i] = new MenuItem(boards[i]);
            menuItems[i].setOnAction(menuItemHandler);



        }
         menuItems[boards.length-1] = new MenuItem(AJOUTER_UN_TABLEAU);
        EventHandler<ActionEvent> menuItemAddBoard = getActionEventAddBoard();

        menuItems[boards.length-1].setOnAction(menuItemAddBoard);
        return menuItems;
    }


    public void selectItem() {
      /*  TreeItem<String> item = (TreeItem<String>) treeBoard.getSelectionModel().getSelectedItem();
        if(item != null){
            System.out.println(item.getValue());
        }*/
    }

    // Ticket Parts

    private String[] parseTickets() throws JsonProcessingException {
        Status[] Tickets = getTickets(user);
        String[] allTickets = new String[Tickets.length + 1];

        allTickets[0] = TOUS_LES_TICKETS;

        for (int i = 0; i < Tickets.length; i++) {
            allTickets[i + 1] = Tickets[i].statusLibelle;
        }
        return allTickets;
    }

    public Status[] getTickets(UserService user) throws JsonProcessingException {
        Body body = new Body();
        body.addValueToBody("limit", "3");
        return ticketService.getTicketsStatus(body);
    }

    public void setRightBorderPaneWithAddTicketButton(){
        Button button = new Button("Ajouter un ticket");

        EventHandler<ActionEvent> buttonAddHandler = event -> {

            if(event.getSource() == button) {
                Stage newStage;
                Parent root = null;
                Body body;
                newStage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddTicketToBoard.fxml"));

                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                AddTicketController popupController = loader.getController();
                newStage.setScene(new Scene(root));
                newStage.initModality(Modality.APPLICATION_MODAL);
                newStage.initOwner(button.getScene().getWindow());
                newStage.setTitle("PickThisUp");
                newStage.getIcons().add(new Image("/logo.PNG"));
                newStage.showAndWait();
                if(!popupController.isValidate())
                    return;
                // add the task to the database
                body = new Body();
                body.addValueToBody("name",popupController.getName());
                body.addValueToBody("description",popupController.getDescription());
                body.addValueToBody("statusId","1");
                try {
                    ticketService.addTicket(body);
                    addTicketGridToCenter();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                event.consume();
            }
        };
        button.setOnAction(buttonAddHandler);
        borderPane.setRight(button);
    }
    private MenuItem[] getBranchsTickets(String[] Tickets) {
        MenuItem[] tasksItems = new MenuItem[Tickets.length];



        for (int i = 0; i < Tickets.length; i++) {
            tasksItems[i] = new MenuItem(Tickets[i]);
            Label newLabel = new Label(Tickets[i]);
            // Create event for switching boards
            String ticketName = Tickets[i];
            EventHandler<ActionEvent> menuItemHandler = event -> {
                MenuItem menuItemTemp = (MenuItem) event.getSource();
                try {

                    currentStatusTicket = ticketName;
                    addTicketGridToCenter();
                    borderPane.setLeft(newLabel);
                    setRightBorderPaneWithAddTicketButton();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                event.consume();
            };


            tasksItems[i].setOnAction(menuItemHandler);
        }

        return tasksItems;
    }

    // Déconnexion part
    public void disconnect(ActionEvent event) throws IOException {
        if (!doesUserWantToDisconnect())
            return;
        Body body = new Body();
        if (user.logout(body))
            switchToConnexionScene(event, "/Connection.fxml");
    }

    public void switchToConnexionScene(ActionEvent event, String ScenePath) throws IOException {

        stage = (Stage) menuBar.getScene().getWindow();
        stage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource(ScenePath));
        root = loader.load();
        root.setTitle("PickThis");
        root.getIcons().add(new Image("/logo.PNG"));
        root.show();

    }

    private boolean doesUserWantToDisconnect() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Déconnexion");
        alert.setHeaderText("Vous êtes sur le point de vous déconnecter! ");
        alert.setContentText("Est-ce vous sur de vouloir vous déconnecter? ");

        return alert.showAndWait().get() == ButtonType.OK;
    }


    public void switchToCLI(ActionEvent actionEvent) throws IOException {
        stage = (Stage) menuBar.getScene().getWindow();
        stage.setOpacity(0);
        stage.setAlwaysOnTop(false);
        CLIInterfaceController.setPrintByInterfaceCode(BOARD, stage, user);

    }


       private void addGridPaneToCenter() throws JsonProcessingException {

        Body body = new Body();
        body.addValueToBody("",String.valueOf(currentBoard.boardId));
        Liste[] listes = listService.getListesFromBoard(body);
        ScrollPaneWithList scrollPaneWithList = new ScrollPaneWithList(listes,user,this);
        borderPane.setCenter(scrollPaneWithList.getFullScrollPane());

    }

    public void addTicketGridToCenter() throws JsonProcessingException {
        Body body = new Body();
        Ticket[] tickets;
        if (currentStatusTicket == TOUS_LES_TICKETS){
            tickets = ticketService.getTickets(body);
        }
        else{
            body.addValueToBody("status",String.valueOf(currentStatusTicket));
            tickets = ticketService.getTicketsByStatus(body);
        }
        ScrollPaneWithTickets scrollPaneWithTickets = new ScrollPaneWithTickets(tickets,user,this);
        borderPane.setCenter(scrollPaneWithTickets.getFullScrollPane());
    }

    @FXML
    private void addNewList(ActionEvent actionEvent) throws IOException {

        Stage newStage;
        Parent root;
        Body body;
        if(actionEvent.getSource() == addListeButton) {


           newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddListToBoard.fxml"));
            root = loader.load();
            AddListController popupController = loader.getController();
            newStage.setScene(new Scene(root));
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.initOwner(addListeButton.getScene().getWindow());
            newStage.setTitle("PickThisUp");
            newStage.getIcons().add(new Image("/logo.PNG"));
            newStage.showAndWait();
            if(!popupController.isValidate())
                return;
            // add the list to the database
            body = new Body();
             body.addValueToBody("name",popupController.getText());
            body.addValueToBody("boardId",String.valueOf(currentBoard.boardId));
          Liste liste =  listService.addListe(body);
            // Refresh
            setBorderPane();

        }
    }

    @FXML
    private void updateBoard(ActionEvent actionEvent) throws IOException {

        Stage newStage;
        Parent root;
        Body body;
        if(actionEvent.getSource() == updateBoardName) {


            newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateBoardName.fxml"));
            root = loader.load();
            UpdateBoardNameController popupController = loader.getController();
            newStage.setScene(new Scene(root));
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.initOwner(updateBoardName.getScene().getWindow());
            newStage.setTitle("PickThisUp");
            newStage.getIcons().add(new Image("/logo.PNG"));
            newStage.showAndWait();
            if (!popupController.isValidate())
                return;
            // add the list to the database
            body = new Body();
            body.addValueToBody("",String.valueOf(currentBoard.boardId));
            body.addValueToBody("name",popupController.getText());

            currentBoard = boardService.updateBoard(body);
            // Refresh
            refreshBoards();
            setBorderPane();

        }
    }


    private EventHandler<ActionEvent> getActionEventAddBoard() {
        EventHandler<ActionEvent> menuItemAddBoard = event -> {
            MenuItem menuItemTemp = (MenuItem) event.getSource();
            Stage newStage;
            Parent root = null;
            Body body;
            if(menuItemTemp.getText() == AJOUTER_UN_TABLEAU) {

                newStage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddBoard.fxml"));
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                AddListController popupController = loader.getController();
                newStage.setScene(new Scene(root));
                newStage.initModality(Modality.APPLICATION_MODAL);
                newStage.initOwner(updateBoardName.getScene().getWindow());
                newStage.setTitle("PickThisUp");
                newStage.getIcons().add(new Image("/logo.PNG"));
                newStage.showAndWait();
                if (!popupController.isValidate())
                    return;
                // add the list to the database
                body = new Body();
                body.addValueToBody("name",popupController.getText());


                // Refresh
                try {
                    boardService.addBoard(body);
                    refreshBoards();
                    setBorderPane();

                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
            event.consume();
        };
        return menuItemAddBoard;
    }


    public boolean deleteBoardAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppression de tableau");
        alert.setHeaderText("Vous êtes sur le point de supprimer ce tableau ainsi que toutes les listes" +
                " et tâches comprises à l'intérieur");
        alert.setContentText("Est-ce vous sur de vouloir supprimer le tableau " + currentBoard.boardName + " ?");

        return alert.showAndWait().get() == ButtonType.OK;

    }

    public void deleteBoard(ActionEvent actionEvent) throws JsonProcessingException {
        if(!deleteBoardAlert()){
            return;
        }
        Body body = new Body();
        body.addValueToBody("",String.valueOf(currentBoard.boardId));
        boardService.deleteBoard(body);
        currentBoard = boards[0];
        refreshBoards();
        setBorderPane();
    }


}
