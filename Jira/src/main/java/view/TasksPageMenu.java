package view;

import controller.LoginController;
import controller.TasksPageController;

import java.sql.SQLException;
import java.util.regex.Matcher;

public class TasksPageMenu {
    public static void execute(String command) throws SQLException {
        Matcher matcher;
        if ((matcher = Regex.getCommandMatcher(command, Regex.EDIT_TITLE)).matches()) {
            System.out.println(TasksPageController.editTitle(Integer.parseInt(matcher.group(1)), matcher.group(2)));
        } else if ((matcher = Regex.getCommandMatcher(command, Regex.EDIT_DESCRIPTION)).matches()) {
            System.out.println(TasksPageController.editDescription(Integer.parseInt(matcher.group(1)), matcher.group(2)));
        } else if ((matcher = Regex.getCommandMatcher(command, Regex.EDIT_PRIORITY)).matches()) {
            System.out.println(TasksPageController.editPriority(Integer.parseInt(matcher.group(1)), matcher.group(2)));
        } else if ((matcher = Regex.getCommandMatcher(command, Regex.EDIT_DEADLINE)).matches()) {
            System.out.println(TasksPageController.editDeadline(Integer.parseInt(matcher.group(1)), matcher.group(2)));
        } else if ((matcher = Regex.getCommandMatcher(command, Regex.REMOVE_ASSIGNED_USER)).matches()) {
            System.out.println(TasksPageController.removeAssignedUser(Integer.parseInt(matcher.group(1)), matcher.group(2)));
        } else if ((matcher = Regex.getCommandMatcher(command, Regex.ADD_ASSIGNED_USER)).matches()) {
            System.out.println(TasksPageController.addAssignedUser(Integer.parseInt(matcher.group(1)), matcher.group(2)));
        } else if ((matcher = Regex.getCommandMatcher(command, Regex.SHOW_COMMENTS)).matches()) {
            TasksPageController.showComments(Integer.parseInt(matcher.group(1)));
        } else if ((matcher = Regex.getCommandMatcher(command, Regex.ADD_COMMENT)).matches()) {
            TasksPageController.addComment(Integer.parseInt(matcher.group(1)), matcher.group(2));
        } else if (command.equals("exit")) {
            MenuController.currentMenu = Menus.EXIT;
            LoginMenu.showLogin();
        } else if (command.equals("back")) {
            MenuController.currentMenu = Menus.MAIN_MENU;
            MainMenu.showMainMenu();
        } else {
            System.out.println(Regex.INVALID_COMMAND);
        }
    }

    public static void showTaskPageMenu(){
        System.out.println("..........TaskPage Menu..........\n" +
                "valid commands:\n" +
                "edit --task --id <taskid> --title <new title>\n" +
                "edit --task --id <taskid> --description <new description>\n" +
                "edit --task --id <taskid> --description <new priority>\n" +
                "edit --task --id <taskid> --description <new deadline(yyyy-mm-dd|HH:mm)>\n" +
                "edit --task --id <taskid> --assignedUsers <username> --add\n" +
                "edit --task --id <taskid> --assignedUsers <username> --remove\n" +
                "task <task id> comments --show\n" +
                "task <task id> comment <comment> --add\n" +
                "back\n" +
                "exit");
    }

}
