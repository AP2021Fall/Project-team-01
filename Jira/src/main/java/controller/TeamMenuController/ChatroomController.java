package controller.TeamMenuController;

import controller.LoginController;
import models.DatabaseHandler;

import java.sql.SQLException;
import java.util.ArrayList;

public class ChatroomController {

    public static void showChatroom() throws SQLException {
        ArrayList<String> show = DatabaseHandler.showChatroom(TeamMenuController.getTeam().getId());
        for (String s : show) {
            System.out.println(s);
        }
        if (show.size() == 0)
            System.out.println("no message yet");
    }
    public static void sendMessage(String message) throws SQLException {
        String username = LoginController.getActiveUser().getUsername();
        String toPrint = " " + username + " : " + message;
        System.out.println(TeamMenuController.getTeam().getId());
        DatabaseHandler.sendMessage( TeamMenuController.getTeam().getId() , toPrint);
    }
    public static void showChatroomMenu(){
        System.out.println("type Chatroom -show to see chats");
        System.out.println("type send --message  to add a chat there");
        System.out.println("type back to get back into TeamMenu");
    }
}
