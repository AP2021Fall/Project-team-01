package view.ProfileMenu;

import javafx.event.ActionEvent;
import javafx.stage.Stage;
import view.SceneController;

public class ProfileMenuGraphic {
    public static Stage stage;
    public SceneController sceneController = new SceneController();

    public void showTeams(ActionEvent actionEvent) {
        sceneController.switchScene("ProfileMenu/showTeamsMenu.fxml");
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
}
