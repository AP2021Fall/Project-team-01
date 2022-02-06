package view;

import appController.AppController;
import controller.MainMenuController;
import controller.TeamMenuController.ScoreBoardController;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import models.User;

import java.io.IOException;
import java.sql.SQLException;

public class NewRoleGraphic {
    public SceneController sceneController = new SceneController();
    public ChoiceBox choiceBox;
    public Label alert;

    public void back(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.USERS_OPTIONS.getLabel());
    }

    public void next(ActionEvent actionEvent) throws SQLException, IOException {
        String choice = (String) choiceBox.getValue();
        AppController.getOutputStream().writeUTF("changeRole1 " + choice + " " + User.getToken());
        String result = MainMenuController.changeRole(ScoreBoardController.usernameToRemove, choice);
        alert.setText(result);
        if (result.equals("now enter a username to replace with this leader in team")) {
            sceneController.switchScene(MenusFxml.NEW_LEADER.getLabel());
        }
    }
}
