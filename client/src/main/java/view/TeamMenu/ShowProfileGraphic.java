package view.TeamMenu;

import appController.AppController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import models.User;
import view.LoginMenuGraphic;
import view.MenusFxml;
import view.SceneController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShowProfileGraphic implements Initializable {

    public TextArea textMyProfile;
    public ImageView profileImage;
    public SceneController sceneController = new SceneController();

    public void goToScoreboard(ActionEvent actionEvent) throws IOException {
        if (LoginMenuGraphic.getRole(User.getActiveUsername()).equals("admin"))
            sceneController.switchScene(MenusFxml.USERS.getLabel());
        else
            sceneController.switchScene(MenusFxml.SCOREBOARD.getLabel());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            String usernameToRemove = AppController.getResult("UsernameToRemove " + User.getToken());
            textMyProfile.setText(AppController.getResult("ShowProfile " + usernameToRemove));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
