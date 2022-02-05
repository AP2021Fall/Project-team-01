package view.TeamMenu;

import controller.LoginController;
import controller.TeamMenuController.BoardMenuController;
import controller.TeamMenuController.ScoreBoardController;
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

public class AddTaskToBoard implements Initializable {
    public ListView listView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> items = null;
        try {
            items = FXCollections.observableArrayList (DatabaseHandler.getTasksByUsernameOutOfBoard(LoginController.getActiveUser().getUsername()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        listView.setItems(items);
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                BoardMenuController.setSelectedTask((String) listView.getSelectionModel().getSelectedItem());

            }
        });
    }

    public void back(ActionEvent actionEvent) {

    }
}
