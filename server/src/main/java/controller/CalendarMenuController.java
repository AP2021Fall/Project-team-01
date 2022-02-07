package controller;

import controller.TeamMenuController.BoardMenuController;
import models.Board;
import models.DatabaseHandler;
import models.User;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class CalendarMenuController {
    public static void showDeadlines(String token) throws SQLException {
        ArrayList<Integer>tasksId = DatabaseHandler.getAllTasks();
        for ( Integer list : tasksId) {
            BoardMenuController.updateTasks(list);
        }
        System.out.println(DatabaseHandler.getDeadlinesByUsername(User.getLoginUsers().get(token).getUsername()));
    }

}
