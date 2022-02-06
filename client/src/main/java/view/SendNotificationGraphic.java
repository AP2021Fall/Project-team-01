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
        String choice = AppController.getResult("choice " + User.getToken());
        switch (choice) {
            case "1" :
                AppController.getResult("sendToUser " + notifications + " " + User.getToken());
                sceneController.switchScene(MenusFxml.LEADER_MAIN_MENU.getLabel());
                break;
            case "2":
                AppController.getResult("sendToTeam " + notifications + " " + User.getToken());
                sceneController.switchScene(MenusFxml.LEADER_MAIN_MENU.getLabel());
                break;
            case "3":
                AppController.getResult("sendToAll " + notifications);
                sceneController.switchScene(MenusFxml.LEADER_MAIN_MENU.getLabel());
                break;
            default:
                return;
        }
    }

    public void goToMainMenu(ActionEvent actionEvent) throws IOException {
        String role = AppController.getResult("role " + User.getActiveUsername());
        if (role.equals("leader"))
            sceneController.switchScene(MenusFxml.LEADER_MAIN_MENU.getLabel());
        else
            sceneController.switchScene(MenusFxml.ADMIN_MAIN_MENU.getLabel());
    }
}
