package view;

import controller.LoginController;
import controller.MainMenuController;
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

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PendingTeamsGraphic implements Initializable {
    public ListView listView;
    public SceneController sceneController = new SceneController();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> items = null;
        try {
            items = FXCollections.observableArrayList (DatabaseHandler.getPendingTeams());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        listView.setItems(items);
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MainMenuController.pendingTeam = (String) listView.getSelectionModel().getSelectedItem();
                sceneController.switchScene(MenusFxml.PENDING_OPTIONS.getLabel());
            }
        });
    }

    public void back(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.ADMIN_MAIN_MENU.getLabel());
    }
}

