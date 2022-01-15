package view;

import controller.TasksPageController;

import java.sql.SQLException;
import java.util.regex.Matcher;

public class TasksPageMenu {
    public static void execute(String command) throws SQLException {
        Matcher matcher;

        if ((matcher = Regex.getCommandMatcher(command, Regex.EDIT_TITLE)).matches()) {
            System.out.println(TasksPageController.editTitle(Integer.parseInt(matcher.group(1)), matcher.group(2)));
        } else if ((matcher = Regex.getCommandMatcher(command, Regex.EDIT_DISCRIPTION)).matches()) {
            System.out.println(TasksPageController.editDescription(Integer.parseInt(matcher.group(1)), matcher.group(2)));
        } else if ((matcher = Regex.getCommandMatcher(command, Regex.EDIT_PRIORITY)).matches()) {
            System.out.println(TasksPageController.editPriority(Integer.parseInt(matcher.group(1)), matcher.group(2)));
        } else if ((matcher = Regex.getCommandMatcher(command, Regex.EDIT_DEADLINE)).matches()) {
            System.out.println(TasksPageController.editDeadline(Integer.parseInt(matcher.group(1)), matcher.group(2)));
        } else if ((matcher = Regex.getCommandMatcher(command, Regex.REMOVE_ASSIGNED_USER)).matches()) {
            System.out.println(TasksPageController.removeAssignedUser(Integer.parseInt(matcher.group(1)), matcher.group(2)));
        } else if ((matcher = Regex.getCommandMatcher(command, Regex.ADD_ASSIGNED_USER)).matches()) {
            System.out.println(TasksPageController.addAssignedUser(Integer.parseInt(matcher.group(1)), matcher.group(2)));
        } else if (command.equals("exit")) {
            MenuController.currentMenu = Menus.EXIT;
        }

    }
}
