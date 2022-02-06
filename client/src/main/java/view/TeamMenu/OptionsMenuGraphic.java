package view.TeamMenu;

import controller.TeamMenuController.ScoreBoardController;
import controller.TeamMenuController.TeamMenuController;
import javafx.event.ActionEvent;
import view.MenusFxml;
import view.SceneController;

import java.sql.SQLException;

public class OptionsMenuGraphic {
    public SceneController sceneController = new SceneController();
    public void profile(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SHOW_PROFILE_MENU.getLabel());
    }

    public void remove(ActionEvent actionEvent) throws SQLException {
        TeamMenuController.deleteMemberFromTeam(ScoreBoardController.usernameToRemove);
        sceneController.switchScene(MenusFxml.SCOREBOARD.getLabel());
    }

    public void back(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SCOREBOARD.getLabel());
    }
}
