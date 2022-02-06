package view.ProfileMenu;

import appController.AppController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import models.User;
import view.MenusFxml;
import view.SceneController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
            AppController.getOutputStream().writeUTF("ShowMyTeams " + User.getToken());
            AppController.getOutputStream().flush();
            String json = AppController.getInputStream().readUTF();
            myTeams = new Gson().fromJson(json,
                    new TypeToken<List<String>>() {
                    }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        VBox vBox = new VBox();
        for (String string : myTeams) {
            Button button = new Button(string);
            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        AppController.getOutputStream().writeUTF("ShowTeam " + string);
                        AppController.getOutputStream().flush();
                        textArea.setText(AppController.getInputStream().readUTF());
                    } catch (IOException e) {
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

    public void goToMainMenu(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.MEMBER_MAIN_MENU.getLabel());
    }
}
