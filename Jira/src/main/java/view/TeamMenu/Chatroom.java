package view.TeamMenu;

import controller.TeamMenuController.ChatroomController;
import controller.TeamMenuController.RoadmapController;
import controller.TeamMenuController.TeamMenuController;
import view.MenuController;
import view.Menus;
import view.Regex;

import java.sql.SQLException;
import java.util.regex.Matcher;

public class Chatroom {
    public static void execute(String command) throws SQLException {
        Matcher matcher;
        if ((matcher = Regex.getCommandMatcher(command,Regex.SHOW_CHAT)).matches())
            ChatroomController.showChatroom();
        else if ((matcher = Regex.getCommandMatcher(command,Regex.SEND_MESSAGE)).matches())
            ChatroomController.sendMessage(matcher.group(1));
        else if ((matcher = Regex.getCommandMatcher(command,Regex.BACK)).matches()){
            TeamMenuController.showTeamMenu();
            MenuController.currentMenu = Menus.TEAM_MENU;
        }
        else
            System.out.println("Invalid Command");
    }

    public static void show() {
        System.out.println("----chatroom menu----");
        System.out.println("send --message <message>");
        System.out.println("Chatroom --show");
    }
}
