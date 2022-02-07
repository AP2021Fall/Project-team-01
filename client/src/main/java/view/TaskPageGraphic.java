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
import java.util.ResourceBundle;

public class TaskPageGraphic implements Initializable {
    public Label taskInfoLabel;
    public Label currentTitle;
    public TextField newTitleTextField;
    public Label editTitleAlert;
    public Label currentDescription;
    public TextField newDescriptionTextField;
    public Label editDescriptionAlert;
    public Label currentPriority;
    public Label editPriorityAlert;
    public ChoiceBox newPriorityChoiceBox;
    public Label currentDeadline;
    public TextField newDeadlineTextField;
    public Label editDeadlineAlert;
    public Label currentAssignedUsers;
    public Label addAssignedUserAlert;
    public ChoiceBox assignUserChoiceBox;
    public Label removeAssignedUserAlert;
    public ChoiceBox removeAssignedUserChoiceBox;
    private int taskId = Integer.parseInt(AppController.getResult("taskIdTaskPage " + User.getToken()));
    SceneController sceneController = new SceneController();

    public TaskPageGraphic() throws IOException {
    }

    public void goToMainMenu(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.MEMBER_MAIN_MENU.getLabel());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            String task = AppController.getResult("taskIdTaskPage " + User.getToken());
            int taskId = Integer.parseInt(task);
            if (taskInfoLabel != null) {
                taskInfoLabel.setText("Task Id: " + taskId + "\nTask Title: " + AppController.getResult("DgetTaskTitleByTaskId " + taskId) +
                        "\nTask Priority: " + AppController.getResult("DgetTaskPriorityByTaskId " + taskId) +
                        "\nTask Description: " + AppController.getResult("DgetTaskDescriptionByTaskId " + taskId) +
                        "\nTask Creation Time: " + AppController.getResult("DgetTaskCreationTimeByTaskId " + taskId) +
                        "\nTask Deadline: " + AppController.getResult("DgetTaskDeadlineByTaskId " + taskId) +
                        "\nTask Comments: " + AppController.getArraylistResult("DgetTaskCommentsByTaskId " + taskId).toString() +
                        "\nTask Assigned Users: " + AppController.getArraylistResult("DgetTaskAssignedUsersByTaskId " + taskId).toString());
            }
            if (currentTitle != null)
                currentTitle.setText(AppController.getResult("DgetTaskPriorityByTaskId " + taskId));
            if (currentDescription != null)
                currentDescription.setText(AppController.getResult("DgetTaskDescriptionByTaskId " + taskId));
            if (assignUserChoiceBox != null) {
                currentAssignedUsers.setText(AppController.getArraylistResult("DgetTaskAssignedUsersByTaskId " + taskId).toString());
                assignUserChoiceBox.getItems().addAll(AppController.getArraylistResult("DgetMembersByTeamName " + AppController.getResult("getTeamNameByTeamId " + AppController.getResult("getTeamIdByTaskId " + taskId))));
            }
            if (currentDeadline != null)
                currentDeadline.setText(AppController.getResult("DgetTaskDeadlineByTaskId " + taskId));
            if (currentPriority != null) {
                newPriorityChoiceBox.getItems().addAll("1", "2", "3", "4");
                currentPriority.setText(AppController.getResult("DgetTaskPriorityByTaskId " + taskId));
            }
            if (removeAssignedUserAlert != null) {
                currentAssignedUsers.setText(AppController.getArraylistResult("DgetTaskAssignedUsersByTaskId " + taskId).toString());
                removeAssignedUserChoiceBox.getItems().addAll(AppController.getArraylistResult("DgetMembersByTeamName " + AppController.getResult("getTeamNameByTeamId " + AppController.getResult("getTeamIdByTaskId " + taskId))));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void backToTaskPageLeader(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.TASK_PAGE_LEADER.getLabel());
    }

