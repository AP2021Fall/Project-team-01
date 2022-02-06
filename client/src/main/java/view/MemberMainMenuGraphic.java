package view;


import controller.LoginController;
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
        if(LoginController.getActiveUser().getRole().equals("leader"))
        sceneController.switchScene(MenusFxml.TASKS_MENU_LEADER.getLabel());
        else if (LoginController.getActiveUser().getRole().equals("member"))
            sceneController.switchScene(MenusFxml.TASKS_MENU.getLabel());
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


    public void notificationAdmin(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.ADMIN_SEND_TO.getLabel());
    }

    public void pendingTeams(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.PENDING_TEAMS.getLabel());
    }

    public void users(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.USERS.getLabel());
    }

    public void statistics(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.STATISTICS.getLabel());
    }
}
