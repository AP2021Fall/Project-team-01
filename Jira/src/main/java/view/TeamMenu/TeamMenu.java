package view.TeamMenu;

import controller.TeamMenuController.ChatroomController;
import controller.TeamMenuController.RoadmapController;
import controller.TeamMenuController.ScoreBoardController;
import controller.TeamMenuController.TasksController;
import view.MainMenu;
import view.MenuController;
import view.Menus;
import view.Regex;

import java.sql.SQLException;
import java.util.regex.Matcher;

public class TeamMenu {
    public static void execute(String command) throws SQLException {
        Matcher matcher = Regex.getCommandMatcher(command,Regex.ENTER_MENU);
        String menu = matcher.group(1);
            switch (menu){
                case "Scoreboard":
                    ScoreBoardController.showMenu();
                    MenuController.currentMenu= Menus.SCOREBOARD;
                    break;
                case "Roadmap":
                    RoadmapController.showRoadmapMenu();
                    MenuController.currentMenu = Menus.ROADMAP;
                    break;
                case "BoardMenu":
                    BoardMenu.showBoardMenu();
                    MenuController.currentMenu = Menus.BOARD_MENU;
                    break;
                case "Chatroom":
                    ChatroomController.showChatroomMenu();
                    MenuController.currentMenu = Menus.CHATROOM;
                    break;
                case "Tasks":
                    TasksController.showTaskMenu();
                    MenuController.currentMenu = Menus.TEAM_TASKS;
                    break;
                case "back":
                    MainMenu.showMainMenu();
                    MenuController.currentMenu = Menus.MAIN_MENU;
                    break;
            }

    }


}
