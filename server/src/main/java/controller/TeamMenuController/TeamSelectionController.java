package controller.TeamMenuController;

import models.DatabaseHandler;
import models.Team;
import models.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class TeamSelectionController {
    public static void enterTeam(String teamName, String token) throws SQLException {
        Team team = new Team();
        team.setName(teamName);
        team.setId(DatabaseHandler.getTeamIdByTeamName(teamName));
        TeamMenuController.getCurrentTeam().put(token, team);
    }

    public static ArrayList<String> showTeams(String token) throws SQLException {
        ArrayList<String> teamsOfUser = DatabaseHandler.getUserTeams(User.getLoginUsers().get(token).getUsername());
        return teamsOfUser;
    }

}
