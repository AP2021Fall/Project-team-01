package view.TeamMenu;

import controller.TeamMenuController.ChatroomController;
import controller.TeamMenuController.TeamMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
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
public class ChatroomGraphic implements Initializable {

    public VBox chatShow;
    public TextField input_String;

    public void sendMessage() throws SQLException {
        ChatroomController.sendMessage(input_String.getText());
        chatShow.getChildren().clear();
        ArrayList<String> chats = DatabaseHandler.showChatroom(TeamMenuController.getTeam().getId());
        for (String str : chats){
            Text text = new Text(str);
            text.setFill(Color.WHITE);
            Font font = new Font("Book Antiqua" , 24);
            text.setFont(font);
            chatShow.getChildren().add(text);
            chatShow.setAlignment(Pos.TOP_CENTER);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ArrayList<String> chats = DatabaseHandler.showChatroom(TeamMenuController.getTeam().getId());
            chatShow.getChildren().add(new Text(chats.get(0)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
