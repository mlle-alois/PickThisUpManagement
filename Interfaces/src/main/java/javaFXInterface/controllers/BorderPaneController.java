package javaFXInterface.controllers;

import CLIInterface.Controllers.CLIInterfaceController;
import Models.*;
import Requete.Body;
import Requete.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static Enum.InterfaceCode.*;

public class BorderPaneController {
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
    Stage root;
    Stage stage;
    Scene scene;

    String currentBoard;

    private User user;

    public Board[] getBoards(User user) throws JsonProcessingException {
        Body body = new Body();
        return user.getBoards(body);
    }

    @SneakyThrows
    public void initialize(User user) {
        System.out.println(user.token);
        this.user = user;
        initializeBoards();
        initializeTickets();
        addGridPaneToCenter();
    }

    private void initializeTickets() throws JsonProcessingException {
        ticketMenu.getItems().addAll(getBranchsTickets(parseTickets()));
    }

    private void initializeBoards() throws JsonProcessingException {
        boardMenu.getItems().addAll(getBranchs(parseBoards()));
    }

    public String[] parseBoards() throws JsonProcessingException {
        Board[] boards = getBoards(user);
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
            menuItems[i] = new MenuItem(boards[i]);
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
        Status[] Tickets = getTickets(user);
        String[] allTickets = new String[Tickets.length + 1];

        allTickets[0] = "Tous les tickets";

        for (int i = 0; i < Tickets.length; i++) {
            allTickets[i + 1] = Tickets[i].statusLibelle;
        }
        return allTickets;
    }

    public Status[] getTickets(User user) throws JsonProcessingException {
        Body body = new Body();
        body.addValueToBody("limit", "3");
        return user.getStatus(body);
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
        ScrollPane scrollPane = new ScrollPane();
        GridPane mainPane = new GridPane();
        setMainGridPaneShape(mainPane);
        
        Body body = new Body();
        Liste[] listes = user.getListes(body);

        create1VboxPerListe(mainPane, listes);

        scrollPane.setContent(mainPane);

        borderPane.setCenter(scrollPane);


    }

    private void create1VboxPerListe(GridPane mainPane, Liste[] listes) throws JsonProcessingException {
        for (int i = 0; i < listes.length; i++){
            VBox vbox = createVbox(listes[i]);

            mainPane.add(vbox,i,0);
        }
    }

    private void setMainGridPaneShape(GridPane mainPane) {
        mainPane.setPadding(new Insets(10,10,10,10));
        mainPane.setHgap(50);
        mainPane.setVgap(50);
    }

    private VBox createVbox(Liste liste) throws JsonProcessingException {
        VBox vbox = new VBox();

        Task[] tasks = getTasksFromListe(liste);
        setVboxShape(vbox, tasks);

        addTitleToVbox(liste, vbox);

        List<GridPane> gridPanes = getGridPanes(tasks);

        addPanesToVbox(vbox, gridPanes);

        return vbox;
    }

    private void addTitleToVbox(Liste liste, VBox vbox) {
        GridPane newGrid = getTitleListWithButtonEvents(liste);

        vbox.getChildren().add(newGrid);
    }

    private GridPane getTitleListWithButtonEvents(Liste liste) {
        GridPane newGrid = new GridPane();
        Label lbl = new Label(liste.listName);
        newGrid.add(lbl,0,0);
        lbl.setPrefSize(50,50);

        // Set Event when clicked from buttons
        EventHandler<ActionEvent> buttonModifHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Modifier");
                event.consume();
            }
        };

        EventHandler<ActionEvent> buttonEraseHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Supprimer");
                event.consume();
            }
        };

        Button modifButton = new Button("Modifer");
        modifButton.setOnAction(buttonModifHandler);

        Button eraseButton = new Button("Supprimer");
        eraseButton.setOnAction(buttonEraseHandler);

        VerticalButtonBar bar = new VerticalButtonBar();
        bar.addButton(modifButton);
        bar.addButton(eraseButton);

        // Set Column constraints
        ColumnConstraints column1 = new ColumnConstraints();
        ColumnConstraints column2 = new ColumnConstraints();
        column1.setPrefWidth(150);
        column2.setPrefWidth(150);

        newGrid.getColumnConstraints().add(column1);
        newGrid.getColumnConstraints().add(column2);
        newGrid.add(bar,1,0,1,1);
        return newGrid;
    }

    private void addPanesToVbox(VBox vbox, List<GridPane> gridPanes) {
        for (GridPane gridPane: gridPanes) {
            vbox.getChildren().add(gridPane);
        }
    }

    private List<GridPane> getGridPanes(Task[] tasks) {
        List<GridPane> gridPanes = new ArrayList<>();

        for (Task task: tasks) {
            GridPane newGrid = new GridPane();
            setGridShape(newGrid);

            addTaskToGrid(task, newGrid);

            gridPanes.add(newGrid);
        }
        return gridPanes;
    }

    private void setVboxShape(VBox vbox, Task[] tasks) {
        int vboxLength = (tasks.length*200+100);
        vbox.setSpacing(20);
        vbox.setPrefSize(150,vboxLength);
        vbox.setStyle("-fx-background-color: #d3d4cb");
    }

    private Task[] getTasksFromListe(Liste liste) throws JsonProcessingException {
        // Get all the tasks linked to the list
        Body body = new Body();
        body.addValueToBody("id",String.valueOf(liste.listId));
        return user.getTasksFromList(body);
    }

    private void addTaskToGrid(Task task, GridPane newGrid) {
        // add title of task
        Label lblTaskName = new Label(task.taskName);
        newGrid.add(lblTaskName,0,0);
        // add task's description
        TextArea lblTextArea = new TextArea(task.taskDescription);
        lblTextArea.setWrapText(true);
        lblTextArea.setEditable(false);
        lblTextArea.setStyle("-fx-control-inner-background: lightgray;");
        newGrid.add(lblTextArea,0,1);

        addButtonBarToGrid(newGrid);
    }

    private void addButtonBarToGrid(GridPane newGrid) {
        // Set Event when clicked from buttons
        EventHandler<ActionEvent> buttonModifHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Modifier");
                event.consume();
            }
        };

        EventHandler<ActionEvent> buttonEraseHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Supprimer");
                event.consume();
            }
        };

        Button modifButton = new Button("Modifer");
        modifButton.setOnAction(buttonModifHandler);

        Button eraseButton = new Button("Supprimer");
        eraseButton.setOnAction(buttonEraseHandler);

        ButtonBar buttonBar = new ButtonBar();
        buttonBar.getButtons().addAll(modifButton,eraseButton);
        newGrid.add(buttonBar,0,2,2,1);
    }

    private void setGridShape(GridPane newGrid) {
        newGrid.setPadding(new Insets(10,   10,10,  10));
        //   newGrid.setGridLinesVisible(true);

        newGrid.setPrefSize(100,180);
        // set style
        newGrid.setStyle("-fx-background-color: blue, lightgray;-fx-border-color:black; -fx-border-width: 1; -fx-border-style: solid;");
        // set Gap between row and column
        newGrid.setHgap(10);
        newGrid.setVgap(5);

        // Set Column constraints
        ColumnConstraints column1 = new ColumnConstraints();
        ColumnConstraints column2 = new ColumnConstraints();
        column1.setPrefWidth(100);
        column2.setPrefWidth(200);

        newGrid.getColumnConstraints().add(column1);
        newGrid.getColumnConstraints().add(column2);

        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints();
        row1.setPrefHeight(100);
        row2.setPrefHeight(200);

        newGrid.getRowConstraints().add(row1);
        newGrid.getRowConstraints().add(row2);
    }
}
