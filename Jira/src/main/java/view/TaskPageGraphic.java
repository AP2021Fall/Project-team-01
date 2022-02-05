package view;

import controller.TasksPageController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.DatabaseHandler;

import javax.xml.crypto.Data;
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
    SceneController sceneController = new SceneController();

    public void goToMainMenu(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.MEMBER_MAIN_MENU.getLabel());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int taskId = TasksPageController.getTaskId();
        String taskTitle = TasksPageController.getTaskTitle();
        try {
            if (taskInfoLabel != null) {
                taskInfoLabel.setText("Task Id: " + taskId + "\nTask Title: " + DatabaseHandler.getTaskTitleByTaskId(taskId) +
                        "\nTask Priority: " + DatabaseHandler.getTaskPriorityByTaskId(taskId) +
                        "\nTask Description: " + DatabaseHandler.getTaskDescriptionByTaskId(taskId) +
                        "\nTask Creation Time: " + DatabaseHandler.getTaskCreationTimeByTaskId(taskId) +
                        "\nTask Deadline: " + DatabaseHandler.getTaskDeadlineByTaskId(taskId) +
                        "\nTask Comments: " + DatabaseHandler.getTaskCommentsByTaskId(taskId).toString() +
                        "\nTask Assigned Users: " + DatabaseHandler.getTaskAssignedUsersByTaskId(taskId).toString());
            }
            if (currentTitle != null)
                currentTitle.setText(DatabaseHandler.getTaskPriorityByTaskId(TasksPageController.getTaskId()));
            if (currentDescription != null)
                currentDescription.setText(DatabaseHandler.getTaskDescriptionByTaskId(TasksPageController.getTaskId()));
            if (assignUserChoiceBox != null) {
                currentAssignedUsers.setText(DatabaseHandler.getTaskAssignedUsersByTaskId(TasksPageController.getTaskId()).toString());
                assignUserChoiceBox.getItems().addAll(DatabaseHandler.getMembersByTeamName(DatabaseHandler.getTeamNameByTeamId(DatabaseHandler.getTeamIdByTaskId(TasksPageController.getTaskId()))));
            }
            if (currentDeadline != null)
                currentDeadline.setText(DatabaseHandler.getTaskDeadlineByTaskId(TasksPageController.getTaskId()));
            if (currentPriority != null) {
                newPriorityChoiceBox.getItems().addAll("1", "2", "3", "4");
                currentPriority.setText(DatabaseHandler.getTaskPriorityByTaskId(TasksPageController.getTaskId()));
            }
            if (removeAssignedUserAlert != null) {
                currentAssignedUsers.setText(DatabaseHandler.getTaskAssignedUsersByTaskId(TasksPageController.getTaskId()).toString());
                removeAssignedUserChoiceBox.getItems().addAll(DatabaseHandler.getMembersByTeamName(DatabaseHandler.getTeamNameByTeamId(DatabaseHandler.getTeamIdByTaskId(TasksPageController.getTaskId()))));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void backToTaskPageLeader(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.TASK_PAGE_LEADER.getLabel());
    }

    public void submitEditTaskTitle(ActionEvent actionEvent) throws SQLException {
        if (newTitleTextField.getText().isEmpty()){
            editTitleAlert.setText("PLEASE FILL OUT THE FIELD!");
            return;
        }
        String result = TasksPageController.editTitle(TasksPageController.getTaskId(), newTitleTextField.getText());
        editTitleAlert.setText(result);
    }

    public void goToEditTitleMenu(ActionEvent actionEvent) throws SQLException {
        sceneController.switchScene(MenusFxml.EDIT_TITLE_TASK.getLabel());
    }

    public void submitEditTaskDescription(ActionEvent actionEvent) throws SQLException {
        if (newDescriptionTextField.getText().isEmpty()){
            editDescriptionAlert.setText("PLEASE FILL OUT THE FIELD!");
            return;
        }
        String result = TasksPageController.editDescription(TasksPageController.getTaskId(), newDescriptionTextField.getText());
        editDescriptionAlert.setText(result);
    }

    public void goToEditDescriptionMenu(ActionEvent actionEvent) throws SQLException {
        sceneController.switchScene(MenusFxml.EDIT_DESCRIPTION_TASK.getLabel());
    }

    public void submitEditTaskPriority(ActionEvent actionEvent) throws SQLException {
        if (((String)newPriorityChoiceBox.getValue()).isEmpty()){
            editPriorityAlert.setText("PLEASE FILL OUT THE FIELD!");
            return;
        }
        String result = TasksPageController.editPriority(TasksPageController.getTaskId(), Integer.parseInt((String) newPriorityChoiceBox.getValue()));
        editPriorityAlert.setText(result);
    }

    public void goToEditPriorityMenu(ActionEvent actionEvent) throws SQLException {
        sceneController.switchScene(MenusFxml.EDIT_PRIORITY_TASK.getLabel());
    }

    public void submitEditTaskDeadline(ActionEvent actionEvent) throws SQLException {
        if (newDeadlineTextField.getText().isEmpty()){
            editDeadlineAlert.setText("PLEASE FILL OUT THE FIELD!");
            return;
        }
        String result = TasksPageController.editDeadline(TasksPageController.getTaskId(), newDeadlineTextField.getText());
        editDeadlineAlert.setText(result);
    }

    public void goToEditDeadlineMenu(ActionEvent actionEvent) throws SQLException {
        sceneController.switchScene(MenusFxml.EDIT_DEADLINE_TASK.getLabel());
    }

    public void submitTaskAssignUser(ActionEvent actionEvent) throws SQLException {
        if (((String)assignUserChoiceBox.getValue()).isEmpty()){
            addAssignedUserAlert.setText("PLEASE CHOOSE THE USER!");
            return;
        }
        String result = TasksPageController.addAssignedUser(TasksPageController.getTaskId(), (String) assignUserChoiceBox.getValue());
        addAssignedUserAlert.setText(result);
    }

    public void goToAddAssignedUserMenu(ActionEvent actionEvent) throws SQLException {
        sceneController.switchScene(MenusFxml.ADD_ASSIGNED_USER_TASK.getLabel());
    }


    public void submitTaskRemoveAssignedUser(ActionEvent actionEvent) throws SQLException {
        if (((String)removeAssignedUserChoiceBox.getValue()).isEmpty()){
            removeAssignedUserAlert.setText("PLEASE CHOOSE THE USER!");
            return;
        }
        String result = TasksPageController.removeAssignedUser(TasksPageController.getTaskId(), (String) removeAssignedUserChoiceBox.getValue());
        removeAssignedUserAlert.setText(result);
    }

    public void goToRemoveAssignedUserMenu(ActionEvent actionEvent) throws SQLException {
        sceneController.switchScene(MenusFxml.REMOVE_ASSIGNED_USER_TASK.getLabel());
    }

}
