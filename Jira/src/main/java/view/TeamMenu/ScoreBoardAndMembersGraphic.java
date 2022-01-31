package view.TeamMenu;

import controller.LoginController;
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

public class ScoreBoardAndMembersGraphic implements Initializable {
    public ListView membersList;
    public SceneController sceneController = new SceneController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> items = null;
        try {
            items = FXCollections.observableArrayList (TeamMenuController.showMembersLeader());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        membersList.setItems(items);
        membersList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ScoreBoardController.usernameToRemove = (String) membersList.getSelectionModel().getSelectedItem();
                if (LoginController.getActiveUser().getRole().equals("leader"))
                    sceneController.switchScene(MenusFxml.OPTIONS_MENU.getLabel());
                else
                    sceneController.switchScene(MenusFxml.MEMBER_OPTION_MENU.getLabel());
            }
        });
    }

    public void back(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SELECTED_TEAM_MENU.getLabel());
    }
}
