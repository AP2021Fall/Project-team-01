package controller;

import controller.TeamMenuController.BoardMenuController;
import models.Board;
import models.DatabaseHandler;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class CalendarMenuController {
    public static void showDeadlines() throws SQLException {
        ArrayList<Integer>tasksId = DatabaseHandler.getAllTasks();
        for ( Integer list : tasksId) {
            BoardMenuController.updateTasks(list);
        }
        System.out.println(DatabaseHandler.getDeadlinesByUsername(LoginController.getActiveUser().getUsername()));
    }

}
