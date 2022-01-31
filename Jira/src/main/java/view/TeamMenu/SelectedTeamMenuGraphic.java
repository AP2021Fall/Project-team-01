package view.TeamMenu;

import controller.ProfileMenuController.ProfileMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import view.MenusFxml;
import view.SceneController;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SelectedTeamMenuGraphic implements Initializable {
    public TextArea textTeamMembers;
    public SceneController sceneController = new SceneController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
        textTeamMembers.setText("members");
    }

    public void goToRoadMap(ActionEvent actionEvent) {
    }

    public void goToChatroom(ActionEvent actionEvent) {
    }

    public void goToBoardMenu(ActionEvent actionEvent) {
    }

    public void goToMainMenu(ActionEvent actionEvent) {
    }

    public void goToScoreBoard(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SCOREBOARD.getLabel());
    }
}
