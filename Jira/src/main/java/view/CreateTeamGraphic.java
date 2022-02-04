package view;

import controller.MainMenuController;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class CreateTeamGraphic {

    public TextField teamName;
    public Label alert;
    public SceneController sceneController = new SceneController();

    public void back(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.LEADER_MAIN_MENU.getLabel());
    }

    public void create(ActionEvent actionEvent) throws SQLException {
        String team = teamName.getText();
        if (team.isEmpty()) {
            alert.setText("please fill our fields");
            return;
        }
        String result = MainMenuController.createTeam(team);
        alert.setText(result);
        if (result.equals("Team created successfully! Waiting For Admin’s confirmation…"))
            sceneController.switchScene(MenusFxml.LEADER_MAIN_MENU.getLabel());
    }
}
