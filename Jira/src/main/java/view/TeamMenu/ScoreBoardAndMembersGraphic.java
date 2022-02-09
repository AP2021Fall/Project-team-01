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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import models.DatabaseHandler;
import view.MenusFxml;
import view.SceneController;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ScoreBoardAndMembersGraphic implements Initializable {
    public ListView membersList;
    public SceneController sceneController = new SceneController();
    public TextField searchBar;
    public ObservableList<String> items = FXCollections.observableArrayList(TeamMenuController.showMembersLeader());

    public ScoreBoardAndMembersGraphic() throws SQLException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> items = null;
        try {
            items = FXCollections.observableArrayList(TeamMenuController.showMembersLeader());
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
        if (LoginController.getActiveUser().getRole().equals("member"))
        sceneController.switchScene(MenusFxml.SELECTED_TEAM_MENU.getLabel());
        else if (LoginController.getActiveUser().getRole().equals("leader"))
            sceneController.switchScene(MenusFxml.SELECTED_TEAM_MENU_LEADER.getLabel());
    }


        public void search(ActionEvent actionEvent) throws SQLException {
            membersList.getItems().clear();
            membersList.getItems().addAll(searchList(searchBar.getText(), items));
        }
    private List<String> searchList(String searchWords, List<String> listOfStrings) {
        List<String> searchWordsArray = Arrays.asList(searchWords.trim());
        return listOfStrings.stream().filter(input -> { //input = test
            return searchWordsArray.stream().allMatch(word -> //word = te
                    input.contains(word));
        }).collect(Collectors.toList());
    }

    public void sortByPoints(ActionEvent actionEvent) throws SQLException {
        membersList.getItems().clear();
        ArrayList<String> help = DatabaseHandler.getAllUsersSortedByScore();
        membersList.getItems().addAll(help);
    }
}
