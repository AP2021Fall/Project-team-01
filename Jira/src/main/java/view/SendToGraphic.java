package view;

import controller.LoginController;
import controller.MainMenuController;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.DatabaseHandler;

import java.sql.SQLException;

public class SendToGraphic{
    public SceneController sceneController = new SceneController();
    public ChoiceBox choiceBox;
    public TextField getter;
    public Label alert;



    public void next(ActionEvent actionEvent) throws SQLException {
        String choice = (String) choiceBox.getValue();
        if (choice.equals("team")) {
            if (!DatabaseHandler.doesTeamExistForUser(getter.getText(), LoginController.getActiveUser().getUsername())){
                alert.setText("you do not have team with this name");
                return;
            }
            MainMenuController.team = getter.getText();
            MainMenuController.choice = 2;
            sceneController.switchScene(MenusFxml.SEND_NOTIFICATION.getLabel());
            return;
        }
        if (choice.equals("user")) {
            if (!DatabaseHandler.doesUsernameExist(getter.getText())) {
                alert.setText("invalid username");
                return;
            }
            MainMenuController.username = getter.getText();
            MainMenuController.choice = 1;
            sceneController.switchScene(MenusFxml.SEND_NOTIFICATION.getLabel());
            return;
        }
        if (choice.equals("all")) {
            MainMenuController.choice = 3;
            sceneController.switchScene(MenusFxml.SEND_NOTIFICATION.getLabel());
            return;
        }
    }

    public void back(ActionEvent actionEvent) {
        if (LoginController.getActiveUser().getRole().equals("leader"))
            sceneController.switchScene(MenusFxml.LEADER_MAIN_MENU.getLabel());
        else
            sceneController.switchScene(MenusFxml.ADMIN_MAIN_MENU.getLabel());
    }


}
