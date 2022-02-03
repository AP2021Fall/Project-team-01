package view.TeamMenu;

import controller.TeamMenuController.BoardMenuController;
import javafx.event.ActionEvent;
import view.MenusFxml;
import view.SceneController;

import java.sql.SQLException;

public class SelectedTaskOptionsGraphic {
    public SceneController sceneController = new SceneController();

    public void details(ActionEvent actionEvent) {
        //TODO task Page
    }

    public void moveNext(ActionEvent actionEvent) throws SQLException {
        BoardMenuController.taskToNext(BoardMenuController.getSelectedTask(), BoardMenuController.getActiveBoard());
        sceneController.switchScene(MenusFxml.SELECTED_BOARD_MENU.getLabel());
    }

    public void forceCategory(ActionEvent actionEvent) {

    }

    public void back(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SELECTED_BOARD_MENU.getLabel());
    }
}
