package view;

import appController.AppController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import models.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TasksMenuGraphic implements Initializable {
    public ObservableList<String> items = FXCollections.observableArrayList(AppController.getArraylistResult("DgetTasksByUsername " + User.getActiveUsername()));
    public TextField searchTextField;
    public ChoiceBox tasksSortChoiceBox;
    public ListView<String> tasksListView;
    public SceneController sceneController = new SceneController();

    public TasksMenuGraphic() throws SQLException, IOException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            tasksSortChoiceBox.getItems().addAll("Priority", "TaskTitle", "DeadLine");
            ArrayList<String> allTasks = AppController.getArraylistResult("DgetTasksByUsername " + User.getActiveUsername());
            ObservableList<String> items = FXCollections.observableArrayList(allTasks);
            tasksListView.setItems(items);
            tasksListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                    String taskName = tasksListView.getSelectionModel().getSelectedItem();
                    AppController.getResult("setTaskIdAndTaskTitle2 " + taskName + " " + User.getToken());
                        if (AppController.getResult("role " + User.getActiveUsername()).equals("leader")) {
                            sceneController.switchScene(MenusFxml.TASK_PAGE_LEADER.getLabel());
                            return;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    sceneController.switchScene(MenusFxml.TASK_PAGE.getLabel());
                }
            });

            tasksSortChoiceBox.setOnAction((event) -> {
                String value = (String) tasksSortChoiceBox.getValue();
                if ("Priority".equals(value)) {
                    tasksListView.getItems().clear();
                    try {
                        tasksListView.setItems(FXCollections.observableArrayList(AppController.getArraylistResult("DsortTaskTitlesByPriority " + User.getActiveUsername())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if ("DeadLine".equals(value)) {
                    tasksListView.getItems().clear();
                    try {
                        tasksListView.setItems(FXCollections.observableList(AppController.getArraylistResult("DsortTaskTitlesByDeadline " + User.getActiveUsername())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if ("TaskTitle".equals(value)) {
                    tasksListView.getItems().clear();
                    try {
                        tasksListView.setItems(FXCollections.observableList(AppController.getArraylistResult("DsortTaskTitlesByTaskTitle " + User.getActiveUsername())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
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

    public void goToMainMenu(ActionEvent actionEvent) throws IOException {
        if (LoginMenuGraphic.getRole(User.getActiveUsername()).equals("leader")) {
            sceneController.switchScene(MenusFxml.LEADER_MAIN_MENU.getLabel());
            return;
        }
        sceneController.switchScene(MenusFxml.MEMBER_MAIN_MENU.getLabel());
    }

    public void createNewTask(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.CREATE_NEW_TASK_MENU.getLabel());
    }

}
