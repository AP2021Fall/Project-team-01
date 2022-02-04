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

public class BoardMenuGraphic implements Initializable {

    public ListView boardsList;
    public SceneController sceneController = new SceneController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> items = null;
        try {
            items = FXCollections.observableArrayList (DatabaseHandler.getBoardsOfTeam(TeamMenuController.getTeam().getId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        boardsList.setItems(items);
        boardsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                BoardMenuController.setActiveBoard((String) boardsList.getSelectionModel().getSelectedItem());
                sceneController.switchScene(MenusFxml.SELECTED_BOARD_MENU.getLabel());
            }
        });
    }

    public void BackToSelectedTeamMenu(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SELECTED_TEAM_MENU.getLabel());
    }

    public void createBoard(ActionEvent actionEvent) {

    }
}
