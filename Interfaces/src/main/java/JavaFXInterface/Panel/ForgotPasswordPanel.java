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

public class ForgotPasswordPanel extends JPanel {

    private final LabelController labelController = new LabelController();

    private JLabel lblConnexion;
    private JLabel lblEmail;
    private JLabel lblMdp;
    private JLabel lblError;
    private JTextField jtfLogin;
    private JPasswordField jpfPassword;
    private JButton buttonConnection;
    private JButton buttonInscription;
    private JButton buttonIForgotPassword;
    //TODO inscription et mot de passe oublié

    /**
     * Constructeur du panel de la connexion
     */
    public ForgotPasswordPanel(JFrame window){

        this.setLayout(new GridBagLayout());

        this.setBackground(Color.white);

        ApplicationMenu applicationMenu = new ApplicationMenu();
        window.setJMenuBar(applicationMenu.getEmptyMenuBar());

        //définition des labels
        this.lblConnexion = new JLabel("Mot de passe oublié");
        this.labelController.setTitle(lblConnexion);
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
        });
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
        this.add(buttonIForgotPassword, gridBagConstraints);

    }
}

