package controller.TeamMenuController;

import models.DatabaseHandler;

import java.sql.SQLException;
import java.util.ArrayList;

public class ScoreBoardController {

    public static String usernameToRemove;
    public static void showScoreboard() throws SQLException {
        ArrayList<Integer>tasksId = DatabaseHandler.getAllTasks();
        for ( Integer list : tasksId) {
            BoardMenuController.updateTasks(list);
        }
        ArrayList<String> show = DatabaseHandler.showScoreboard(TeamMenuController.getTeam().getName());
        for (int i = 0; i < show.size();i++)
            System.out.println(show.get(i));
    }

    public static void showMenu(){
        System.out.println("type Scoreboard --show to see Scoreboard");
        System.out.println("type back to get into TeamMenu");
    }

}
