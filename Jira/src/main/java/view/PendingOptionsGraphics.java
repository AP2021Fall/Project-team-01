package view;

import controller.MainMenuController;
import javafx.event.ActionEvent;

import java.sql.SQLException;

public class PendingOptionsGraphics {
    public SceneController sceneController = new SceneController();

    public void accept(ActionEvent actionEvent) throws SQLException {
        MainMenuController.acceptTeams(MainMenuController.pendingTeam.split("      "));
        sceneController.switchScene(MenusFxml.PENDING_TEAMS.getLabel());
    }

    public void reject(ActionEvent actionEvent) throws SQLException {
        MainMenuController.rejectTeams(MainMenuController.pendingTeam.split("      "));
        sceneController.switchScene(MenusFxml.PENDING_TEAMS.getLabel());
    }

    public void back(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.PENDING_TEAMS.getLabel());
    }
}
