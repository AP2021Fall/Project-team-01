package view.ProfileMenu;

import controller.ProfileMenuController.ProfileMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import view.MenusFxml;
import view.SceneController;

import java.net.URL;
import java.util.ResourceBundle;

public class showMyProfileGraphic implements Initializable {
    public TextArea textMyProfile ;
    public SceneController sceneController = new SceneController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textMyProfile.setText(ProfileMenuController.showMyProfile());
    }

    public void showTeams(ActionEvent actionEvent) {
    }

    public void showMyProfile(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.PROFILE_MENU.getLabel());
    }

    public void changeUsername(ActionEvent actionEvent) {
    }

    public void changePassword(ActionEvent actionEvent) {
    }

    public void showLogs(ActionEvent actionEvent) {
    }

    public void showNotifications(ActionEvent actionEvent) {
    }

}
