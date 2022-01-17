package view;

import java.sql.SQLException;
import java.util.Scanner;

public class MenuController {
    public static Scanner sc = new Scanner(System.in);
    public static Menus currentMenu = Menus.LOGIN_MENU;

    public static void execute() throws SQLException {
//        System.out.println("welcome to Jira (v.1)");
//        System.out.println("..........Start Menu..........\nfor login: user login --username <username> --password <password>\n" +
//                "for sign up: user create --username <username> --password1 <password> --password2 <password> --email Address <email> --role <role(member/leader/admin)>");
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
                case CALENDAR_MENU:
                    CalendarMenu.execute(command);
                    break;
            }
        }
    }
}