    public void submitEditTaskTitle(ActionEvent actionEvent) throws SQLException, IOException {
        if (newTitleTextField.getText().isEmpty()){
            editTitleAlert.setText("PLEASE FILL OUT THE FIELD!");
            return;
        }
        String result = AppController.getResult("editTitle " + newTitleTextField.getText() + " " + User.getToken());
        editTitleAlert.setText(result);
    }

    public void goToEditTitleMenu(ActionEvent actionEvent) throws SQLException {
        sceneController.switchScene(MenusFxml.EDIT_TITLE_TASK.getLabel());
    }

    public void submitEditTaskDescription(ActionEvent actionEvent) throws SQLException, IOException {
        if (newDescriptionTextField.getText().isEmpty()){
            editDescriptionAlert.setText("PLEASE FILL OUT THE FIELD!");
            return;
        }
        String result = AppController.getResult("editDescription " + newDescriptionTextField.getText() + " " + User.getToken());
        editDescriptionAlert.setText(result);
    }

    public void goToEditDescriptionMenu(ActionEvent actionEvent) throws SQLException {
        sceneController.switchScene(MenusFxml.EDIT_DESCRIPTION_TASK.getLabel());
    }

    public void submitEditTaskPriority(ActionEvent actionEvent) throws SQLException, IOException {
        if (((String)newPriorityChoiceBox.getValue()).isEmpty()) {
            editPriorityAlert.setText("PLEASE FILL OUT THE FIELD!");
            return;
        }
        String priority = (String) newPriorityChoiceBox.getValue();
        String result = AppController.getResult("editPriority " + priority + " " + User.getToken());
        editPriorityAlert.setText(result);
    }

    public void goToEditPriorityMenu(ActionEvent actionEvent) throws SQLException {
        sceneController.switchScene(MenusFxml.EDIT_PRIORITY_TASK.getLabel());
    }

    public void submitEditTaskDeadline(ActionEvent actionEvent) throws SQLException, IOException {
        if (newDeadlineTextField.getText().isEmpty()){
            editDeadlineAlert.setText("PLEASE FILL OUT THE FIELD!");
            return;
        }
        String result = AppController.getResult("editDeadLine " + newDeadlineTextField.getText() + " " + User.getToken());
        editDeadlineAlert.setText(result);
    }

    public void goToEditDeadlineMenu(ActionEvent actionEvent) throws SQLException {
        sceneController.switchScene(MenusFxml.EDIT_DEADLINE_TASK.getLabel());
    }

    public void submitTaskAssignUser(ActionEvent actionEvent) throws SQLException, IOException {
        if (((String)assignUserChoiceBox.getValue()).isEmpty()){
            addAssignedUserAlert.setText("PLEASE CHOOSE THE USER!");
            return;
        }
        String assigned = (String) assignUserChoiceBox.getValue();
        String result = AppController.getResult("editAssigned " + assigned + " " + User.getToken());
        addAssignedUserAlert.setText(result);
    }

    public void goToAddAssignedUserMenu(ActionEvent actionEvent) throws SQLException {
        sceneController.switchScene(MenusFxml.ADD_ASSIGNED_USER_TASK.getLabel());
    }


    public void submitTaskRemoveAssignedUser(ActionEvent actionEvent) throws SQLException, IOException {
        if (((String)removeAssignedUserChoiceBox.getValue()).isEmpty()){
            removeAssignedUserAlert.setText("PLEASE CHOOSE THE USER!");
            return;
        }
        String assigned = (String) removeAssignedUserChoiceBox.getValue();
        String result = AppController.getResult("editRemoveAssigned " + assigned + " " + User.getToken());
        removeAssignedUserAlert.setText(result);
    }

    public void goToRemoveAssignedUserMenu(ActionEvent actionEvent) throws SQLException {
        sceneController.switchScene(MenusFxml.REMOVE_ASSIGNED_USER_TASK.getLabel());
    }

}
