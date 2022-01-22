package controller.TeamMenuController;

import controller.LoginController;
import models.DatabaseHandler;
import models.Team;
import view.MenuController;
import view.Menus;

import java.sql.SQLException;
import java.util.ArrayList;

public class TeamSelectionController {
    public static void enterTeam( String teamName) throws SQLException {
        if (DatabaseHandler.doesTeamExistForUser(teamName, LoginController.getActiveUser().getUsername())) {
            Team team = new Team();
            team.setName(teamName);
            team.setId(DatabaseHandler.getTeamIdByTeamName(teamName));
            TeamMenuController.setTeam(team);
            MenuController.currentMenu = Menus.TEAM_MENU;
            TeamMenuController.showTeamMenu();
        } else {
            System.out.println("wrong team name");
        }
    }
    public static void showTeams() throws SQLException {
        ArrayList<String> teamsOfUser = DatabaseHandler.getUserTeams(LoginController.getActiveUser().getUsername());
        for (String s : teamsOfUser) {
            System.out.println(s);
        }
        if (teamsOfUser.isEmpty())
            System.out.println("no team:(");
    }

}
