package JavaFXInterface.Panel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;

import JavaFXInterface.Controllers.ConnectionController;
import Controller.LabelController;
import JavaFXInterface.Controllers.ContentPanelController;
import JavaFXInterface.Menu.ApplicationMenu;
import Enum.InterfaceCode;

public class InscriptionPanel extends JPanel{

    private final LabelController labelController = new LabelController();

    private JLabel lblConnection;
    private JLabel lblEmail;
    private JLabel lblPassword;
    private final JLabel lblPasswordConfirmation;
    private JLabel lblError;
    private JTextField jtfLogin;
    private JPasswordField jpfPassword;
    private JButton buttonConnection;
    private JButton buttonInscription;
    private JButton buttonForgotPassword;
    //TODO inscription et mot de passe oublié

    /**
     * Constructeur du panel de la connexion
     */
    public InscriptionPanel(JFrame window){

        this.setLayout(new GridBagLayout());

        this.setBackground(Color.white);

        ApplicationMenu applicationMenu = new ApplicationMenu();
        window.setJMenuBar(applicationMenu.getEmptyMenuBar());

        //définition des labels
        this.lblConnection = new JLabel("Inscription");
        this.labelController.setTitle(lblConnection);
        this.lblEmail = new JLabel("E-mail : ");
        this.lblPassword = new JLabel("Mot de passe : ");
        this.lblPasswordConfirmation = new JLabel("Confirmation de mot de passe : ");
        this.lblError = new JLabel(" ");

        this.lblError.setForeground(new Color(200,0,2));

        //définition des zones de textes
        this.jtfLogin = new JTextField();
        this.jpfPassword = new JPasswordField();

        //dimension des zones de texte
        this.jtfLogin.setPreferredSize(new Dimension(150, 30));
        this.jpfPassword.setPreferredSize(new Dimension(150, 30));

        Border emptyBorder = BorderFactory.createEmptyBorder();

        //définition, nommage et action des boutons
        this.buttonConnection = new JButton("Valider");
        this.buttonConnection.setName("Connexion");
        this.buttonConnection.addActionListener(new ConnectionController(lblError, jtfLogin, jpfPassword));

        this.buttonInscription = new JButton("Inscription");
        this.buttonInscription.setName("Inscription");
        this.buttonInscription.setPreferredSize(new Dimension(80, 50));
        this.buttonInscription.setBorder(emptyBorder);
        this.buttonInscription.setBackground(Color.white);
        this.buttonInscription.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ContentPanelController.setContentPaneByInterfaceCode(InterfaceCode.INSCRIPTION, window);
            }
        });

        this.buttonForgotPassword = new JButton("Mot de passe oublié");
        this.buttonForgotPassword.setName("MdpOublie");
        this.buttonForgotPassword.setBorder(emptyBorder);
        this.buttonForgotPassword.setBackground(Color.white);
        //this.buttonIForgotPassword.addActionListener(/*new ActionBouton(vehicule, materiel, ajouter, supprimer, modifier, rechercher, deconnexion1, deconnexion2, deconnexion3, statistique, statistique2, statistique3, emprunt, emprunts, vehicule2, materiel2, menuResponsable, menuVisiteur, menuDirecteur, menuVide, fenetre, jtfLogin, jpfPassword, buttonConnection, lblError, lblError, lblError, user)*/);

        //ajout des éléments au panel
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        this.add(lblConnection, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        this.add(new JLabel(" "), gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        this.add(lblEmail, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        this.add(jtfLogin, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        this.add(lblPassword, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        this.add(jpfPassword, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        this.add(lblError, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        this.add(buttonConnection, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        this.add(buttonInscription, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        this.add(buttonForgotPassword, gridBagConstraints);

    }
}
