package view.TeamMenu;

import controller.TeamMenuController.*;
import view.MainMenu;
import view.MenuController;
import view.Menus;
import view.Regex;

import java.sql.SQLException;
import java.util.regex.Matcher;

public class TeamMenu {
    public static void execute(String command) throws SQLException {
        Matcher matcher ;
        if ( (matcher = Regex.getCommandMatcher(command,Regex.ENTER_MENU)).matches()) {
            String menu = matcher.group(1);
            switch (menu) {
                case "Scoreboard":
                    ScoreBoardController.showMenu();
                    MenuController.currentMenu = Menus.SCOREBOARD;
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
        }else if ((matcher = Regex.getCommandMatcher(command,Regex.SHOW_ALL_TASKS_FOR_LEADER)).matches())
            TeamMenuController.showAllTasksLeader();
        else if ((matcher = Regex.getCommandMatcher(command , Regex.CREATE_TASK)).matches())
            TeamMenuController.createTask(matcher.group(1) ,matcher.group(2) , matcher.group(3));
        else if ((matcher = Regex.getCommandMatcher(command,Regex.SHOW_MEMBERS_FOR_LEADER)).matches())
            TeamMenuController.showMembersLeader();
        else if ((matcher = Regex.getCommandMatcher(command,Regex.ADD_MEMBER_TO_TEAM)).matches())
            TeamMenuController.addMemberToTeam(matcher.group(1));
        else if ((matcher = Regex.getCommandMatcher(command,Regex.DELETE_MEMBER_FROM_TEAM)).matches())
            TeamMenuController.deleteMemberFromTeam(matcher.group(1));
        else if ((matcher = Regex.getCommandMatcher(command,Regex.SUSPEND_MEMBER)).matches())
            TeamMenuController.suspendMember(matcher.group(1));
        else if ((matcher = Regex.getCommandMatcher(command,Regex.PROMOTE_MEMBER)).matches())
            TeamMenuController.promoteMember(matcher.group(1));
        else if ((matcher = Regex.getCommandMatcher(command,Regex.ASSIGN_MEMBER_TO_TASK_BY_LEADER)).matches())
            TeamMenuController.assignMemberToTask(matcher.group(1));
        else if ((matcher = Regex.getCommandMatcher(command,Regex.SHOW_SCOREBOARD_TO_LEADER)).matches())
            TeamMenuController.showScoreboardToLeader();
    }


}
