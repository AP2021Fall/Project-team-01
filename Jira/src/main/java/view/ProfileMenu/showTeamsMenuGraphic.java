package view.ProfileMenu;

import controller.ProfileMenuController.ProfileMenuController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import view.MenusFxml;
import view.SceneController;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class showTeamsMenuGraphic implements Initializable {
    public SceneController sceneController = new SceneController();
    public BorderPane borderPane;
    public ScrollPane scrollPane;
    public TextArea textArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String> myTeams = new ArrayList<>();
        try {
            myTeams = ProfileMenuController.showTeams();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        VBox vBox = new VBox();
        for (String string : myTeams) {
            Button button = new Button(string);
            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        textArea.setText(ProfileMenuController.showTeam(string));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
            vBox.getChildren().add(button);
        }
        scrollPane.setContent(vBox);
    }

    public void showMyProfile(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SHOW_MY_PROFILE.getLabel());
    }

    public void changeUsername(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.CHANGE_USERNAME_MENU.getLabel());
    }

    public void changePassword(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.CHANGE_PASSWORD_MENU.getLabel());
    }

    public void showLogs(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SHOW_LOGS_MENU.getLabel());
    }

    public void showNotifications(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SHOW_NOTIFICATION_MENU.getLabel());
    }
}
