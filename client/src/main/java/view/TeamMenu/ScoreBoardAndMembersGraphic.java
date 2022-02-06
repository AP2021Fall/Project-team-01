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
import view.LoginMenuGraphic;
import view.MenusFxml;
import view.SceneController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ScoreBoardAndMembersGraphic implements Initializable {
    public ListView membersList;
    public SceneController sceneController = new SceneController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> items = null;
        try {
            items = FXCollections.observableArrayList (AppController.getArraylistResult("ShowMembersAndLeader " + User.getToken()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        membersList.setItems(items);
        membersList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    AppController.getResult("SelectUsernameToRemove " + membersList.getSelectionModel().getSelectedItem() + " " + User.getToken());
                    if (LoginMenuGraphic.getRole(User.getActiveUsername()).equals("leader"))
                        sceneController.switchScene(MenusFxml.OPTIONS_MENU.getLabel());
                    else
                        sceneController.switchScene(MenusFxml.MEMBER_OPTION_MENU.getLabel());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void back(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SELECTED_TEAM_MENU.getLabel());
    }
}
