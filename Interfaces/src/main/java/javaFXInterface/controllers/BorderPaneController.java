package javaFXInterface.controllers;

import CLIInterface.Controllers.CLIInterfaceController;
import CLIInterface.Menu.BoardMenu;
import Models.Board;
import Models.Liste;
import Models.StatusModel;
import Requete.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

    // Screen AddList
    @FXML
    private Button validateAddListButton;
    @FXML
    private Button cancelAddListButton;
    @FXML
    private TextField addListTextField;

    private Stage root;
    private Stage stage;
    private Scene scene;

    public Board currentBoard;
    private TicketsService ticketsService;
    private ListService listService;
    private BoardService boardService;



    private User user;
    public User getUser() {
        return user;
    }

    private Board[] boards;

    public Board[] getBoards() throws JsonProcessingException {
        Body body = new Body();
        //return user.getBoards(body);
        return boardService.getBoards(body);
    }

    @SneakyThrows
    public void initialize(User user) {
        this.user = user;
        this.ticketsService = new TicketsService(user);
        this.listService = new ListService(user);
        this.boardService = new BoardService(user);

        initializeBoards();
        initializeTickets();

        currentBoard = boards[0];
        setBorderPane();

    }

    public void setBorderPane() throws JsonProcessingException {
        addGridPaneToCenter();
      //  TextArea lblTextArea = new TextArea(currentBoard.boardName);
      //  VboxForList vboxForList = new VboxForList(user,this);
        borderPane.setLeft(new Label(currentBoard.boardName));
      //  borderPane.setLeft(vboxForList.getTitleVbox());

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
        allBoars[boards.length] = "Ajouter un tableau";
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
        StatusModel[] Tickets = getTickets(user);
        String[] allTickets = new String[Tickets.length + 1];

        allTickets[0] = "Tous les tickets";

        for (int i = 0; i < Tickets.length; i++) {
            allTickets[i + 1] = Tickets[i].statusLibelle;
        }
        return allTickets;
    }

    public StatusModel[] getTickets(User user) throws JsonProcessingException {
        Body body = new Body();
        body.addValueToBody("limit", "3");
        return ticketsService.getTicketsStatus(body);
    }

    private MenuItem[] getBranchsTickets(String[] Tickets) {
        MenuItem[] tasksItems = new MenuItem[Tickets.length];

        for (int i = 0; i < Tickets.length; i++) {
            tasksItems[i] = new MenuItem(Tickets[i]);
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
        CLIInterfaceController.setContentPaneByInterfaceCode(BOARD, stage, user);

    }


       private void addGridPaneToCenter() throws JsonProcessingException {

        Body body = new Body();
        body.addValueToBody("",String.valueOf(currentBoard.boardId));
        Liste[] listes = listService.getListesFromBoard(body);
        ScrollPaneWithList scrollPaneWithList = new ScrollPaneWithList(listes,user,this);
        borderPane.setCenter(scrollPaneWithList.getFullScrollPane());

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
            // add the list to the database
            body = new Body();
            body.addValueToBody("",String.valueOf(currentBoard.boardId));
            body.addValueToBody("name",popupController.getText());

            currentBoard = boardService.updateBoard(body);
            // Refresh
            refreshBoards();
          //  initialize(this.getUser());
            setBorderPane();

        }
    }

/*    public void switchToScene(ActionEvent event, String ScenePath, User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ScenePath));

        root = loader.load();

        BorderPaneController borderPaneController = loader.getController();
        borderPaneController.initialize(user);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }*/

}
