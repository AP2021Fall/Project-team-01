package view.TeamMenu;

import controller.TeamMenuController.BoardMenuController;
import controller.TeamMenuController.TeamMenuController;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.DatabaseHandler;
import view.MenusFxml;
import view.SceneController;

import java.sql.SQLException;

public class AddCategoryToBoard {

    public TextField categoryName;
    public TextField column;
    public Label alert;
    public SceneController sceneController = new SceneController();

    public void add(ActionEvent actionEvent) throws SQLException {
        String category = categoryName.getText();
        if (column.getText().isEmpty()) {
            String result = BoardMenuController.addCategorySelect(category);
            alert.setText(result);
            return;
        }
        String result = BoardMenuController.addCategoryToColumnSelect(category, column.getText());
        alert.setText(result);
    }

    public void back(ActionEvent actionEvent) throws SQLException {
        DatabaseHandler.removeBoard(BoardMenuController.getActiveBoard(), TeamMenuController.getTeam().getId());
        sceneController.switchScene(MenusFxml.CREATE_BOARD_NAME.getLabel());
    }

    public void next(ActionEvent actionEvent) throws SQLException {
        String result = BoardMenuController.completeBoardFirstStepSelect();
        alert.setText(result);
        if (result.equals("first step completed"))
            sceneController.switchScene(MenusFxml.BOARD_MENU_L.getLabel());
    }
}
