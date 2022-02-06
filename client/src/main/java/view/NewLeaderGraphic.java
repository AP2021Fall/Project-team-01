package view;

import appController.AppController;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.User;

import java.io.IOException;
import java.sql.SQLException;

public class NewLeaderGraphic {
    public TextField newLeader;
    public SceneController sceneController = new SceneController();

    public Label alert;

    public void back(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.USERS_OPTIONS.getLabel());
    }

    public void next(ActionEvent actionEvent) throws SQLException, IOException {
        if (newLeader.getText().isEmpty()) {
            alert.setText("please fill out fields");
            return;
        }
        AppController.getOutputStream().writeUTF("changeRole " + newLeader.getText() + " " + User.getToken());
        AppController.getOutputStream().flush();
        String result = AppController.getInputStream().readUTF();
        alert.setText(result);
        if (result.equals("Roles changed successfully!"))
            sceneController.switchScene(MenusFxml.USERS_OPTIONS.getLabel());
    }
}
