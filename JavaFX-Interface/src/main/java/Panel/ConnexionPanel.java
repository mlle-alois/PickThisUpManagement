package Panel;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import Controller.LabelController;
import Menu.ApplicationMenu;

public class ConnexionPanel extends JPanel{

    private final LabelController labelController = new LabelController();

    private JLabel lblConnexion;
    private JLabel lblEmail;
    private JLabel lblMdp;
    private JLabel lblErreur;
    private JTextField jtfLogin;
    private JPasswordField jpfMdp;
    private JButton boutonConnexion;
    private JButton boutonInscription;
    private JButton boutonMdpOublie;
    //TODO inscription et mot de passe oublié

    /**
     * Constructeur du panel de la connexion
     */
    public ConnexionPanel(JFrame window){

        this.setLayout(new GridBagLayout());

        this.setBackground(Color.white);

        ApplicationMenu applicationMenu = new ApplicationMenu();
        window.setJMenuBar(applicationMenu.getEmptyMenuBar());

        //définition des labels
        this.lblConnexion = new JLabel("Connexion");
        this.labelController.setTitle(lblConnexion);
        this.lblEmail = new JLabel("E-mail : ");
        this.lblMdp = new JLabel("Mot de passe : ");
        this.lblErreur = new JLabel(" ");

        //définition des zones de textes
        this.jtfLogin = new JTextField();
        this.jpfMdp = new JPasswordField();

        //dimension des zones de texte
        this.jtfLogin.setPreferredSize(new Dimension(150, 30));
        this.jpfMdp.setPreferredSize(new Dimension(150, 30));

        Border emptyBorder = BorderFactory.createEmptyBorder();

        //définition, nommage et action des boutons
        this.boutonConnexion = new JButton("Valider");
        this.boutonConnexion.setName("Connexion");
        //this.boutonConnexion.addActionListener(/*new ActionBouton(vehicule, materiel, ajouter, supprimer, modifier, rechercher, deconnexion1, deconnexion2, deconnexion3, statistique, statistique2, statistique3, emprunt, emprunts, vehicule2, materiel2, menuResponsable, menuVisiteur, menuDirecteur, menuVide, fenetre, jtfLogin, jpfMdp, boutonConnexion, lblErreur, lblErreur, lblErreur, user)*/);

        this.boutonInscription = new JButton("Inscription");
        this.boutonInscription.setName("Inscription");
        this.boutonInscription.setPreferredSize(new Dimension(80, 50));
        this.boutonInscription.setBorder(emptyBorder);
        this.boutonInscription.setBackground(Color.white);
        //this.boutonInscription.addActionListener(/*new ActionBouton(vehicule, materiel, ajouter, supprimer, modifier, rechercher, deconnexion1, deconnexion2, deconnexion3, statistique, statistique2, statistique3, emprunt, emprunts, vehicule2, materiel2, menuResponsable, menuVisiteur, menuDirecteur, menuVide, fenetre, jtfLogin, jpfMdp, boutonConnexion, lblErreur, lblErreur, lblErreur, user)*/);

        this.boutonMdpOublie = new JButton("Mot de passe oublié");
        this.boutonMdpOublie.setName("MdpOublie");
        this.boutonMdpOublie.setBorder(emptyBorder);
        this.boutonMdpOublie.setBackground(Color.white);
        //this.boutonMdpOublie.addActionListener(/*new ActionBouton(vehicule, materiel, ajouter, supprimer, modifier, rechercher, deconnexion1, deconnexion2, deconnexion3, statistique, statistique2, statistique3, emprunt, emprunts, vehicule2, materiel2, menuResponsable, menuVisiteur, menuDirecteur, menuVide, fenetre, jtfLogin, jpfMdp, boutonConnexion, lblErreur, lblErreur, lblErreur, user)*/);

        //ajout des éléments au panel
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        this.add(lblConnexion, gridBagConstraints);

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
        this.add(jpfMdp, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        this.add(lblErreur, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        this.add(boutonConnexion, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        this.add(boutonInscription, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        this.add(boutonMdpOublie, gridBagConstraints);

    }
}
