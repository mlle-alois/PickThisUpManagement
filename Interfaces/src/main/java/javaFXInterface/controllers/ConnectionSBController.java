package javaFXInterface.controllers;
import Requete.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class ConnectionSBController {

 private Stage stage;
 private Scene scene;
 private Parent root;
 @FXML
 TextField textAreaId;
 @FXML
 TextField testAreaPassword;

 public void connection(ActionEvent event) throws IOException {

 User user = new Requete.User();

  var loginBody = new Requete.Body();
  loginBody.addValueToBody("mail",textAreaId.getText());
  loginBody.addValueToBody("password",testAreaPassword.getText());

  user.Login(loginBody);

 // switchToScene(event,"/Boards.fxml",user);
  switchToScene(event, "/MenuBoard.fxml",user);
 }
 public void switchToScene(ActionEvent event,String ScenePath,User user) throws IOException {
  FXMLLoader loader= new FXMLLoader(getClass().getResource(ScenePath));

   root = loader.load();

 //  BoardController boardController = loader.getController();
  // boardController.initialize(user);

   BoardController boardController = loader.getController();
   boardController.initialize(user);
  //  ManagementController managementController = loader.getController();
  //  managementController.initialize(user);
  // TicketsController ticketsController = loader.getController();
   //ticketsController.initialize(user);
 // stage =  FXMLLoader.load(getClass().getResource(""));

  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
  scene = new Scene(root);
  stage.setScene(scene);
  stage.show();
 }

}
