package view;

import controller.MainMenuController;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class NewLeaderGraphic {
    public TextField newLeader;
    public SceneController sceneController = new SceneController();

    public Label alert;

    public void back(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.USERS_OPTIONS.getLabel());
    }

    public void next(ActionEvent actionEvent) throws SQLException {
        if (newLeader.getText().isEmpty()) {
            alert.setText("please fill out fields");
            return;
        }
        String result = MainMenuController.changeRoleToMember(newLeader.toString());
        alert.setText(result);
        if (result.equals("Roles changed successfully!"))
            sceneController.switchScene(MenusFxml.USERS_OPTIONS.getLabel());
    }
}
