package view.TeamMenu;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import view.MenusFxml;
import view.SceneController;

public class CreateBoardNameGraphic {
    public Text alert;
    public TextField boardName;
    public SceneController sceneController = new SceneController();


    public void back(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.BOARD_MENU_L.getLabel());
    }

    public void next(ActionEvent actionEvent) {

    }
}
