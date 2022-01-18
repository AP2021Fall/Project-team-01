package controller.TeamMenuController;

import controller.LoginController;
import models.DatabaseHandler;

import java.util.ArrayList;

public class ChatroomController {

    public static void showChatroom(){
        ArrayList<String> show = DatabaseHandler.showChatroom(TeamMenuController.getTeam().getName());
        for (String s : show) {
            System.out.println(s);
        }
        if (show.size() == 0)
            System.out.println("no message yet");
    }
    public static void sendMessage(String message){
        String username = LoginController.getActiveUser().getUsername();
        String toPrint = " " + username + " : " + message;
        DatabaseHandler.sendMessage( TeamMenuController.getTeam() , toPrint);
    }
    public static void showChatroomMenu(){
        System.out.println("type Chatroom -show to see chats");
        System.out.println("type send --message  to add a chat there");
        System.out.println("type back to get back into TeamMenu");
    }
}
