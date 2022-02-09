package view;

import controller.LoginController;
import controller.TeamMenuController.TeamMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.DatabaseHandler;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreateNewTaskMenuGraphic implements Initializable {
    public SceneController sceneController = new SceneController();
    public TextField taskTitle;
    public TextField taskCreationDate;
    public TextField taskDeadline;
    public ChoiceBox teamNameChoiceBox;
    public Label alert;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ArrayList<String> teamsOfLeader = DatabaseHandler.getUserTeams(LoginController.getActiveUser().getUsername());
            teamNameChoiceBox.getItems().addAll(teamsOfLeader);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //it should be leader main menu
    public void goToMainMenu(ActionEvent actionEvent) {
        if (LoginController.getActiveUser().getRole().equals("member"))
            sceneController.switchScene(MenusFxml.MEMBER_MAIN_MENU.getLabel());
        else if (LoginController.getActiveUser().getRole().equals("leader"))
            sceneController.switchScene(MenusFxml.LEADER_MAIN_MENU.getLabel());
        else if (LoginController.getActiveUser().getRole().equals("admin"))
            sceneController.switchScene(MenusFxml.ADMIN_MAIN_MENU.getLabel());
    }

    public void backToTasksMenu(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.TASKS_MENU.getLabel());
    }

    public void createNewTask(ActionEvent actionEvent) throws SQLException {
        String title = taskTitle.getText();
        String creationDate = taskCreationDate.getText();
        String deadline = taskDeadline.getText();
        String teamName = (String) teamNameChoiceBox.getValue();
        if (title.isEmpty() || creationDate.isEmpty() || deadline.isEmpty() || teamName.isEmpty()) {
            alert.setText("PLEASE FILL OUT ALL THE FIELDS!");
            return;
        }
        String result = TeamMenuController.createTask(title, creationDate, deadline, teamName);
        if (result.equals("task created successfully")){
            taskTitle.clear();
            taskDeadline.clear();
            taskCreationDate.clear();
        }
        alert.setText(result);
    }

}
