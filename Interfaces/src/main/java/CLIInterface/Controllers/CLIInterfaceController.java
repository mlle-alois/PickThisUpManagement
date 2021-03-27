package CLIInterface.Controllers;

import CLIInterface.Menu.ConnectionMenu;
import Enum.InterfaceCode;

import javax.swing.*;

public class CLIInterfaceController {

    public static void setContentPaneByInterfaceCode(InterfaceCode interfaceCode, JFrame window) {
        switch (interfaceCode) {
            case CONNECTION -> {
                ConnectionMenu.printMenu(window);
            }
            case INSCRIPTION -> {

            }
            case FORGOT_PASSWORD -> {

            }
        }
    }

}
