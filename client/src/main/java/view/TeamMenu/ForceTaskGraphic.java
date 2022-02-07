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

public class ForceTaskGraphic implements Initializable {

    public ListView categories;
    public SceneController sceneController = new SceneController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            int teamId = Integer.parseInt(AppController.getResult("CurrentTeamId " + User.getToken()));
            String selectedTask = AppController.getResult("GetSelectedTask " + User.getToken());
            String activeBoard = AppController.getResult("GetActiveBoard " + User.getToken());
            ObservableList<String> items = FXCollections.observableArrayList(AppController.getArraylistResult("DgetCategories " + activeBoard + " " + teamId));
            categories.setItems(items);
            categories.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    String category = (String) categories.getSelectionModel().getSelectedItem();
                    try {
                        AppController.getResult("ForceTaskToCategorySelect " + category + " " + selectedTask + " " + User.getToken());
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
        sceneController.switchScene(MenusFxml.SELECTED_TASK_OPTIONS.getLabel());
    }
}
