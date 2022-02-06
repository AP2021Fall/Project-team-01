package view.TeamMenu;

import controller.TeamMenuController.BoardMenuController;
import controller.TeamMenuController.TeamMenuController;
import view.MenuController;
import view.Menus;
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
        else if ((matcher = Regex.getCommandMatcher(command, Regex.SELECT_BOARD)).matches())
            BoardMenuController.selectBoard(matcher.group(1));
        else if ((matcher = Regex.getCommandMatcher(command, Regex.DESELECT_BOARD)).matches())
            BoardMenuController.deselectBoard();
        else if ((matcher = Regex.getCommandMatcher(command, Regex.ADD_CATEGORY_TO_COLUMN)).matches())
            BoardMenuController.addCategoryToColumn(matcher.group(1), matcher.group(2), matcher.group(3));
        else if ((matcher = Regex.getCommandMatcher(command, Regex.ADD_CATEGORY_TO_COLUMN_SELECT)).matches())
            BoardMenuController.addCategoryToColumnSelect(matcher.group(1), matcher.group(2));
        else if ((matcher = Regex.getCommandMatcher(command, Regex.ADD_CATEGORY)).matches())
            BoardMenuController.addCategory(matcher.group(1), matcher.group(2));
        else if ((matcher = Regex.getCommandMatcher(command, Regex.ADD_CATEGORY_SELECT)).matches())
            BoardMenuController.addCategorySelect(matcher.group(1));
        else if ((matcher = Regex.getCommandMatcher(command, Regex.COMPLETE_BOARD_FIRST_STEP)).matches())
            BoardMenuController.completeBoardFirstStep(matcher.group(1));
        else if ((matcher = Regex.getCommandMatcher(command, Regex.COMPLETE_BOARD_FIRST_STEP_SELECT)).matches())
            BoardMenuController.completeBoardFirstStepSelect();
        else if ((matcher = Regex.getCommandMatcher(command, Regex.ADD_TASK_TO_BOARD)).matches())
            BoardMenuController.addTaskToBoard(matcher.group(1), matcher.group(2));
        else if ((matcher = Regex.getCommandMatcher(command, Regex.ADD_TASK_TO_BOARD_SELECT)).matches())
            BoardMenuController.addTaskToBoardSelect(matcher.group(1));
        else if ((matcher = Regex.getCommandMatcher(command, Regex.ASSIGN_TASK_TO_MEMBER)).matches())
            BoardMenuController.assignTaskToMember(matcher.group(1), matcher.group(2), matcher.group(3));
        else if ((matcher = Regex.getCommandMatcher(command, Regex.ASSIGN_TASK_TO_MEMBER_SELECT)).matches())
            BoardMenuController.assignTaskToMemberSelect(matcher.group(1), matcher.group(2));
        else if ((matcher = Regex.getCommandMatcher(command, Regex.FORCE_TASK_TO_CATEGORY)).matches())
            BoardMenuController.forceTaskToCategory(matcher.group(1), matcher.group(2), matcher.group(3));
        else if ((matcher = Regex.getCommandMatcher(command, Regex.FORCE_TASK_TO_CATEGORY_SELECT)).matches())
            BoardMenuController.forceTaskToCategorySelect(matcher.group(1), matcher.group(2));
        else if ((matcher = Regex.getCommandMatcher(command, Regex.TASK_TO_NEXT)).matches())
            BoardMenuController.taskToNext(matcher.group(1) , matcher.group(2));
        else if ((matcher = Regex.getCommandMatcher(command, Regex.TASK_TO_NEXT_SELECT)).matches())
            BoardMenuController.taskToNextSelect(matcher.group(1));
        else if ((matcher = Regex.getCommandMatcher(command, Regex.SHOW_FAILED_AND_DONE)).matches())
            BoardMenuController.showFailedAndDone(matcher.group(1) , matcher.group(2));
        else if ((matcher = Regex.getCommandMatcher(command, Regex.SHOW_FAILED_AND_DONE_SELECT)).matches())
            BoardMenuController.showFailedAndDoneSelect(matcher.group(1));
        else if ((matcher = Regex.getCommandMatcher(command, Regex.RENEW_FAILED_TASK)).matches())
            BoardMenuController.renewFailedTask( matcher.group(1) , matcher.group(3) , matcher.group(4) ,matcher.group(6) , matcher.group(7));
        else if ((matcher = Regex.getCommandMatcher(command, Regex.RENEW_FAILED_TASK_SELECT)).matches())
            BoardMenuController.renewFailedTaskSelect(matcher.group(1),matcher.group(3),matcher.group(4),matcher.group(6));
        else if ((matcher = Regex.getCommandMatcher(command , Regex.SHOW_TASKS_OF_CATEGORY_SELECT)).matches())
            BoardMenuController.showTasksInCategorySelect(matcher.group(1));
        else if ((matcher = Regex.getCommandMatcher(command, Regex.SHOW_BOARD)).matches())
            BoardMenuController.showBoardDetails(matcher.group(1));
        else if ((matcher = Regex.getCommandMatcher(command, Regex.SHOW_BOARD_SELECT)).matches())
            BoardMenuController.showBoardDetailsSelect();
        else if (command.equals("back")) {
            TeamMenuController.showTeamMenu();
            MenuController.currentMenu = Menus.TEAM_MENU;
        }
        else
            System.out.println("Invalid Format");
    }

    public static void showBoardMenu() {
        System.out.println("----BoardMenu----");
        System.out.println("some useful command:)");
    }
}
