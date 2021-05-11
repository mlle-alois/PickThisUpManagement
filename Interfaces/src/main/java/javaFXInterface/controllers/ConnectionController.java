package javaFXInterface.controllers;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectionController implements ActionListener {

    private JLabel lblError;
    private JTextField jtfLogin;
    private JPasswordField jpfPassword;

    public ConnectionController(JLabel lblError, JTextField jtfLogin, JPasswordField jpfPassword) {
        this.lblError = lblError;
        this.jtfLogin = jtfLogin;
        this.jpfPassword = jpfPassword;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Modele.connexion();
        //on récupère le mot de passe
        char[] c = jpfPassword.getPassword();
        String mdp = new String(c).trim();
        //et le mail
        String login = jtfLogin.getText().trim();
        //on vérifie que les champs sont remplis
        if(login.equals("") || mdp.equals("")){
            this.lblError.setText("Veuillez saisir tous les champs");
        }
        else{
            //on tente de connecter l'utilisateur
            this.lblError.setText("");
            /*boolean bool = Modele.connecterUser(login, mdp);
            //si les id sont corrects on accéde à l'application
            if(bool == true){
                //on récuére le type de l'utilisateur pour afficher le bon menu
                int typeUser = Modele.getTypeUser(login);
                //on garde en mémoire l'id de l'utilisateur connecté
                int idUser = Modele.getIdUser(login);
                user.setIdUser(idUser);
                //puis on affiche le panel
                fenetre.setContentPane(new Panel_Accueil(vehicule, materiel, ajouter, supprimer, modifier, rechercher, deconnexion1, deconnexion2, deconnexion3, statistique, statistique2, statistique3, emprunt, emprunts, vehicule2, materiel2, menuResponsable, menuVisiteur, menuDirecteur, menuVide, fenetre, idUser, typeUser));
                fenetre.revalidate();
            }
            //sinon erreur
            else{
                this.lblError.setText("Identifiants incorrects");
            }*/
        }
    }
}
