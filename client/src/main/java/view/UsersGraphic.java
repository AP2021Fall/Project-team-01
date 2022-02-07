package view;

import appController.AppController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import models.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class UsersGraphic implements Initializable {

    public ListView listView;
    public ChoiceBox choiceBox;
    public SceneController sceneController = new SceneController();
    public TextField search;
    public ObservableList<String> items = FXCollections.observableArrayList(DatabaseHandler.getAllUsersSortedByName());

    public UsersGraphic() throws SQLException {
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ObservableList<String> items = FXCollections.observableArrayList(DatabaseHandler.getAllUsers());
            listView.setItems(items);
            listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    String username = (String) listView.getSelectionModel().getSelectedItem();
                    try {
                        AppController.getResult("SelectUsernameToRemove " + username + " " + User.getToken());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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

    private List<String> searchList(String searchWords, List<String> listOfStrings) {
        List<String> searchWordsArray = Arrays.asList(searchWords.trim());
        return listOfStrings.stream().filter(input -> { //input = test
            return searchWordsArray.stream().allMatch(word -> //word = te
                    input.contains(word));
        }).collect(Collectors.toList());
    }

    public void searchButton(ActionEvent actionEvent) {
        listView.getItems().clear();
        listView.getItems().addAll(searchList(search.getText(), items));
    }
}
