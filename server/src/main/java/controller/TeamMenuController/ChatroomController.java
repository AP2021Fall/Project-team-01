package controller.TeamMenuController;

import controller.LoginController;
import models.DatabaseHandler;
import models.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class ChatroomController {

    public static String showChatroom(String token) throws SQLException {
        ArrayList<String> show = DatabaseHandler.showChatroom(TeamMenuController.getCurrentTeam().get(token).getId());
        StringBuilder stringBuilder = null;
        if (show.size() == 0)
            return("no message yet");
        for (String s : show) {
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }

    public static void sendMessage(String message, String token) throws SQLException {
        String username = User.getLoginUsers().get(token).getUsername();
        String toPrint = " " + username + " : " + message;
        DatabaseHandler.sendMessage(TeamMenuController.getCurrentTeam().get(token).getId() , toPrint);
    }

    public static void showChatroomMenu(){
        System.out.println("type Chatroom -show to see chats");
        System.out.println("type send --message  to add a chat there");
        System.out.println("type back to get back into TeamMenu");
    }
}
