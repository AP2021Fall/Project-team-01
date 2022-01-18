package controller.TeamMenuController;

import controller.LoginController;
import controller.TeamMenuController.TeamMenuController;
import models.DatabaseHandler;
import models.Team;
import view.MenuController;
import view.Menus;

import java.util.ArrayList;

public class TeamSelectionController {
    public static void enterTeam( String teamName) {
        Team team = new Team();
        team.setName(teamName);
        TeamMenuController.setTeam(team);
        MenuController.currentMenu = Menus.TEAM_MENU;
        TeamMenuController.showTeamMenu();
    }
    public static void showTeams(){
        ArrayList<String> teamsOfUser = DatabaseHandler.getTeamByUsername(LoginController.getActiveUser().getUsername());
        for (int i = 0 ; i < teamsOfUser.size() ; i++){
            System.out.println((i+1 )+ " "+ teamsOfUser.get(i));
        }
    }

}
