package view.TeamMenu;

import appController.AppController;
import javafx.event.ActionEvent;
import models.User;
import view.MenusFxml;
import view.SceneController;

import java.io.IOException;
import java.sql.SQLException;

public class OptionsMenuGraphic {
    public SceneController sceneController = new SceneController();
    public void profile(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SHOW_PROFILE_MENU.getLabel());
    }

    public void remove(ActionEvent actionEvent) throws SQLException, IOException {
        String usernameToRemove = AppController.getResult("UsernameToRemove " + User.getToken());
        AppController.getResult("DeleteMemberFromTeam " + usernameToRemove + " " + User.getToken());
        sceneController.switchScene(MenusFxml.SCOREBOARD.getLabel());
    }

    public void back(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SCOREBOARD.getLabel());
    }
}
