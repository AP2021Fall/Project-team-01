package view.TeamMenu;

import controller.ProfileMenuController.ProfileMenuController;
import controller.TeamMenuController.TeamMenuController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ScoreBoardAndMembersGraphic extends Application implements Initializable {
    public ListView membersList;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane pane = FXMLLoader.load(getClass().getResource("scoreboardAndMembersMenu.fxml"));
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

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
                System.out.println(membersList.getSelectionModel().getSelectedItem());
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
