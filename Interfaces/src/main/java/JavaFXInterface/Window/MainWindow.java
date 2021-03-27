package JavaFXInterface.Window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Enum.InterfaceCode;
import JavaFXInterface.Controllers.ContentPanelController;

import javax.swing.*;

public class MainWindow extends JFrame implements ActionListener{

    /**
     * Contenu de la fenetre principale
     */
    public MainWindow(InterfaceCode interfaceCode){

        this.setIconImage(new ImageIcon("pictures/logo.PNG").getImage());
        //titre de la fenetre
        this.setTitle("PickThisUp - Gestion");
        //positionnement de la fenetre dans le coin en haut à gauche
        this.setLocation(3, 3);
        //finir le programme à la fermeture de la fenetre
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //taille de la fenetre
        this.setSize(1200, 650);
        //fenetre non redimentionnable
        this.setResizable(false);
        // La JFrame s'ouvrira au milieu de l'écran
        this.setLocationRelativeTo(null);

        //changement du panel
        ContentPanelController.setContentPaneByInterfaceCode(interfaceCode, this);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
