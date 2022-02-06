package view;

import appController.AppController;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.SQLException;

public class PendingOptionsGraphics {
    public SceneController sceneController = new SceneController();

    public void accept(ActionEvent actionEvent) throws SQLException, IOException {
        AppController.getOutputStream().writeUTF("acceptTeams" );
        AppController.getOutputStream().flush();
        AppController.getInputStream().readUTF();
        sceneController.switchScene(MenusFxml.PENDING_TEAMS.getLabel());
    }

    public void reject(ActionEvent actionEvent) throws SQLException, IOException {
        AppController.getOutputStream().writeUTF("rejectTeams" );
        AppController.getOutputStream().flush();
        AppController.getInputStream().readUTF();
        sceneController.switchScene(MenusFxml.PENDING_TEAMS.getLabel());
    }

    public void back(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.PENDING_TEAMS.getLabel());
    }
}
