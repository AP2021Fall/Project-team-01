package view.ProfileMenu;

import controller.ProfileMenuController.ChangePasswordMenuController;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import view.MenusFxml;
import view.SceneController;

import java.sql.SQLException;

public class ChangePasswordMenuGraphic {

    public TextField newPassword;
    public SceneController sceneController = new SceneController();
    public Text alert;
    public PasswordField oldPassword;


    public void showTeams(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SHOW_TEAMS_MENU.getLabel());
    }

    public void showMyProfile(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SHOW_MY_PROFILE.getLabel());
    }

    public void changeUsername(ActionEvent actionEvent) throws SQLException {
        sceneController.switchScene(MenusFxml.CHANGE_USERNAME_MENU.getLabel());
    }

    public void changePassword(ActionEvent actionEvent) throws SQLException {
        String newPass = newPassword.getText();
        String oldPass = oldPassword.getText();
        if (newPass.isEmpty() || oldPass.isEmpty()) {
            alert.setText("please fill out all fields");
            return;
        }
        String result = ChangePasswordMenuController.changePassword(oldPass, newPass);
        switch (result) {
            case "login":
            case "password changed successfully":
                sceneController.switchScene(MenusFxml.LOGIN_MENU.getLabel());
                break;
            default:
                alert.setText(result);
        }
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
