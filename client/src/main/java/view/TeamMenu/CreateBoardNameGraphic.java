package view.TeamMenu;

import controller.TeamMenuController.BoardMenuController;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import view.MenusFxml;
import view.SceneController;

import java.sql.SQLException;

public class CreateBoardNameGraphic {
    public Text alert;
    public TextField boardName;
    public SceneController sceneController = new SceneController();


    public void back(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.BOARD_MENU_L.getLabel());
    }

    public void next(ActionEvent actionEvent) throws SQLException {
        if (boardName.getText().isEmpty()){
            alert.setText("please fill out field");
            return;
        }
        String result = BoardMenuController.createBoard(boardName.getText());
        alert.setText(result);
        if (result.equals("board created")) {
            BoardMenuController.selectBoard(boardName.getText());
            sceneController.switchScene(MenusFxml.ADD_CATEGORY.getLabel());
        }
    }
}
