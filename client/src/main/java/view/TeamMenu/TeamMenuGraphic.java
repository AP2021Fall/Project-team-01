package view.TeamMenu;

import appController.AppController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import models.User;
import view.LoginMenuGraphic;
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
                            AppController.getOutputStream().writeUTF("SelectTeam " + teamName + " " + User.getToken());
                            AppController.getOutputStream().flush();
                            String result = AppController.getInputStream().readUTF();
                            if (LoginMenuGraphic.getRole(User.getActiveUsername()).equals("member"))
                                sceneController.switchScene(MenusFxml.SELECTED_TEAM_MENU.getLabel());
                            else if (LoginMenuGraphic.getRole(User.getActiveUsername()).equals("leader"))
                                sceneController.switchScene(MenusFxml.SELECTED_TEAM_MENU_LEADER.getLabel());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void goToMainMenu(ActionEvent actionEvent) throws IOException {
        String role = AppController.getResult("role " + User.getActiveUsername());
        if (role.equals("leader")) {
            sceneController.switchScene(MenusFxml.LEADER_MAIN_MENU.getLabel());
            return;
        }
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
        return items;
    }

}
