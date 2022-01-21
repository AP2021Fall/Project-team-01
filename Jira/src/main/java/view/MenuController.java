package view;

import view.ProfileMenu.ChangePasswordMenu;
import view.ProfileMenu.ProfileMenu;
import view.TeamMenu.*;

import java.sql.SQLException;
import java.util.Scanner;

public class MenuController {
    public static Scanner sc = new Scanner(System.in);
    public static Menus currentMenu = Menus.LOGIN_MENU;
    private static String changeRoleUsername;

    public static void execute() throws SQLException {
        while (currentMenu != Menus.EXIT) {
            String command = sc.nextLine();
            switch (currentMenu) {
                case MAIN_MENU:
                    MainMenu.execute(command);
                    break;
                case LOGIN_MENU:
                    LoginMenu.execute(command);
                    break;
                case PROFILE_MENU:
                    ProfileMenu.execute(command);
                    break;
                case TASKS_PAGE:
                    TasksPageMenu.execute(command);
                    break;
                case TEAM_MENU:
                    TeamMenu.execute(command);
                    break;
                case TEAM_SELECTION:
                    TeamSelection.execute(command);
                    break;
                case SCOREBOARD:
                    ScoreBoard.execute(command);
                    break;
                case ROADMAP:
                    Roadmap.execute(command);
                    break;
                case CHANGE_PASSWORD_MENU:
                    ChangePasswordMenu.execute(command);
                    break;
                case BOARD_MENU:
                    BoardMenu.execute(command);
                    break;
                case CHATROOM:
                    Chatroom.execute(command);
                    break;
                case TEAM_TASKS:
                    Tasks.execute(command);
                    break;
                case CALENDAR_MENU:
                    CalendarMenu.execute(command);
                    break;
                case CHANGE_ROLE_MENU:
                    ChangeRoleMenu.execute(command);
                    break;
            }
        }
    }

    public static String getChangeRoleUsername() {
        return changeRoleUsername;
    }

    public static void setChangeRoleUsername(String changeRoleUsername) {
        MenuController.changeRoleUsername = changeRoleUsername;
    }
}


