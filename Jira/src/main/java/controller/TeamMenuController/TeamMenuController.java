package controller.TeamMenuController;

import models.Team;

public class TeamMenuController {
    private static Team currentTeam;

    public static Team getTeam() {
        return currentTeam;
    }

    public static void setTeam(Team team) {
        TeamMenuController.currentTeam = team;
    }

    public static void showTeamMenu(){
        System.out.println("Enter Menu");
        System.out.println("Scoreboard");
        System.out.println("BoardMenu");
        System.out.println("Roadmap");
        System.out.println("ChatRoom");
        System.out.println("Tasks");
        System.out.println("back");
    }
}
