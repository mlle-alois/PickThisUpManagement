package javaFXInterface.controllers;

import CLIInterface.Controllers.CLIInterfaceController;
import Services.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import static Enum.InterfaceCode.CONNECTION;


public class ConnectionSBController {
    @FXML
    private Label labelError;

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    TextField textAreaId;
    @FXML
    PasswordField testAreaPassword;

    private UserService user;

    private static String errorConnection = "La connexion a échoué";

    public void connection(ActionEvent event) throws IOException {

        this.user = new UserService();

        var loginBody = new Services.Body();
        loginBody.addValueToBody("mail", textAreaId.getText());
        loginBody.addValueToBody("password", testAreaPassword.getText());

        if (user.login(loginBody)) {
            switchToScene(event, "/BorderPaneBoard.fxml", user);
        } else {
            labelError.setText(errorConnection);
        }
    }

    public void switchToScene(ActionEvent event, String ScenePath, UserService user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ScenePath));

        root = loader.load();

        BorderPaneController borderPaneController = loader.getController();
        borderPaneController.initialize(user);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToConnectionUML(String ScenePath, Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ScenePath));

        stage = loader.load();

        stage.setTitle("PickThisUp");
        stage.getIcons().add(new Image("/logo.PNG"));

        scene = stage.getScene();
        stage.setScene(scene);
        stage.setOpacity(1);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    public void switchToUML(String ScenePath, Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ScenePath));

        root = loader.load();

        stage.setTitle("PickThisUp");
        stage.getIcons().add(new Image("/logo.PNG"));

        scene = stage.getScene();
        stage.setScene(scene);
        stage.setOpacity(1);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    public void switchToCLI(ActionEvent event) throws IOException {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setOpacity(0);
        stage.setAlwaysOnTop(false);
        CLIInterfaceController.setPrintByInterfaceCode(CONNECTION, stage, user);
    }


}
