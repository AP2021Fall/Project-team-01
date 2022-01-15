package view;

import java.sql.SQLException;
import java.util.Scanner;

public class MenuController {
    public static Scanner sc = new Scanner(System.in);
    private static Menus currentMenu = Menus.REGISTER_MENU;
    public static void execute() throws SQLException {
        while (true) {
            String command = sc.nextLine();
            switch (currentMenu) {
                case MAIN_MENU:
                    MainMenu.execute(command);
                    break;
            }
        }
    }
}
