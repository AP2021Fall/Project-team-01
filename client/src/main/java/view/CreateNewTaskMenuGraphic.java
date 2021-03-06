package view;

import appController.AppController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.User;

import java.io.IOException;
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
            ArrayList<String> teamsOfLeader = AppController.getArraylistResult("DgetUserTeams " + User.getActiveUsername());
            teamNameChoiceBox.getItems().addAll(teamsOfLeader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToMainMenu(ActionEvent actionEvent) throws IOException {
        sceneController.switchScene(MenusFxml.BOARD_MENU_L.getLabel());
    }

    public void backToTasksMenu(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.TASKS_MENU_LEADER.getLabel());
    }

    public void createNewTask(ActionEvent actionEvent) throws SQLException, IOException {
        String title = taskTitle.getText();
        String creationDate = taskCreationDate.getText();
        String deadline = taskDeadline.getText();
        String teamName = (String) teamNameChoiceBox.getValue();
        if (title.isEmpty() || creationDate.isEmpty() || deadline.isEmpty() || teamName.isEmpty()) {
            alert.setText("PLEASE FILL OUT ALL THE FIELDS!");
            return;
        }
        AppController.getOutputStream().writeUTF("createTask " + title + " " + creationDate + " " + deadline + " " + teamName);
        AppController.getOutputStream().flush();
        String result = AppController.getInputStream().readUTF();
        if (result.equals("task created successfully")) {
            taskTitle.clear();
            taskDeadline.clear();
            taskCreationDate.clear();
        }
        alert.setText(result);
    }

}
