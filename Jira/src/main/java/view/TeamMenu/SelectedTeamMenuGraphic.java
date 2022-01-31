package view.TeamMenu;

import controller.TeamMenuController.TeamMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import view.MenusFxml;
import view.SceneController;

import java.net.URL;
import java.util.ResourceBundle;

public class SelectedTeamMenuGraphic implements Initializable {
    public SceneController sceneController = new SceneController();

    public Label welcome;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        welcome.setText("Team: "+ TeamMenuController.getTeam().getName());
    }

    public void goToRoadMap(ActionEvent actionEvent) {
    }

    public void goToChatroom(ActionEvent actionEvent) {
    }

    public void goToBoardMenu(ActionEvent actionEvent) {
    }

    public void goToMainMenu(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.MEMBER_MAIN_MENU.getLabel());
    }

    public void goToScoreBoard(ActionEvent actionEvent) {
    }

    public void backToTeamMenu(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.TEAM_MENU.getLabel());
    }
}
