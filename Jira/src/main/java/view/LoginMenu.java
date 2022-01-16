package view;

import controller.LoginController;

import java.sql.SQLException;
import java.util.regex.Matcher;

public class LoginMenu {
    public static void execute(String command) throws SQLException {
        Matcher matcher ;

        if ((matcher = Regex.getCommandMatcher(command, Regex.CREATE_USER)).matches()) {
            System.out.println(LoginController.createUser(matcher.group(1), matcher.group(2), matcher.group(3),matcher.group(4),
                    matcher.group(5)));
        } else if((matcher = Regex.getCommandMatcher(command, Regex.LOGIN_USER)).matches()){
            System.out.println(LoginController.loginUser(matcher.group(1), matcher.group(2)));
            MainMenu.show();
            MenuController.currentMenu = Menus.MAIN_MENU;
        } else if (command.equals("exit")) {
            MenuController.currentMenu = Menus.EXIT;
        }

    }
}
