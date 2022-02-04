package view;

import controller.LoginController;
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
            ArrayList<String> allTasksWithTaskId = DatabaseHandler.getTasksByUsername(LoginController.getActiveUser().getUsername());
            ArrayList<String> allTasks = null;
            for (String string : allTasksWithTaskId){
                String stringToAdd = (string.split(" "))[1];
                allTasks.add(stringToAdd);
            }
            ObservableList<String> items = FXCollections.observableArrayList(allTasksWithTaskId);
            tasksListView.setItems(items);
            tasksListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    String taskName = tasksListView.getSelectionModel().getSelectedItem();
                    //                        sceneController.switchScene(MenusFxml.SELECTED_TASK_MENU.getLabel());
                }
            });

            tasksSortChoiceBox.setOnAction((event) -> {
                Object value = tasksSortChoiceBox.getValue();
                if ("Priority".equals(value)) {
                    tasksListView.getItems().clear();
                    tasksListView.getItems().addAll(DatabaseHandler.sortTaskTitlesByPriority(allTasks));

                } else if ("Deadline".equals(value)) {
                    tasksListView.getItems().clear();
                    tasksListView.getItems().addAll(DatabaseHandler.sortTaskTitlesByDeadline(allTasks));

                } else if ("TaskTitle".equals(value)) {
                    tasksListView.getItems().clear();
                    tasksListView.getItems().addAll(DatabaseHandler.sortTaskTitlesByTaskTitle(allTasks));
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
        sceneController.switchScene(MenusFxml.MEMBER_MAIN_MENU.getLabel());
    }
}
