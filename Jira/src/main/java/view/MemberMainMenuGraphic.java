package view;


import javafx.scene.input.MouseEvent;

public class MemberMainMenuGraphic {
    public SceneController sceneController = new SceneController();

    public void profileMenu(MouseEvent mouseEvent) {
        sceneController.switchScene("ProfileMenu/profileMenu.fxml");
    }

    public void teamMenu(MouseEvent mouseEvent) {
        sceneController.switchScene("ProfileMenu/showTeamsMenu.fxml");
    }

    public void taskPage(MouseEvent mouseEvent) {

    }

    public void calenderMenu(MouseEvent mouseEvent) {

    }

    public void logout(MouseEvent mouseEvent) {
        sceneController.switchScene(MenusFxml.LOGIN_MENU.getLabel());
    }

}
