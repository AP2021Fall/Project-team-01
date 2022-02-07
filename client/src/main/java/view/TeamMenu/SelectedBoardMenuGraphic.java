package view.TeamMenu;

import appController.AppController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.User;
import view.LoginMenuGraphic;
import view.MenusFxml;
import view.SceneController;

import java.io.IOException;
import java.net.URL;
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
            int teamId = Integer.parseInt(AppController.getResult("CurrentTeamId " + User.getToken()));
            String activeBoard = AppController.getResult("GetActiveBoard " + User.getToken());
            ArrayList<String> categories = AppController.getArraylistResult("DgetCategories " + activeBoard + " " + teamId);
            int columns = categories.size() + 2;
            for (int i = 0; i < columns; i++) {
                VBox vBox = new VBox();
                vBox.setPrefHeight(250);
                vBox.setPrefWidth(100);
                ArrayList<String> tasksTitle = new ArrayList<>();
                if (i == columns - 1) {
                    vBox.getChildren().add(new Text("Done tasks"));
                    tasksTitle = AppController.getArraylistResult("DgetDoneTasksTitle " + activeBoard + " " + teamId);
                } else if (i == columns - 2) {
                    vBox.getChildren().add(new Text("Failed tasks"));
                    tasksTitle = AppController.getArraylistResult("DgetFailedTasksTitle " + activeBoard + " " + teamId);
                } else {
                    vBox.getChildren().add(new Text(categories.get(i)));
                    tasksTitle = AppController.getArraylistResult("DgetTaskOfCategory " + categories.get(i) + " " +
                            activeBoard + " " + teamId);
                }
                for (String taskTitle : tasksTitle) {
                    Text text = new Text(taskTitle);
                    text.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            try {
                                AppController.getResult("SetSelectedTask " + text.getText() + " " + User.getToken());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            sceneController.switchScene(MenusFxml.SELECTED_TASK_OPTIONS.getLabel());
                            String TaskTitle = text.getText();
                            try {
                                AppController.getResult("setTaskIdAndTaskTitle2 " + AppController.getResult("DgetTaskIdByTaskTitle " + taskTitle + " " + teamId) + " " + taskTitle + " " + User.getToken());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            try {
                                if (LoginMenuGraphic.getRole(User.getActiveUsername()).equals("leader")) {
                                    sceneController.switchScene(MenusFxml.TASK_PAGE_LEADER.getLabel());
                                    return;
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            sceneController.switchScene(MenusFxml.TASK_PAGE.getLabel());
                        }
                    });
                    vBox.getChildren().add(text);
                }
                hBox.getChildren().add(vBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addTask(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.ADD_TASK_TO_BOARD.getLabel());
    }
}
