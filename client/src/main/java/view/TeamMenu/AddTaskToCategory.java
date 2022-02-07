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
import view.MenusFxml;
import view.SceneController;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddTaskToCategory implements Initializable {
    public ListView listView;
    public SceneController sceneController = new SceneController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> items = null;
        try {
            items = FXCollections.observableArrayList (DatabaseHandler.getCategories(BoardMenuController.getActiveBoard(), TeamMenuController.getTeam().getId()));
            listView.setItems(items);
            listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    String category = (String) listView.getSelectionModel().getSelectedItem();
                    try {
                        DatabaseHandler.addTaskToBoard(DatabaseHandler.getTaskIdByTaskTitle(BoardMenuController.getSelectedTask(), TeamMenuController.getTeam().getId()), BoardMenuController.getActiveBoard());
                        DatabaseHandler.addToCategory(category, BoardMenuController.getSelectedTask(), BoardMenuController.getActiveBoard(), TeamMenuController.getTeam().getId());
                        sceneController.switchScene(MenusFxml.SELECTED_BOARD_MENU.getLabel());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void back(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.ADD_TASK_TO_BOARD.getLabel());
    }
}
