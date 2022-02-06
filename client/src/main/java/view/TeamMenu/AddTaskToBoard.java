package view.TeamMenu;

import controller.LoginController;
import controller.TeamMenuController.BoardMenuController;
import controller.TeamMenuController.ScoreBoardController;
import controller.TeamMenuController.TeamMenuController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import models.DatabaseHandler;
import view.MenusFxml;
import view.SceneController;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddTaskToBoard implements Initializable {
    public ListView listView;
    public SceneController sceneController = new SceneController();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> items = null;
        try {
            items = FXCollections.observableArrayList (DatabaseHandler.getTasksByUsernameOutOfBoard(LoginController.getActiveUser().getUsername(), TeamMenuController.getTeam().getId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        listView.setItems(items);
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                BoardMenuController.setSelectedTask((String) listView.getSelectionModel().getSelectedItem());
                sceneController.switchScene(MenusFxml.ADD_CATEGORY_TO_TASK.getLabel());
            }
        });
    }

    public void back(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SELECTED_BOARD_MENU.getLabel());
    }
}
