package view.TeamMenu;

import appController.AppController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
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
    public String username;
    public String message;
    public int state = 0;

    public void sendMessage() throws SQLException, IOException {
        if (state == 1) {
            sendEditedMessage();
            state = 0;
            return;
        }
        if (LoginMenuGraphic.getRole(User.getActiveUsername()).equals("leader")) {
            String teamName = AppController.getResult("CurrentTeamName " + User.getToken());
            AppController.getResult("setTeamChoice " + teamName + " " + User.getToken());
            AppController.getResult("sendToTeam " + "Leader Sent A Message! " + " " + User.getToken());
        }
        AppController.getResult("SendMessage " + input_String.getText() + " " + User.getToken());
        sceneController.switchScene(MenusFxml.CHATROOM_MENU.getLabel());
    }

    private void sendEditedMessage() throws IOException {
        ArrayList<String> chats = AppController.getArraylistResult("ShowChatroom " + User.getToken());
        int index = chats.indexOf(message);
        chats.add(index, " " + username + " : " + input_String.getText());
        chats.remove(index + 1);
        String teamId = AppController.getResult("CurrentTeamId " + User.getToken());
        String json = AppController.toJson(chats);
        AppController.getResult("DsetChatroom " + json + " " + teamId);
        sceneController.switchScene(MenusFxml.CHATROOM_MENU.getLabel());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chatShow = new VBox();
        chatShow.getStyleClass().add("color-palette");
        chatShow.setBackground(new Background(new BackgroundFill(Color.PURPLE, CornerRadii.EMPTY, Insets.EMPTY)));
        scrollPane.setContent(chatShow);
        chatShow.setPrefWidth(463);
        try {
            ContextMenu contextMenu = new ContextMenu();
            MenuItem menuItem1 = new MenuItem("Delete");
            MenuItem menuItem2 = new MenuItem("edit");
            MenuItem menuItem3 = new MenuItem("pin");
            contextMenu.getItems().addAll(menuItem1, menuItem2, menuItem3);
            menuItem1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        deleteMessage(username, message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            menuItem2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    editMessage(username, message);
                }
            });
            ArrayList<String> chats = AppController.getArraylistResult("ShowChatroom " + User.getToken());
            for (String str : chats) {
                Label label = new Label(str);
                label.setTextFill(Color.WHITE);
                Font font = new Font("Book Antiqua", 20);
                label.setFont(font);
                label.setContextMenu(contextMenu);
                label.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                    @Override
                    public void handle(ContextMenuEvent event) {
                        message = label.getText();
                        username = message.split(" ")[1];
                    }
                });
                chatShow.getChildren().add(label);
            }
            chats.add("\n");
            chats.add("\n");
            chatShow.setAlignment(Pos.TOP_LEFT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editMessage(String username, String message) {
        if (!username.equals(User.getActiveUsername())) {
            return;
        }
        int i = message.indexOf(':');
        i += 2;
        input_String.setText(message.substring(i));
        state = 1;
    }

    private void deleteMessage(String username, String message) throws IOException {
        if (LoginMenuGraphic.getRole(User.getActiveUsername()).equals("member") && !username.equals(User.getActiveUsername())) {
            return;
        }
        ArrayList<String> chats = AppController.getArraylistResult("ShowChatroom " + User.getToken());
        chats.remove(message);
        String teamId = AppController.getResult("CurrentTeamId " + User.getToken());
        String json = AppController.toJson(chats);
        AppController.getResult("DsetChatroom " + json + " " + teamId);
        sceneController.switchScene(MenusFxml.CHATROOM_MENU.getLabel());
    }

    public void BackToTeamMenu(ActionEvent actionEvent) throws IOException {
        if (LoginMenuGraphic.getRole(User.getActiveUsername()).equals("leader")) {
            sceneController.switchScene(MenusFxml.SELECTED_TEAM_MENU_LEADER.getLabel());
            return;
        }
        sceneController.switchScene(MenusFxml.SELECTED_TEAM_MENU.getLabel());
    }

    public void RefreshChatroom(ActionEvent actionEvent) throws IOException {
        sceneController.switchScene(MenusFxml.CHATROOM_MENU.getLabel());
    }
}
