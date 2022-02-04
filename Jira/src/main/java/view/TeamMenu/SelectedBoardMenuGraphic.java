package view.TeamMenu;

import controller.TeamMenuController.BoardMenuController;
import controller.TeamMenuController.TeamMenuController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.MouseEvent;
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
                ArrayList<String> tasksTitle = new ArrayList<>();
                if (i == columns -1) {
                    vBox.getChildren().add(new Text("Done tasks"));
                     tasksTitle = DatabaseHandler.getDoneTasksTitle(BoardMenuController.getActiveBoard(),
                            TeamMenuController.getTeam().getId());
                } else if (i == columns - 2) {
                    vBox.getChildren().add(new Text("Failed tasks"));
                    tasksTitle = DatabaseHandler.getFailedTasksTitle(BoardMenuController.getActiveBoard(),
                            TeamMenuController.getTeam().getId());
                } else {
                    vBox.getChildren().add(new Text(categories.get(i)));
                    tasksTitle = DatabaseHandler.getTaskOfCategory(categories.get(i),
                            BoardMenuController.getActiveBoard(), TeamMenuController.getTeam().getId());
                }
                for (String taskTitle : tasksTitle) {
                    Text text = new Text(taskTitle);
                    text.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            BoardMenuController.setSelectedTask(text.getText());
                            sceneController.switchScene(MenusFxml.SELECTED_TASK_OPTIONS.getLabel());
                        }
                    });
                    vBox.getChildren().add(text);
                }
                hBox.getChildren().add(vBox);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addTask(ActionEvent actionEvent) {

    }
}
