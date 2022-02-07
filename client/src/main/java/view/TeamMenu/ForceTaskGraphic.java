package view.TeamMenu;

import controller.TeamMenuController.BoardMenuController;
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

public class ForceTaskGraphic implements Initializable {

    public ListView categories;
    public SceneController sceneController = new SceneController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ObservableList<String> items = FXCollections.observableArrayList(DatabaseHandler.getCategories(BoardMenuController.getActiveBoard(), TeamMenuController.getTeam().getId()));
            categories.setItems(items);
            categories.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    String category = (String) categories.getSelectionModel().getSelectedItem();
                    try {
                        BoardMenuController.forceTaskToCategorySelect(category, BoardMenuController.getSelectedTask());
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
        sceneController.switchScene(MenusFxml.SELECTED_TASK_OPTIONS.getLabel());
    }
}
