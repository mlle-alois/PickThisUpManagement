package CLIInterface.Controllers;

import CLIInterface.Menu.ConnectionMenu;
import CLIInterface.Menu.GeneralMenu;
import Enum.InterfaceCode;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class CLIInterfaceController {

    public static void setContentPaneByInterfaceCode(InterfaceCode interfaceCode, Stage window) throws IOException {
        switch (interfaceCode) {
            case CONNECTION -> {
                ConnectionMenu.printMenu(window);
            }
            case BOARD, TICKET -> {
                GeneralMenu.printGeneralMenu(window);
            }
        }
    }

}
