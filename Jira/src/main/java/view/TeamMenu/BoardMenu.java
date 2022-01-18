package view.TeamMenu;

import controller.TeamMenuController.BoardMenuController;
import view.Regex;

import java.sql.SQLException;
import java.util.regex.Matcher;

public class BoardMenu {
    public static void execute(String command) throws SQLException {
        Matcher matcher;
        if ((matcher = Regex.getCommandMatcher(command, Regex.CREATE_BOARD)).matches())
            BoardMenuController.createBoard(matcher.group(1));
        else if ((matcher = Regex.getCommandMatcher(command, Regex.REMOVE_BOARD)).matches())
            BoardMenuController.removeBoard(matcher.group(1));
        else if ((matcher = Regex.getCommandMatcher(command,Regex.SELECT_BOARD)).matches())
            BoardMenuController.selectBoard(matcher.group(1));
        else if ((matcher = Regex.getCommandMatcher(command,Regex.DESELECT_BOARD)).matches())
            BoardMenuController.deselectBoard();
        else if ((matcher = Regex.getCommandMatcher(command,Regex.ADD_CATEGORY)).matches())
            BoardMenuController.addCategory();
        else if ((matcher = Regex.getCommandMatcher(command,Regex.ADD_CATEGORY_TO_COLUMN)).matches())
            BoardMenuController.addCategoryToColumn();
        else if ((matcher = Regex.getCommandMatcher(command,Regex.COMPLETE_BOARD_FIRST_STEP)).matches())
            BoardMenuController.completeBoardFirstStep();
        else if ((matcher = Regex.getCommandMatcher(command,Regex.ADD_TASK_TO_BOARD)).matches())
            BoardMenuController.addTaskToBoard();
        else if ((matcher = Regex.getCommandMatcher(command,Regex.ASSIGN_TASK_TO_MEMBER)).matches())
            BoardMenuController.assignTaskToMemeber();
        else if ((matcher = Regex.getCommandMatcher(command,Regex.FORCE_TASK_TO_CATEGORY)).matches())
            BoardMenuController.forceTaskToCategory();
        else if ((matcher = Regex.getCommandMatcher(command,Regex.TASK_TO_NEXT)).matches())
            BoardMenuController.taskToNext();
        else if ((matcher = Regex.getCommandMatcher(command,Regex.SHOW_TASKS_IN_CATEGORY)).matches())
            BoardMenuController.showTaskInCategory();
        else if ((matcher = Regex.getCommandMatcher(command,Regex.SHOW_FAILED_AND_DONE)).matches())
            BoardMenuController.showFailedAndDone();
        else if ((matcher = Regex.getCommandMatcher(command,Regex.RENEW_FAILED_TASK)).matches())
            BoardMenuController.renewFailedTask();
        else if ((matcher = Regex.getCommandMatcher(command , Regex.SHOW_BOARD)).matches())
            showBoard();
        else
            System.out.println("Invalid Format");
    }
    public static void showBoard() {
    }
}
