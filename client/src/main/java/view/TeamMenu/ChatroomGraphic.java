package view.TeamMenu;

import appController.AppController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.User;
import view.LoginMenuGraphic;
import view.MenusFxml;
import view.SceneController;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChatroomGraphic implements Initializable {

    public VBox chatShow;
    public TextField input_String;
    public ScrollPane scrollPane;
    public SceneController sceneController = new SceneController();

    public void sendMessage() throws SQLException, IOException {
        if (LoginMenuGraphic.getRole(User.getActiveUsername()).equals("leader")){
            String teamName = AppController.getResult("CurrentTeamName " + User.getToken());
            AppController.getResult("setTeamChoice " + teamName + " " + User.getToken());
            AppController.getResult("sendToTeam " + "Leader Sent A Message! " + " " + User.getToken());
        }
        AppController.getResult("SendMessage " + input_String.getText() + " " + User.getToken());
        chatShow.getChildren().clear();
        ArrayList<String> chats = AppController.getArraylistResult("ShowChatroom " + User.getToken());
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
            ArrayList<String> chats = AppController.getArraylistResult("ShowChatroom " + User.getToken());
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void BackToTeamMenu(ActionEvent actionEvent) throws IOException {
        if (LoginMenuGraphic.getRole(User.getActiveUsername()).equals("leader")){
            sceneController.switchScene(MenusFxml.SELECTED_TEAM_MENU_LEADER.getLabel());
            return;
        }
        sceneController.switchScene(MenusFxml.SELECTED_TEAM_MENU.getLabel());
    }

    public void RefreshChatroom(ActionEvent actionEvent) throws IOException {
        sceneController.switchScene(MenusFxml.CHATROOM_MENU.getLabel());
    }
}
