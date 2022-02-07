package view.TeamMenu;

import appController.AppController;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.User;
import view.MenusFxml;
import view.SceneController;

import java.io.IOException;
import java.sql.SQLException;

public class AddCategoryToBoard {

    public TextField categoryName;
    public TextField column;
    public Label alert;
    public SceneController sceneController = new SceneController();

    public void add(ActionEvent actionEvent) throws SQLException, IOException {
        String category = categoryName.getText();
        if (column.getText().isEmpty()) {
            String result = AppController.getResult("AddCategorySelect " + category + " " + User.getToken());
            alert.setText(result);
            return;
        }
        String result = AppController.getResult("AddCategoryToColumnSelect " + category + " " + column.getText() + " " + User.getToken());
        alert.setText(result);
    }

    public void back(ActionEvent actionEvent) throws SQLException, IOException {
        int teamId = Integer.parseInt(AppController.getResult("CurrentTeamId " + User.getToken()));
        String activeBoard = AppController.getResult("GetActiveBoard " + User.getActiveUsername());
        AppController.getResult("DremoveBoard " + activeBoard + " " + teamId);
        sceneController.switchScene(MenusFxml.CREATE_BOARD_NAME.getLabel());
    }

    public void next(ActionEvent actionEvent) throws SQLException, IOException {
        String result = AppController.getResult("CompleteBoardFirstStepSelect " + User.getToken());
        alert.setText(result);
        if (result.equals("first step completed"))
            sceneController.switchScene(MenusFxml.BOARD_MENU_L.getLabel());
    }
}
