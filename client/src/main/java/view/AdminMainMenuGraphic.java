package view;

import javafx.event.ActionEvent;

public class AdminMainMenuGraphic {
    public SceneController sceneController = new SceneController();

    public void users(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.USERS.getLabel());
    }

    public void pendingTeam(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.PENDING_TEAMS.getLabel());
    }

    public void banUsers(ActionEvent actionEvent) {
    }

    public void information(ActionEvent actionEvent) {
    }

    public void logout(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.LOGIN_MENU.getLabel());
    }
}
