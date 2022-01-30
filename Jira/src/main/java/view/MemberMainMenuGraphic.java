package view;


import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MemberMainMenuGraphic {
    public static Stage stage;
    public SceneController sceneController = new SceneController();


    public void profileMenu(MouseEvent mouseEvent) {

    }

    public void teamMenu(MouseEvent mouseEvent) {

    }

    public void taskPage(MouseEvent mouseEvent) {

    }

    public void calenderMenu(MouseEvent mouseEvent) {

    }

    public void logout(MouseEvent mouseEvent) {
        sceneController.switchScene(MenusFxml.LOGIN_MENU.getLabel());
    }
}
