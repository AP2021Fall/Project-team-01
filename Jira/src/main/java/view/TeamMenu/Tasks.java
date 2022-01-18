package view.TeamMenu;

import controller.TeamMenuController.TasksController;
import controller.TeamMenuController.TeamMenuController;
import view.MenuController;
import view.Menus;
import view.Regex;

import java.sql.SQLException;
import java.util.regex.Matcher;

public class Tasks {
    public static void execute(String command) throws SQLException {
        Matcher matcher;
        if ((matcher = Regex.getCommandMatcher(command, Regex.SHOW_TASKS)).matches())
            TasksController.showTasks();
        else if ((matcher = Regex.getCommandMatcher(command, Regex.SHOW_TASK_BY_ID)).matches())
            TasksController.showTaskById(matcher.group(1));
        else if ((matcher = Regex.getCommandMatcher(command, Regex.BACK)).matches()) {
            TeamMenuController.showTeamMenu();
            MenuController.currentMenu = Menus.TEAM_MENU;
        } else
            System.out.println("Invalid Command");
    }
}
