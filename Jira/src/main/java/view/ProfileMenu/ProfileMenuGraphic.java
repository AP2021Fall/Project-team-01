package view.ProfileMenu;

import controller.LoginController;
import javafx.event.ActionEvent;
import view.MenusFxml;
import view.SceneController;

public class ProfileMenuGraphic {
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

    public void showNotifications(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SHOW_NOTIFICATION_MENU.getLabel());
    }

    public void goToMainMenu(ActionEvent actionEvent) {
        if (LoginController.getActiveUser().getRole().equals("member"))
            sceneController.switchScene(MenusFxml.MEMBER_MAIN_MENU.getLabel());
        else if (LoginController.getActiveUser().getRole().equals("leader"))
            sceneController.switchScene(MenusFxml.LEADER_MAIN_MENU.getLabel());
        else if (LoginController.getActiveUser().getRole().equals("admin"))
            sceneController.switchScene(MenusFxml.ADMIN_MAIN_MENU.getLabel());
    }
}
