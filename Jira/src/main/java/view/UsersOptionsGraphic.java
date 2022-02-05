package view;

import controller.MainMenuController;
import controller.TeamMenuController.ScoreBoardController;
import javafx.event.ActionEvent;

import java.sql.SQLException;

public class UsersOptionsGraphic {
    public SceneController sceneController = new SceneController();

    public void profile(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SHOW_PROFILE_MENU.getLabel());
    }

    public void ban(ActionEvent actionEvent) throws SQLException {
        MainMenuController.banUser(ScoreBoardController.usernameToRemove);
        sceneController.switchScene(MenusFxml.USERS.getLabel());
    }

    public void changeRole(ActionEvent actionEvent) {

    }

    public void back(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.USERS.getLabel());
    }
}
