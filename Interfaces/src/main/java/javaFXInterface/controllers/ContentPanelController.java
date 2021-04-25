package javaFXInterface.controllers;

import Enum.InterfaceCode;
import javaFXInterface.Panel.ConnectionPanel;
import javaFXInterface.Panel.ForgotPasswordPanel;
import javaFXInterface.Panel.InscriptionPanel;

import javax.swing.*;

public class ContentPanelController {

    public static void setContentPaneByInterfaceCode(InterfaceCode interfaceCode, JFrame window) {
        switch (interfaceCode) {
            case CONNECTION -> {
                window.setVisible(true);
                window.setContentPane(new ConnectionPanel(window));
                window.revalidate();
            }
            case INSCRIPTION -> {
                window.setVisible(true);
                window.setContentPane(new InscriptionPanel(window));
                window.revalidate();
            }
            case FORGOT_PASSWORD -> {
                window.setVisible(true);
                window.setContentPane(new ForgotPasswordPanel(window));
                window.revalidate();
            }
        }
    }

}
