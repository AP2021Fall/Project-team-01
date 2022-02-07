package view.TeamMenu;

import appController.AppController;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;
import models.User;
import view.LoginMenuGraphic;
import view.MenusFxml;
import view.SceneController;

import java.io.IOException;
import java.sql.SQLException;

public class SelectedTaskOptionsGraphic {
    public SceneController sceneController = new SceneController();
    public Text alert;

    public void details(ActionEvent actionEvent) {
        try {
            String taskTitle = AppController.getResult("GetSelectedTask " + User.getToken());
            String teamId = AppController.getResult("CurrentTeamId " + User.getToken());
            AppController.getResult("setTaskIdAndTaskTitle2 " + AppController.getResult("DgetTaskIdByTaskTitle " + taskTitle + " " + teamId) + " " + taskTitle + " " + User.getToken());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (LoginMenuGraphic.getRole(User.getActiveUsername()).equals("leader")) {
                sceneController.switchScene(MenusFxml.TASK_PAGE_LEADER.getLabel());
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        sceneController.switchScene(MenusFxml.TASK_PAGE.getLabel());
    }

    public void moveNext(ActionEvent actionEvent) throws SQLException, IOException {
        int teamId = Integer.parseInt(AppController.getResult("CurrentTeamId " + User.getToken()));
        String selectedTask = AppController.getResult("GetSelectedTask " + User.getToken());
        String activeBoard = AppController.getResult("GetActiveBoard " + User.getToken());
        String taskId = AppController.getResult("DgetTaskIdByTaskTitle " + selectedTask + " " + teamId);
        String bool = AppController.getResult("DisUsernameAssigned " + taskId + " " + User.getActiveUsername());
        if (bool.equals("n")) {
            alert.setText("This task is not aligned to you");
            return;
        }
        AppController.getResult("TaskToNext " + selectedTask + " " + activeBoard + " " + User.getToken());

        String teamName = AppController.getResult("CurrentTeamName " + User.getToken());
        AppController.getResult("setTeamChoice " + teamName + " " + User.getToken());
        AppController.getResult("sendToTeam " + "taskId: " + taskId + " moved next! " + User.getToken());
        sceneController.switchScene(MenusFxml.SELECTED_BOARD_MENU.getLabel());
    }

    public void forceCategory(ActionEvent actionEvent) throws SQLException, IOException {
        int teamId = Integer.parseInt(AppController.getResult("CurrentTeamId " + User.getToken()));
        String selectedTask = AppController.getResult("GetSelectedTask " + User.getToken());
        String taskId = AppController.getResult("DgetTaskIdByTaskTitle " + selectedTask + " " + teamId);
        String bool = AppController.getResult("DisUsernameAssigned " + taskId + " " + User.getActiveUsername());
        if (bool.equals("n")) {
            alert.setText("This task is not aligned to you");
        } else {
            sceneController.switchScene(MenusFxml.FORCE_TASK.getLabel());
        }

    }

    public void back(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SELECTED_BOARD_MENU.getLabel());
    }
}
