package view.ProfileMenu;

import controller.ProfileMenuController.ProfileMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import view.MenusFxml;
import view.SceneController;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class showNotificationsGraphic implements Initializable {
    public TextArea textNotifications;
    public SceneController sceneController = new SceneController();

    public void showTeams(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SHOW_TEAMS_MENU.getLabel());
    }

    public void showMyProfile(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SHOW_MY_PROFILE.getLabel());
    }

    public void changeUsername(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.CHANGE_USERNAME_MENU.getLabel());
    }

    public void changePassword(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.CHANGE_PASSWORD_MENU.getLabel());
    }

    public void showLogs(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SHOW_LOGS_MENU.getLabel());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ArrayList<String> notifications = ProfileMenuController.showNotifications();
            StringBuilder notification = new StringBuilder();
            for (String i : notifications) {
                notification.append(i).append("\n");
            }
            textNotifications.setText(notification.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void goToMainMenu(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.MEMBER_MAIN_MENU.getLabel());
    }
}
