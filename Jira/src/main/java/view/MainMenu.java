package view;

import controller.MainMenuController;

import java.sql.SQLException;
import java.util.regex.Matcher;

public class MainMenu {
    public static void execute(String command) throws SQLException {
        Matcher matcher = Regex.getCommandMatcher(command, "^enter\\smenu\\s(.+)$");
        if (matcher.find()) {
            String menu = matcher.group(1);
            switch (menu){
                case "profile menu":
                    break;
                case "team menu":
                    break;
                case "tasks page":
                    break;
                case "calendar menu":
                    break;
                case "notification bar":
                    break;
            }
        } else if(command.equals("show --teams")) {
            MainMenuController.showTeams();
        }

    }

}
