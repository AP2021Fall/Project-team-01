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
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BoardMenuGraphic implements Initializable {

    public ListView boardsList;
    public SceneController sceneController = new SceneController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> items = null;
        try {
            int teamId = Integer.parseInt(AppController.getResult("CurrentTeamId " + User.getToken()));
            items = FXCollections.observableArrayList (AppController.getArraylistResult("DgetBoardsOfTeam " + teamId));
        } catch (IOException e) {
            e.printStackTrace();
        }
        boardsList.setItems(items);
        boardsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    AppController.getResult("SetActiveBoard " + boardsList.getSelectionModel().getSelectedItem() + " " + User.getToken());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sceneController.switchScene(MenusFxml.SELECTED_BOARD_MENU.getLabel());
            }
        });
    }

    public void BackToSelectedTeamMenu(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SELECTED_TEAM_MENU.getLabel());
    }

    public void createBoard(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.CREATE_BOARD_NAME.getLabel());
    }
}
