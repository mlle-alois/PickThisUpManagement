package javaFXInterface.controllers;
import Requete.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;



public class ConnectionSBController {

 @FXML
 TextField textAreaId;
 @FXML
 TextField testAreaPassword;

 public void connection(ActionEvent e) throws JsonProcessingException {

 User user = new Requete.User();

  var loginBody = new Requete.Body();
  loginBody.addValueToBody("mail",textAreaId.getText());
  loginBody.addValueToBody("password",testAreaPassword.getText());

  user.Login(loginBody);

 }
}
