package javaFXInterface.Panel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;

import javaFXInterface.controllers.ConnectionController;
import Controller.LabelController;
import javaFXInterface.controllers.ContentPanelController;
import javaFXInterface.Menu.ApplicationMenu;
import Enum.InterfaceCode;
import CLIInterface.Controllers.CLIInterfaceController;

public class ConnectionPanel extends JPanel{

    private final LabelController labelController = new LabelController();

    private JLabel lblConnection;
    private JLabel lblEmail;
    private JLabel lblMdp;
    //TODO voir comment afficher des pop-up d'erreur au lieu du label
    private JLabel lblError;
    private JTextField jtfLogin;
    private JPasswordField jpfPassword;
    private JButton buttonConnection;
    private JButton buttonInscription;
    private JButton buttonIForgotPassword;
    private JButton buttonCLI;
    //TODO inscription et mot de passe oublié

    /**
     * Constructeur du panel de la connexion
     */
    public ConnectionPanel(JFrame window){

        this.setLayout(new GridBagLayout());

        this.setBackground(Color.white);

        ApplicationMenu applicationMenu = new ApplicationMenu();
        window.setJMenuBar(applicationMenu.getEmptyMenuBar());

        //définition des labels
        this.lblConnection = new JLabel("Connexion");
        this.labelController.setTitle(lblConnection);
        this.lblEmail = new JLabel("E-mail : ");
        this.lblMdp = new JLabel("Mot de passe : ");
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
        //TODO
        this.buttonConnection.addActionListener(new ConnectionController(lblError, jtfLogin, jpfPassword));

        /* Mise de côté des fonctionnalités d'inscription et de mot de passe oublié sur la partie java

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

        this.buttonIForgotPassword = new JButton("Mot de passe oublié");
        this.buttonIForgotPassword.setName("MdpOublie");
        this.buttonIForgotPassword.setBorder(emptyBorder);
        this.buttonIForgotPassword.setBackground(Color.white);
        this.buttonIForgotPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ContentPanelController.setContentPaneByInterfaceCode(InterfaceCode.FORGOT_PASSWORD, window);
            }
        });*/

        this.buttonCLI = new JButton("Passer en CLI");
        this.buttonCLI.setPreferredSize(new Dimension(90, 20));
        this.buttonCLI.setFont(new Font("Arial", Font.PLAIN, 8));
        this.buttonCLI.setName("CLI");
        //TODO
        this.buttonCLI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setVisible(false);
                //CLIInterfaceController.setContentPaneByInterfaceCode(InterfaceCode.CONNECTION, window);
            }
        });

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
        this.add(lblMdp, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        this.add(jpfPassword, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        this.add(lblError, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        this.add(buttonConnection, gridBagConstraints);

        /*gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        this.add(buttonInscription, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        this.add(buttonIForgotPassword, gridBagConstraints);*/

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        this.add(new JLabel(" "), gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        this.add(buttonCLI, gridBagConstraints);

    }
}
