package CLIInterface.Controllers;

import CLIInterface.Menu.ConnectionMenu;
import CLIInterface.Menu.GeneralMenu;
import Enum.InterfaceCode;
import Services.UserService;
import javafx.stage.Stage;

import java.io.IOException;

public class CLIInterfaceController {

    public static void setPrintByInterfaceCode(InterfaceCode interfaceCode, Stage window, UserService user) throws IOException {
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
