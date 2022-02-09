package view.TeamMenu;

import controller.LoginController;
import controller.ProfileMenuController.ProfileMenuController;
import controller.TeamMenuController.ScoreBoardController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import view.MenusFxml;
import view.SceneController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ShowProfileGraphic implements Initializable {

    public TextArea textMyProfile;
    public ImageView profileImage;
    public SceneController sceneController = new SceneController();

    public void goToScoreboard(ActionEvent actionEvent) {
        if (LoginController.getActiveUser().getRole().equals("admin"))
            sceneController.switchScene(MenusFxml.USERS.getLabel());
        else
            sceneController.switchScene(MenusFxml.SCOREBOARD.getLabel());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String path = "D:\\Project-team-01\\Jira\\src\\main\\resources\\images\\" + ScoreBoardController.usernameToRemove + ".png";
        File file = new File(path);
        if (file.exists()){
            try {
                InputStream inputStream = new FileInputStream(path);
                Image image = new Image(inputStream);
                profileImage.setImage(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        try {
            textMyProfile.setText(ProfileMenuController.showMyProfile(ScoreBoardController.usernameToRemove));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
