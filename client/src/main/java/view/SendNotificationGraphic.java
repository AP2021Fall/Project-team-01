package view;

import appController.AppController;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import models.User;

import java.io.IOException;
import java.sql.SQLException;

public class SendNotificationGraphic {
    public TextField notification;
    public SceneController sceneController = new SceneController();

    public void send(ActionEvent actionEvent) throws SQLException, IOException {
        String notifications = notification.getText();
        if (notifications.isEmpty())
            return;
        AppController.getOutputStream().writeUTF("choice " + User.getToken());
        AppController.getOutputStream().flush();
        String choice = AppController.getInputStream().readUTF();
        switch (choice) {
            case "1" :
                AppController.getOutputStream().writeUTF("sendToUser " + notifications + " " + User.getToken());
                AppController.getOutputStream().flush();
                AppController.getInputStream().read();
                sceneController.switchScene(MenusFxml.LEADER_MAIN_MENU.getLabel());
                break;
            case "2":
                AppController.getOutputStream().writeUTF("sendToTeam " + notifications + " " + User.getToken());
                AppController.getOutputStream().flush();
                AppController.getInputStream().read();
                sceneController.switchScene(MenusFxml.LEADER_MAIN_MENU.getLabel());
                break;
            case "3":
                AppController.getOutputStream().writeUTF("sendToAll " + notifications);
                AppController.getOutputStream().flush();
                AppController.getInputStream().read();
                sceneController.switchScene(MenusFxml.LEADER_MAIN_MENU.getLabel());
                break;
            default:
                return;
        }
    }

    public void goToMainMenu(ActionEvent actionEvent) throws IOException {
        AppController.getOutputStream().writeUTF("role " + User.getActiveUsername());
        AppController.getOutputStream().flush();
        String role = AppController.getInputStream().readUTF();
        if (LoginController.getActiveUser().getRole().equals("leader"))
            sceneController.switchScene(MenusFxml.LEADER_MAIN_MENU.getLabel());
        else
            sceneController.switchScene(MenusFxml.ADMIN_MAIN_MENU.getLabel());
    }
}
