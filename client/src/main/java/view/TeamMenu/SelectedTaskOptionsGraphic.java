package view.TeamMenu;

import appController.AppController;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;
import models.DatabaseHandler;
import models.User;
import view.MenusFxml;
import view.SceneController;

import java.io.IOException;
import java.sql.SQLException;

public class SelectedTaskOptionsGraphic {
    public SceneController sceneController = new SceneController();
    public Text alert;

    public void details(ActionEvent actionEvent) {
        //TODO task Page
    }

    public void moveNext(ActionEvent actionEvent) throws SQLException, IOException {
        int teamId = Integer.parseInt(AppController.getResult("CurrentTeamId " + User.getToken()));
        String selectedTask = AppController.getResult("GetSelectedTask " + User.getToken());
        String activeBoard = AppController.getResult("GetActiveBoard " + User.getToken());
        if (!DatabaseHandler.isUsernameAssigned(DatabaseHandler.getTaskIdByTaskTitle(selectedTask,
                teamId), User.getActiveUsername())) {
            alert.setText("This task is not aligned to you");
            return;
        }
        AppController.getResult("TaskToNext " + selectedTask + " " + activeBoard + " " + User.getToken());
        sceneController.switchScene(MenusFxml.SELECTED_BOARD_MENU.getLabel());
    }

    public void forceCategory(ActionEvent actionEvent) throws SQLException, IOException {
        int teamId = Integer.parseInt(AppController.getResult("CurrentTeamId " + User.getToken()));
        String selectedTask = AppController.getResult("GetSelectedTask " + User.getToken());
        if (!DatabaseHandler.isUsernameAssigned(DatabaseHandler.getTaskIdByTaskTitle(selectedTask,
                teamId), User.getActiveUsername())) {
            alert.setText("This task is not aligned to you");
        } else {
            sceneController.switchScene(MenusFxml.FORCE_TASK.getLabel());
        }

    }

    public void back(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SELECTED_BOARD_MENU.getLabel());
    }
}
