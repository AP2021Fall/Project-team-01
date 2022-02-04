package view;

import controller.LoginController;
import controller.MainMenuController;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class SendNotificationGraphic {
    public TextField notification;
    public SceneController sceneController = new SceneController();

    public void send(ActionEvent actionEvent) throws SQLException {
        String notifications = notification.getText();
        if (notifications.isEmpty())
            return;
        switch (MainMenuController.choice) {
            case 1 :
                MainMenuController.sendNotificationToUser(notifications, MainMenuController.username);
                sceneController.switchScene(MenusFxml.LEADER_MAIN_MENU.getLabel());
                break;
            case 2:
                MainMenuController.sendNotificationToTeam(notifications, MainMenuController.team);
                sceneController.switchScene(MenusFxml.LEADER_MAIN_MENU.getLabel());
                break;
            case 3:
                MainMenuController.sendNotificationToAll(notifications);
                sceneController.switchScene(MenusFxml.LEADER_MAIN_MENU.getLabel());
                break;
            default:
                return;
        }
    }

    public void goToMainMenu(ActionEvent actionEvent) {
        if (LoginController.getActiveUser().getRole().equals("leader"))
            sceneController.switchScene(MenusFxml.LEADER_MAIN_MENU.getLabel());
        else
            sceneController.switchScene(MenusFxml.ADMIN_MAIN_MENU.getLabel());
    }
}
