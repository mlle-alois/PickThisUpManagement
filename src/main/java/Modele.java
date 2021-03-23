import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.PreparedStatement;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author opalali
 * @author azimmermann
 */
public class Modele {

    private static Connection connexion;
    private static Statement st;
    private static ResultSet rs;
    private static ResultSet rs2;
    private static PreparedStatement ps;

    public static void connexion() {
        try {
            //recuperation du driver
            Class.forName("com.mysql.jdbc.Driver");
            //connexion e la bdd
            connexion = DriverManager.getConnection("jdbc:mysql://mysql-pickthisup.alwaysdata.net/pickthisup_pickthisup", "229728", "P1ckTh1sUp");
            //creation du statement
            st = connexion.createStatement();
        } catch (ClassNotFoundException erreur) {
            System.out.println("Driver non charge !" + erreur);
        } catch (SQLException erreur) {
            System.out.println("La connexion e la base de donnees a echoue ou Erreur SQL" + erreur);
        }
    }

    /**
     * deconnexion de la bdd
     */
    public static void deconnexion() {
        try {
            //cleture de la connexion
            connexion.close();
        } catch (SQLException erreur) {
            System.out.println("La deconnexion e la base de donnees a echouee ou Erreur SQLe" + erreur);
        }
    }

    /**
     * Connection de l'utilisateur
     *
     * @param login
     * @param mdp
     * @return booleen de la reussite ou de l'echec de la connexion
     */
    public static boolean connecterUser(String login, String mdp) {
        boolean bool = false;
        try {
            //on recupere le nombre d'utilisateurs correspondant aux identifiants
            ps = connexion.prepareStatement("SELECT count(*) as nb FROM USER WHERE user_mail = ? AND user_password = ?");
            ps.setString(1, login);
            ps.setString(2, mdp);

            rs = ps.executeQuery();

            rs.next();
            //si il y en a un la connexion est reussie
            if (rs.getInt("nb") == 1) {
                bool = true;
            }

            rs.close();
        } catch (SQLException erreur) {
            System.out.println("La connexion e la base de donnees a echoue ou Erreur SQLe" + erreur);
        }

        return bool;
    }
}
