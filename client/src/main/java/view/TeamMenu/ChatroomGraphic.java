package view.TeamMenu;

import controller.TeamMenuController.ChatroomController;
import controller.TeamMenuController.TeamMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.DatabaseHandler;
import javafx.scene.paint.Color;
import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import view.MenusFxml;
import view.SceneController;

public class ChatroomGraphic implements Initializable {

    public VBox chatShow;
    public TextField input_String;
    public ScrollPane scrollPane;
    public SceneController sceneController = new SceneController();

    public void sendMessage() throws SQLException {
        ChatroomController.sendMessage(input_String.getText());
        chatShow.getChildren().clear();
        ArrayList<String> chats = DatabaseHandler.showChatroom(TeamMenuController.getTeam().getId());
        for (String str : chats){
            Text text = new Text(str);
            text.setFill(Color.WHITE);
            Font font = new Font("Book Antiqua" , 20);
            text.setFont(font);
            chatShow.getChildren().add(text);
        }
        chats.add("\n");
        chats.add("\n");
        chatShow.setAlignment(Pos.TOP_LEFT);
        input_String.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chatShow = new VBox();
        chatShow.getStyleClass().add("color-palette");
        chatShow.setBackground(new Background( new BackgroundFill(Color.PURPLE , CornerRadii.EMPTY, Insets.EMPTY)));
        scrollPane.setContent(chatShow);
        chatShow.setPrefWidth(463);
        try {
            ArrayList<String> chats = DatabaseHandler.showChatroom(TeamMenuController.getTeam().getId());
            for (String str :chats) {
                Text text = new Text(str);
                text.setFill(Color.WHITE);
                Font font = new Font("Book Antiqua" , 20);
                text.setFont(font);
                chatShow.getChildren().add(text);
            }
            chats.add("\n");
            chats.add("\n");
            chatShow.setAlignment(Pos.TOP_LEFT);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void BackToTeamMenu(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SELECTED_TEAM_MENU.getLabel());
    }
}
