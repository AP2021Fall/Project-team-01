package view;

import appController.AppController;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.User;

import java.io.IOException;
import java.sql.SQLException;

public class SendToGraphic{
    public SceneController sceneController = new SceneController();
    public ChoiceBox choiceBox;
    public TextField getter;
    public Label alert;



    public void next(ActionEvent actionEvent) throws SQLException, IOException {
        String choice = (String) choiceBox.getValue();
        if (choice.equals("team")) {
            String result = AppController.getResult("DdoesTeamExistForUser " + getter.getText() + " " + User.getActiveUsername());
            if (result.equals("n")){
                alert.setText("you do not have team with this name");
                return;
            }
            AppController.getResult("setTeamChoice " + getter.getText() + " " + User.getToken());
            sceneController.switchScene(MenusFxml.SEND_NOTIFICATION.getLabel());
            return;
        }
        if (choice.equals("user")) {
            String result = AppController.getResult("DdoesUsernameExist " + getter.getText());
            if (result.equals("n")) {
                alert.setText("invalid username");
                return;
            }
            AppController.getResult("setUserChoice " + getter.getText() + " " + User.getToken());
            sceneController.switchScene(MenusFxml.SEND_NOTIFICATION.getLabel());
            return;
        }
        if (choice.equals("all")) {
            AppController.getResult("setChoice " + User.getToken());
            sceneController.switchScene(MenusFxml.SEND_NOTIFICATION.getLabel());
            return;
        }
    }

    public void back(ActionEvent actionEvent) throws IOException {
        String role = AppController.getResult("role " + User.getActiveUsername());
        if (role.equals("leader"))
            sceneController.switchScene(MenusFxml.LEADER_MAIN_MENU.getLabel());
        else
            sceneController.switchScene(MenusFxml.ADMIN_MAIN_MENU.getLabel());
    }


}
