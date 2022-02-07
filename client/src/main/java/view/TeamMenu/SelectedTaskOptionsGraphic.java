package view.TeamMenu;

import controller.LoginController;
import controller.TeamMenuController.BoardMenuController;
import controller.TeamMenuController.TeamMenuController;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;
import view.MenusFxml;
import view.SceneController;

import java.sql.SQLException;

public class SelectedTaskOptionsGraphic {
    public SceneController sceneController = new SceneController();
    public Text alert;

    public void details(ActionEvent actionEvent) {
        //TODO task Page
    }

    public void moveNext(ActionEvent actionEvent) throws SQLException {
        if (!DatabaseHandler.isUsernameAssigned(DatabaseHandler.getTaskIdByTaskTitle(BoardMenuController.getSelectedTask(),
                TeamMenuController.getTeam().getId()), LoginController.getActiveUser().getUsername())) {
            alert.setText("This task is not aligned to you");
            return;
        }
        BoardMenuController.taskToNext(BoardMenuController.getSelectedTask(), BoardMenuController.getActiveBoard());
        sceneController.switchScene(MenusFxml.SELECTED_BOARD_MENU.getLabel());
    }

    public void forceCategory(ActionEvent actionEvent) throws SQLException {
        if (!DatabaseHandler.isUsernameAssigned(DatabaseHandler.getTaskIdByTaskTitle(BoardMenuController.getSelectedTask(),
                TeamMenuController.getTeam().getId()), LoginController.getActiveUser().getUsername())) {
            alert.setText("This task is not aligned to you");
        } else {
            sceneController.switchScene(MenusFxml.FORCE_TASK.getLabel());
        }

    }

    public void back(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SELECTED_BOARD_MENU.getLabel());
    }
}
