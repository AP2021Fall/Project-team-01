package view.TeamMenu;

import appController.AppController;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import models.User;
import view.MenusFxml;
import view.SceneController;

import java.io.IOException;
import java.sql.SQLException;

public class CreateBoardNameGraphic {
    public Text alert;
    public TextField boardName;
    public SceneController sceneController = new SceneController();


    public void back(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.BOARD_MENU_L.getLabel());
    }

    public void next(ActionEvent actionEvent) throws SQLException, IOException {
        if (boardName.getText().isEmpty()){
            alert.setText("please fill out field");
            return;
        }
        String result = AppController.getResult("CreateBoard " + boardName.getText() + " " + User.getToken());
        alert.setText(result);
        if (result.equals("board created")) {
            AppController.getResult("SelectBoard " + boardName.getText() + " " + User.getToken());
            sceneController.switchScene(MenusFxml.ADD_CATEGORY.getLabel());
        }
    }
}
