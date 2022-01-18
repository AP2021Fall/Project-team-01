package view.TeamMenu;

import controller.TeamMenuController.RoadmapController;
import controller.TeamMenuController.ScoreBoardController;
import view.MenuController;
import view.Menus;
import view.Regex;

import java.sql.SQLException;
import java.util.regex.Matcher;

public class TeamMenu {
    public static void execute(String command) throws SQLException {
        Matcher matcher = Regex.getCommandMatcher(command,Regex.ENTER_MENU);
        String menu = matcher.group(1);
            switch (menu){
                case "Scoreboard":
                    ScoreBoardController.showMenu();
                    MenuController.currentMenu= Menus.SCOREBOARD;
                    break;
                case "Roadmap":
                    RoadmapController.showRoadmapMenu();
                    MenuController.currentMenu = Menus.ROADMAP;
                    break;




            }

    }


}
