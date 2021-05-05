package CLIInterface.Controllers;

import CLIInterface.Menu.ConnectionMenu;
import CLIInterface.Menu.GeneralMenu;
import Enum.InterfaceCode;
import Requete.User;
import javafx.stage.Stage;

import java.io.IOException;

public class CLIInterfaceController {

    public static void setContentPaneByInterfaceCode(InterfaceCode interfaceCode, Stage window, User user) throws IOException {
        switch (interfaceCode) {
            case CONNECTION -> {
                ConnectionMenu.printMenu(window, user);
            }
            case BOARD, TICKET -> {
                GeneralMenu.printGeneralMenu(window, user);
            }
        }
    }

}
