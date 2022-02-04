package view;


import javafx.scene.input.MouseEvent;

public class MemberMainMenuGraphic {
    public SceneController sceneController = new SceneController();

    public void profileMenu(MouseEvent mouseEvent) {
        sceneController.switchScene(MenusFxml.PROFILE_MENU.getLabel());
    }

    public void teamMenu(MouseEvent mouseEvent) {
        sceneController.switchScene(MenusFxml.TEAM_MENU.getLabel());
    }

    public void tasksMenu(MouseEvent mouseEvent) {
        sceneController.switchScene(MenusFxml.TASKS_MENU.getLabel());
    }

    public void calenderMenu(MouseEvent mouseEvent) {
        sceneController.switchScene(MenusFxml.CALENDAR_MENU.getLabel());
    }

    public void logout(MouseEvent mouseEvent) {
        sceneController.switchScene(MenusFxml.LOGIN_MENU.getLabel());
    }

}
