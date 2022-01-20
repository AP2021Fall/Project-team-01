package view;

import view.ProfileMenu.ProfileMenu;
import view.TeamMenu.Roadmap;
import view.TeamMenu.ScoreBoard;
import view.TeamMenu.TeamMenu;
import view.TeamMenu.TeamSelection;

import java.sql.SQLException;
import java.util.Scanner;

public class MenuController {
    public static Scanner sc = new Scanner(System.in);
    public static Menus currentMenu = Menus.LOGIN_MENU;

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

                    break;
            }
        }
    }
}


