package view.ProfileMenu;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import view.SceneController;

public class ChangeUsernameMenuGraphic {

    public TextField newUsername;
    public Text alert;
    public SceneController sceneController = new SceneController();

    public void showTeams(ActionEvent actionEvent) {
        sceneController.switchScene();
    }

    public void showMyProfile(ActionEvent actionEvent) {
    }

    public void changeUsername(ActionEvent actionEvent) {
    }

    public void changePassword(ActionEvent actionEvent) {
    }

    public void showLogs(ActionEvent actionEvent) {
    }

    public void showNotifications(ActionEvent actionEvent) {
    }

    public void goToMainMenu(ActionEvent actionEvent) {
    }
}
