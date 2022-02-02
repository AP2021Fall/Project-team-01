package view.TeamMenu;

import controller.TeamMenuController.BoardMenuController;
import controller.TeamMenuController.TeamMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.DatabaseHandler;
import view.MenusFxml;
import view.SceneController;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SelectedBoardMenuGraphic implements Initializable {

    public HBox hBox;
    public SceneController sceneController = new SceneController();

    public void back(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.BOARD_MENU.getLabel());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ArrayList<String> categories = DatabaseHandler.getCategories(BoardMenuController.getActiveBoard(), TeamMenuController.getTeam().getId());
            int columns = categories.size() + 2;
            for (int i = 0; i < columns; i++){
                VBox vBox = new VBox();
                vBox.setPrefHeight(250);
                vBox.setPrefWidth(100);
                if (i == columns -1) {
                    vBox.getChildren().add(new Text("Done tasks"));
                    ArrayList<String> tasksTitle = DatabaseHandler.getDoneTasksTitle(BoardMenuController.getActiveBoard(),
                            TeamMenuController.getTeam().getId());
                    for (String taskTitle : tasksTitle) {
                        vBox.getChildren().add(new Text(taskTitle));
                    }
                } else if (i == columns - 2) {
                    vBox.getChildren().add(new Text("Failed tasks"));
                    ArrayList<String> tasksTitle = DatabaseHandler.getFailedTasksTitle(BoardMenuController.getActiveBoard(),
                            TeamMenuController.getTeam().getId());
                    for (String taskTitle : tasksTitle) {
                        vBox.getChildren().add(new Text(taskTitle));
                    }
                } else {
                    vBox.getChildren().add(new Text(categories.get(i)));
                    ArrayList<String> tasksTitle = DatabaseHandler.getTaskOfCategory(categories.get(i),
                            BoardMenuController.getActiveBoard(), TeamMenuController.getTeam().getId());
                    for (String taskTitle : tasksTitle) {
                        vBox.getChildren().add(new Text(taskTitle));
                    }
                }
                hBox.getChildren().add(vBox);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
