package view.TeamMenu;

import controller.ProfileMenuController.ProfileMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import view.MenusFxml;
import view.SceneController;

import java.net.URL;
import java.util.ResourceBundle;

public class ShowProfileGraphic implements Initializable {

    public TextArea textMyProfile;
    public ImageView profileImage;
    public SceneController sceneController = new SceneController();

    public void goToScoreboard(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SCOREBOARD.getLabel());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textMyProfile.setText(ProfileMenuController.showMyProfile());
    }
}
