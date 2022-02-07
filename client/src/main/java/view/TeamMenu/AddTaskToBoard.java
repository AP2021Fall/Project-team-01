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

public class AddTaskToBoard implements Initializable {
    public ListView listView;
    public SceneController sceneController = new SceneController();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> items = null;
        try {
            int teamId = Integer.parseInt(AppController.getResult("CurrentTeamId " + User.getToken()));
            items = FXCollections.observableArrayList(AppController.getArraylistResult("DgetTasksByUsernameOutOfBoard " + User.getActiveUsername() + " " + teamId));
        } catch (IOException e) {
            e.printStackTrace();
        }
        listView.setItems(items);
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    AppController.getResult("SetSelectedTask " + listView.getSelectionModel().getSelectedItem() + " " + User.getToken());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sceneController.switchScene(MenusFxml.ADD_CATEGORY_TO_TASK.getLabel());
            }
        });
    }

    public void back(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SELECTED_BOARD_MENU.getLabel());
    }
}
