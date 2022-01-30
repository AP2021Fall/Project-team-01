package view.ProfileMenu;

import controller.ProfileMenuController.ProfileMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import view.MenusFxml;
import view.SceneController;

import java.net.URL;
import java.sql.SQLException;
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
    }

    public void changePassword(ActionEvent actionEvent) {
    }

    public void showLogs(ActionEvent actionEvent) {
    }

    public void showNotifications(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            textNotifications.setText(ProfileMenuController.showNotifications().toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
