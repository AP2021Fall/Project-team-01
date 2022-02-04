package view;


import javafx.event.ActionEvent;
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
        sceneController.switchScene(MenusFxml.TASKS_MENU_LEADER.getLabel());
    }

    public void calenderMenu(MouseEvent mouseEvent) {
        sceneController.switchScene(MenusFxml.CALENDAR_MENU.getLabel());
    }

    public void logout(MouseEvent mouseEvent) {
        sceneController.switchScene(MenusFxml.LOGIN_MENU.getLabel());
    }

    public void Notification(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SEND_TO.getLabel());
    }

    public void createTeam(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.CREATE_TEAM.getLabel());
    }
}
