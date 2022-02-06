package view.ProfileMenu;

import appController.AppController;
import controller.ProfileMenuController.ProfileMenuController;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import view.MenusFxml;
import view.SceneController;

import java.io.IOException;
import java.sql.SQLException;

public class ChangeUsernameMenuGraphic {

    public TextField newUsername;
    public Text alert;
    public SceneController sceneController = new SceneController();

    public void showTeams(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SHOW_TEAMS_MENU.getLabel());
    }

    public void showMyProfile(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SHOW_MY_PROFILE.getLabel());
    }

    public void changeUsername(ActionEvent actionEvent) throws SQLException, IOException {
        if (newUsername.getText().isEmpty()) {
            alert.setText("please fill out field");
            return;
        }

        AppController.getOutputStream().writeUTF("ChangeUsername " + newUsername);
        AppController.getOutputStream().flush();
        String result = AppController.getInputStream().readUTF();
//        String result = ProfileMenuController.changeUsername(newUsername.getText());
        if (result.equals("username successfully changed")) {
            sceneController.switchScene(MenusFxml.LOGIN_MENU.getLabel());
            return;
        }
        alert.setText(result);
    }

    public void changePassword(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.CHANGE_PASSWORD_MENU.getLabel());
    }

    public void showLogs(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SHOW_LOGS_MENU.getLabel());
    }

    public void showNotifications(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SHOW_NOTIFICATION_MENU.getLabel());
    }

    public void goToMainMenu(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.MEMBER_MAIN_MENU.getLabel());
    }
}
