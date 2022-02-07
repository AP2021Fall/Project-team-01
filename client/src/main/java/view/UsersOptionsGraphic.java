package view;

import appController.AppController;
import javafx.event.ActionEvent;
import models.User;

import java.io.IOException;
import java.sql.SQLException;

public class UsersOptionsGraphic {
    public SceneController sceneController = new SceneController();

    public void profile(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SHOW_PROFILE_MENU.getLabel());
    }

    public void ban(ActionEvent actionEvent) throws SQLException, IOException {
        AppController.getResult("banUser " + User.getToken());
        sceneController.switchScene(MenusFxml.USERS.getLabel());
    }

    public void changeRole(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.NEW_ROLE.getLabel());
    }

    public void back(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.USERS.getLabel());
    }
}
