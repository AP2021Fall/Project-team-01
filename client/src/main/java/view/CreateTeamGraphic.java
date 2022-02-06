package view;

import appController.AppController;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.User;

import java.io.IOException;
import java.sql.SQLException;

public class CreateTeamGraphic {

    public TextField teamName;
    public Label alert;
    public SceneController sceneController = new SceneController();

    public void back(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.LEADER_MAIN_MENU.getLabel());
    }

    public void create(ActionEvent actionEvent) throws SQLException, IOException {
        String team = teamName.getText();
        if (team.isEmpty()) {
            alert.setText("please fill our fields");
            return;
        }
        AppController.getOutputStream().writeUTF("createTeam " + team + " " + User.getToken());
        AppController.getOutputStream().flush();
        String result = AppController.getInputStream().readUTF();
        alert.setText(result);
        if (result.equals("Team created successfully! Waiting For Admin’s confirmation…"))
            sceneController.switchScene(MenusFxml.LEADER_MAIN_MENU.getLabel());
    }
}
