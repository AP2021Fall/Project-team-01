package view.ProfileMenu;

import appController.AppController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import models.User;
import view.LoginMenuGraphic;
import view.MenusFxml;
import view.SceneController;

import java.io.IOException;
import java.net.URL;
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
            AppController.getOutputStream().writeUTF("ShowNotifications " + User.getToken());
            AppController.getOutputStream().flush();
            String result = AppController.getInputStream().readUTF();
            textNotifications.setText(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToMainMenu(ActionEvent actionEvent) throws IOException {
        if (LoginMenuGraphic.getRole(User.getActiveUsername()).equals("leader")) {
            sceneController.switchScene(MenusFxml.LEADER_MAIN_MENU.getLabel());
            return;
        }
        sceneController.switchScene(MenusFxml.MEMBER_MAIN_MENU.getLabel());
    }
}
