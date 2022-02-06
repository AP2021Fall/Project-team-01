package controller.TeamMenuController;

import controller.LoginController;
import models.DatabaseHandler;

import java.sql.SQLException;
import java.util.ArrayList;

public class TasksController {
    public static void showTasks() throws SQLException {
        ArrayList<String> show = DatabaseHandler.getTeamTasksByTeamId(TeamMenuController.getTeam().getId());
        for (int i = 0; i < show.size(); i++)
            System.out.println(show.get(i));
        if (show.size() == 0)
            System.out.println("no task yet");

    }

    public static void showTaskById(int taskId) throws SQLException {
        if (DatabaseHandler.doesTaskExist(taskId)) {
            if (DatabaseHandler.isUsernameTeamMate(LoginController.getActiveUser().getUsername(), DatabaseHandler.getTeamIdByTaskId(taskId))) {
                String show = DatabaseHandler.getDetailOfTask(taskId);
                System.out.println(show);
            } else {
                System.out.println("this task is not in your team");
            }
        } else {
            System.out.println("task doesn't exist");
        }
    }

    public static void showTaskMenu() {
        System.out.println("type show tasks to see all tasks of user");
        System.out.println("type show task --id to see a task by id");
        System.out.println("type back to go back into TeamMenu");
    }
}
