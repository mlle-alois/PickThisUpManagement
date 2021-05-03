package javaFXInterface.controllers;

import Requete.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


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

    private static String errorConnection = "La connexion a échoué";

    public void connection(ActionEvent event) throws IOException {

        User user = new Requete.User();

        var loginBody = new Requete.Body();
        loginBody.addValueToBody("mail", textAreaId.getText());
        loginBody.addValueToBody("password", testAreaPassword.getText());

        if (user.login(loginBody)) {
            switchToScene(event, "/MenuBarBoard.fxml", user);
        } else {
            labelError.setText(errorConnection);
        }
    }

    public void switchToScene(ActionEvent event, String ScenePath, User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ScenePath));

        root = loader.load();

   /*BoardController boardController = loader.getController();
   boardController.initialize(user);*/
        MenuController menuController = loader.getController();
        menuController.initialize(user);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
