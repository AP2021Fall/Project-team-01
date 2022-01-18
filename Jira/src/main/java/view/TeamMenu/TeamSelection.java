package view.TeamMenu;

import controller.TeamMenuController.TeamSelectionController;
import view.Regex;

import java.sql.SQLException;
import java.util.regex.Matcher;

public class TeamSelection {
    public static void execute(String command) throws SQLException {
        Matcher matcher;
        if ((matcher = Regex.getCommandMatcher(command,Regex.ENTER_TEAM)).matches())
            TeamSelectionController.enterTeam(matcher.group(1));
        else
            System.out.println("Invalid command");
    }
}
