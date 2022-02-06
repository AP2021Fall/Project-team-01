package view.TeamMenu;

import controller.TeamMenuController.ScoreBoardController;
import controller.TeamMenuController.TeamMenuController;
import view.Regex;

import java.sql.SQLException;
import java.util.regex.Matcher;

public class ScoreBoard {
    public static void execute(String command) throws SQLException {
        Matcher matcher;
        if ((matcher = Regex.getCommandMatcher(command, Regex.SHOW_SCOREBOARD)).matches())
            ScoreBoardController.showScoreboard();
        else if ((matcher = Regex.getCommandMatcher(command, Regex.BACK)).matches()) {
            TeamMenuController.showTeamMenu();
            MenuController.currentMenu = Menus.TEAM_MENU;
        } else
            System.out.println("Invalid Command");
    }
}
