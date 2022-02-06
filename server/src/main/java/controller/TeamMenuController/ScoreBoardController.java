package controller.TeamMenuController;

import models.DatabaseHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ScoreBoardController {
    private static HashMap<String, String> usernameToRemove = new HashMap<>();

    public static HashMap<String, String> getUsernameToRemove() {
        return usernameToRemove;
    }

    public static void setUsernameToRemove(HashMap<String, String> usernameToRemove) {
        ScoreBoardController.usernameToRemove = usernameToRemove;
    }

    public static void showScoreboard() throws SQLException {
        ArrayList<Integer> tasksId = DatabaseHandler.getAllTasks();
        for (Integer list : tasksId) {
            BoardMenuController.updateTasks(list);
        }
        ArrayList<String> show = DatabaseHandler.showScoreboard(TeamMenuController.getTeam().getName());
        for (int i = 0; i < show.size(); i++)
            System.out.println(show.get(i));
    }

    public static void showMenu() {
        System.out.println("type Scoreboard --show to see Scoreboard");
        System.out.println("type back to get into TeamMenu");
    }

}
