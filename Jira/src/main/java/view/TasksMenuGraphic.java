package view;

import controller.LoginController;
import controller.TasksPageController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import models.DatabaseHandler;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TasksMenuGraphic implements Initializable {
    public ObservableList<String> items = FXCollections.observableArrayList(DatabaseHandler.getTasksByUsername(LoginController.getActiveUser().getUsername()));
    public TextField searchTextField;
    public ChoiceBox tasksSortChoiceBox;
    public ListView<String> tasksListView;
    public SceneController sceneController = new SceneController();

    public TasksMenuGraphic() throws SQLException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            tasksSortChoiceBox.getItems().addAll("Priority", "TaskTitle", "DeadLine");
            ArrayList<String> allTasks = DatabaseHandler.getTasksByUsername(LoginController.getActiveUser().getUsername());
            ObservableList<String> items = FXCollections.observableArrayList(allTasks);
            tasksListView.setItems(items);
            tasksListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    String taskName = tasksListView.getSelectionModel().getSelectedItem();
                    TasksPageController.setTaskIdAndTaskTitle(taskName);
                    if (LoginController.getActiveUser().getRole().equals("leader")) {
                        sceneController.switchScene(MenusFxml.TASK_PAGE_LEADER.getLabel());
                        return;
                    }
                    sceneController.switchScene(MenusFxml.TASK_PAGE.getLabel());
                }
            });

            tasksSortChoiceBox.setOnAction((event) -> {
                Object value = tasksSortChoiceBox.getValue();
                if ("Priority".equals(value)) {
                    tasksListView.getItems().clear();
                    try {
                        tasksListView.getItems().addAll(DatabaseHandler.sortTaskTitlesByPriority(LoginController.getActiveUser().getUsername()));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                } else if ("Deadline".equals(value)) {
                    tasksListView.getItems().clear();
                    try {
                        tasksListView.getItems().addAll(DatabaseHandler.sortTaskTitlesByDeadline(LoginController.getActiveUser().getUsername()));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                } else if ("TaskTitle".equals(value)) {
                    tasksListView.getItems().clear();
                    try {
                        tasksListView.getItems().addAll(DatabaseHandler.sortTaskTitlesByTaskTitle(LoginController.getActiveUser().getUsername()));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void search(ActionEvent actionEvent) throws SQLException {
        tasksListView.getItems().clear();
        tasksListView.getItems().addAll(searchList(searchTextField.getText(), items));
    }

    private List<String> searchList(String searchWords, List<String> listOfStrings) {
        List<String> searchWordsArray = Arrays.asList(searchWords.trim());
        return listOfStrings.stream().filter(input -> { //input = test
            return searchWordsArray.stream().allMatch(word -> //word = te
                    input.contains(word));
        }).collect(Collectors.toList());
    }

    public void goToMainMenu(ActionEvent actionEvent) {
        if (LoginController.getActiveUser().getRole().equals("member"))
            sceneController.switchScene(MenusFxml.MEMBER_MAIN_MENU.getLabel());
        else if (LoginController.getActiveUser().getRole().equals("leader"))
            sceneController.switchScene(MenusFxml.LEADER_MAIN_MENU.getLabel());
        else if (LoginController.getActiveUser().getRole().equals("admin"))
            sceneController.switchScene(MenusFxml.ADMIN_MAIN_MENU.getLabel());
    }

    public void createNewTask(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.CREATE_NEW_TASK_MENU.getLabel());
    }

}
