package view;

import controller.LoginController;
import controller.TeamMenuController.ScoreBoardController;
import controller.TeamMenuController.TeamSelectionController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import models.DatabaseHandler;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UsersGraphic implements Initializable {

    public ListView listView;
    public ChoiceBox choiceBox;
    public SceneController sceneController = new SceneController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ObservableList<String> items = FXCollections.observableArrayList(DatabaseHandler.getAllUsers());
            listView.setItems(items);
            listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    ScoreBoardController.usernameToRemove = (String) listView.getSelectionModel().getSelectedItem();
                    sceneController.switchScene(MenusFxml.USERS_OPTIONS.getLabel());
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        choiceBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    ObservableList<String> items;
                    String sort = (String) choiceBox.getValue();
                    if (sort.equals("name"))
                        items = FXCollections.observableArrayList(DatabaseHandler.getAllUsersSortedByName());
                    else if (sort.equals("score"))
                        items = FXCollections.observableArrayList(DatabaseHandler.getAllUsersSortedByScore());
                    else
                        items = FXCollections.observableArrayList(DatabaseHandler.getAllUsers());
                    listView.setItems(items);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void back(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.ADMIN_MAIN_MENU.getLabel());
    }
}
