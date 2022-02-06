package view.TeamMenu;

import appController.AppController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.LoginController;
import controller.TeamMenuController.TeamSelectionController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import models.User;
import view.MenusFxml;
import view.SceneController;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TeamMenuGraphic implements Initializable {
    public SceneController sceneController = new SceneController();
    public ListView<String> listViewTeams;
    public TextField searchBar;

    public TeamMenuGraphic() throws SQLException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            listViewTeams.setItems(getItems());
            listViewTeams.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    String teamName = listViewTeams.getSelectionModel().getSelectedItem();
                    if (teamName != null) {
                        try {
                            TeamSelectionController.enterTeam(teamName);
                            if (LoginController.getActiveUser().getRole().equals("member"))
                                sceneController.switchScene(MenusFxml.SELECTED_TEAM_MENU.getLabel());
                            else if (LoginController.getActiveUser().getRole().equals("leader"))
                                sceneController.switchScene(MenusFxml.SELECTED_TEAM_MENU_LEADER.getLabel());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void goToMainMenu(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.MEMBER_MAIN_MENU.getLabel());
    }

    public void search(ActionEvent actionEvent) throws SQLException, IOException {
        listViewTeams.getItems().clear();
        listViewTeams.getItems().addAll(searchList(searchBar.getText(), getItems()));
    }

    private List<String> searchList(String searchWords, List<String> listOfStrings) {
        List<String> searchWordsArray = Arrays.asList(searchWords.trim());
        return listOfStrings.stream().filter(input -> {
            return searchWordsArray.stream().allMatch(word ->
                    input.contains(word));
        }).collect(Collectors.toList());
    }

    public ObservableList<String> getItems () throws IOException {
        AppController.getOutputStream().writeUTF("ShowMyTeams " + User.getToken());
        AppController.getOutputStream().flush();
        String json = AppController.getInputStream().readUTF();
        ArrayList<String> myTeams = new Gson().fromJson(json,
                new TypeToken<List<String>>() {
                }.getType());
        ObservableList<String> items = FXCollections.observableArrayList (myTeams);
    }

}
