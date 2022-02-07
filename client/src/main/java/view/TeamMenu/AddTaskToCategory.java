package view.TeamMenu;

import appController.AppController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import models.User;
import view.MenusFxml;
import view.SceneController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddTaskToCategory implements Initializable {
    public ListView listView;
    public SceneController sceneController = new SceneController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> items = null;
        try {
            int teamId = Integer.parseInt(AppController.getResult("CurrentTeamId " + User.getToken()));
            String selectedTask = AppController.getResult("GetSelectedTask " + User.getToken());
            String activeBoard = AppController.getResult("GetActiveBoard " + User.getToken());
            items = FXCollections.observableArrayList (AppController.getArraylistResult("DgetCategories " + activeBoard + " " + teamId));
            listView.setItems(items);
            listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    String category = (String) listView.getSelectionModel().getSelectedItem();
                    try {
                        int taskId = Integer.parseInt(AppController.getResult("DgetTaskIdByTaskTitle " + selectedTask + " " + teamId));
                        AppController.getResult("DaddTaskToBoard " + taskId + " " + activeBoard);
                        AppController.getResult("DaddToCategory " + category + " " + selectedTask + " " + activeBoard + " " + teamId);
                        sceneController.switchScene(MenusFxml.SELECTED_BOARD_MENU.getLabel());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void back(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.ADD_TASK_TO_BOARD.getLabel());
    }
}
