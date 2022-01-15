package view;

import controller.LoginController;

import java.sql.SQLException;
import java.util.regex.Matcher;

public class LoginMenu {
    public static void execute(String command) throws SQLException {
        Matcher matcher = Regex.getCommandMatcher(command, "^user\\screate\\s--username\\s(.+)--password1\\s(.+)\\s" +
                "--password2\\s(.+)\\s--email\\sAddress\\s(.+)\\s--role\\s(.+)$");

        if (matcher.find()) {
            System.out.println(LoginController.createUser(matcher.group(1), matcher.group(2), matcher.group(3),matcher.group(4),
                    matcher.group(5)));
        } else if (command.equals("exit")) {
            MenuController.currentMenu = Menus.EXIT;
        }

    }
}
