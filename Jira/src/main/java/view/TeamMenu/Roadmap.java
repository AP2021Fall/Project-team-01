package view.TeamMenu;

import controller.TeamMenuController.RoadmapController;
import controller.TeamMenuController.TeamMenuController;
import view.MenuController;
import view.Menus;
import view.Regex;

import java.sql.SQLException;
import java.util.regex.Matcher;

public class Roadmap {
    public static void execute(String command) throws SQLException {
        Matcher matcher;
        if ((matcher = Regex.getCommandMatcher(command,Regex.SHOW_ROADMAP)).matches())
            RoadmapController.showRoadmap();
        else if ((matcher = Regex.getCommandMatcher(command,Regex.BACK)).matches()){
            TeamMenuController.showTeamMenu();
            MenuController.currentMenu = Menus.TEAM_MENU;
        } else
            System.out.println("Invalid Command");

    }
}