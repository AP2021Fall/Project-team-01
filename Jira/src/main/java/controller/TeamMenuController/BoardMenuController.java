package controller.TeamMenuController;

import controller.LoginController;
import models.Board;
import models.DatabaseHandler;
import view.Regex;

import java.sql.SQLException;
import java.util.ArrayList;

public class BoardMenuController {
    private static String activeBoard = null;

    private static String selectedTask;

    public static String getActiveBoard() {
        return activeBoard;
    }

    public static void setActiveBoard(String activeBoard) {
        BoardMenuController.activeBoard = activeBoard;
    }

    public static void updateTasks(int taskId) throws SQLException {
        int help = DatabaseHandler.getStateOfTask(taskId);
        if (help == 3) {
            if (DatabaseHandler.doesDeadlinePassed(taskId)) {
                DatabaseHandler.setStateOfTask(taskId, 0);
                ArrayList<String> list = DatabaseHandler.getMembersOfTask(taskId);
                for (String s : list) {
                    int i = DatabaseHandler.getPointsOfUser(s);
                    DatabaseHandler.setPointOfUser(s, i - 5);
                }
            }
        }

    }

    public static String createBoard(String boardName) throws SQLException {
        if (LoginController.getActiveUser().getRole().equals("leader")) {
            if (!(DatabaseHandler.doesBoardExist(boardName, TeamMenuController.getTeam().getId()))) {
                DatabaseHandler.createBoard(boardName, TeamMenuController.getTeam().getId());
                return ("board created");
            } else
                return ("There is already a board with this name");
        } else
            return ("You do not have the permission to do this action!");
    }

    public static void removeBoard(String boardName) throws SQLException {
        if (LoginController.getActiveUser().getRole().equals("leader")) {
            if (DatabaseHandler.doesBoardExist(boardName, TeamMenuController.getTeam().getId()))
                DatabaseHandler.removeBoard(boardName, TeamMenuController.getTeam().getId());
            else
                System.out.println("There is no board with this name");
        } else
            System.out.println("You do not have the permission to do this action!");
    }

    public static void selectBoard(String boardName) throws SQLException {
        if (DatabaseHandler.doesBoardExist(boardName, TeamMenuController.getTeam().getId())) {
            setActiveBoard(boardName);
            System.out.println("Board selected");
        } else
            System.out.println("There is no board with this name");
    }

    public static void deselectBoard() {
        if (getActiveBoard() != null) {
            setActiveBoard(null);
            System.out.println("board deselected");
        } else
            System.out.println("No board is selected");
    }

    public static String addCategorySelect(String categoryName) throws SQLException {
        String take = BoardMenuController.getActiveBoard();
        if (take != null)
            return addCategory(categoryName, take);
        else
            return ("No board is selected");
    }

    public static String addCategory(String categoryName, String boardName) throws SQLException {

        if (LoginController.getActiveUser().getRole().equals("leader")) {
            if (DatabaseHandler.doesBoardExist(boardName, TeamMenuController.getTeam().getId())) {
                if (!DatabaseHandler.doesCategoryExist(categoryName, boardName, TeamMenuController.getTeam().getId())) {
                    DatabaseHandler.addCategory(categoryName, boardName, TeamMenuController.getTeam().getId());
                    return ("category added");
                } else
                    return ("category already exist");
            } else
                return ("There is not a board with this name");
        } else
            return ("You do not have the permission to do this action!");
    }

    public static String addCategoryToColumnSelect(String categoryName, String columnNum) throws SQLException {
        String take = BoardMenuController.getActiveBoard();
        if (take != null)
            return addCategoryToColumn(categoryName, columnNum, take);
        else
            return ("No board is selected");
    }

    public static String addCategoryToColumn(String categoryName, String columnNum, String boardName) throws SQLException {
        if (LoginController.getActiveUser().getRole().equals("leader")) {
            if (DatabaseHandler.doesBoardExist(boardName, TeamMenuController.getTeam().getId())) {
                if (!DatabaseHandler.doesCategoryExist(categoryName, boardName, TeamMenuController.getTeam().getId())) {
                    int help = DatabaseHandler.numOfBoardCategories(boardName, TeamMenuController.getTeam().getId());
                    if (help >= Integer.parseInt(columnNum) - 1 && help > 0) {
                        DatabaseHandler.addCategoryToColumn(categoryName, Integer.parseInt(columnNum) - 1, boardName, TeamMenuController.getTeam().getId());
                        return ("category added to specific column");
                    } else
                        return ("wrong column");
                } else
                    return ("category already exist");
            } else
                return ("There is not a board with this name");
        } else
            return ("You do not have the permission to do this action!");
    }

    public static String completeBoardFirstStepSelect() throws SQLException {
        String take = BoardMenuController.getActiveBoard();
        if (take != null)
            return completeBoardFirstStep(take);
        else
            return ("No board is selected");
    }

    public static String completeBoardFirstStep(String boardName) throws SQLException {
        if (LoginController.getActiveUser().getRole().equals("leader")) {
            if (DatabaseHandler.doesBoardExist(boardName, TeamMenuController.getTeam().getId())) {
                if (DatabaseHandler.numOfBoardCategories(boardName, TeamMenuController.getTeam().getId()) != 0) {
                    DatabaseHandler.finishBoard(boardName, TeamMenuController.getTeam().getId());
                    return ("first step completed");
                } else
                    return ("Please make a category first");
            } else
                return ("There is no board with this name");
        } else
            return ("You do not have the permission to do this action!");
    }

    public static void addTaskToBoardSelect(String taskId) throws SQLException {
        String take = BoardMenuController.getActiveBoard();
        if (take != null)
            addTaskToBoard(taskId, take);
        else
            System.out.println("No board is selected");
    }

    public static void addTaskToBoard(String taskId, String boardName) throws SQLException {
        ArrayList<Integer> tasksId = DatabaseHandler.getAllTasks();
        for (Integer list : tasksId) {
            BoardMenuController.updateTasks(list);
        }
        if (LoginController.getActiveUser().getRole().equals("leader")) {
            int taskNum = Integer.parseInt(taskId);
            if (DatabaseHandler.doesBoardExist(boardName, TeamMenuController.getTeam().getId())) {
                if (DatabaseHandler.doesTaskExist(taskNum)) {
                    if (LoginController.getActiveUser().getUsername().equals(DatabaseHandler.getTaskLeaderByTaskId(taskNum))) {
                        if (!(DatabaseHandler.doesTaskAddedToBoard(taskNum, boardName))) {
                            if (!(DatabaseHandler.doesDeadlinePassed(taskNum))) {
                                if (DatabaseHandler.isTaskAssigned(taskNum)) {
                                    if (DatabaseHandler.getBoardState(boardName, TeamMenuController.getTeam().getId()).equals("yes")) {
                                        DatabaseHandler.addTaskToBoard(taskNum, boardName);
                                        System.out.println("task successfully added to board");
                                    } else
                                        System.out.println("finish board first");
                                } else
                                    System.out.println("Please assign this task to someone first");
                            } else
                                System.out.println("The deadline of this task has already passed");
                        } else
                            System.out.println("This task has already been added to this board");
                    } else
                        System.out.println("you don't have access to do this action!");
                } else
                    System.out.println("Invalid task id!");
            } else
                System.out.println("There is not any board with that name in this team");
        } else
            System.out.println("You do not have the permission to do this action!");

    }

    public static void assignTaskToMemberSelect(String teamMember, String taskId) throws SQLException {
        String take = BoardMenuController.getActiveBoard();
        if (take != null)
            assignTaskToMember(teamMember, taskId, take);
        else
            System.out.println("No board is selected");
    }

    public static void assignTaskToMember(String teamMember, String taskId, String boardName) throws SQLException {
        ArrayList<Integer> tasksId = DatabaseHandler.getAllTasks();
        for (Integer list : tasksId) {
            BoardMenuController.updateTasks(list);
        }
        if (LoginController.getActiveUser().getRole().equals("leader")) {
            int taskNum = Integer.parseInt(taskId);
            if (DatabaseHandler.doesBoardExist(boardName, TeamMenuController.getTeam().getId())) {
                if (DatabaseHandler.doesTaskExist(taskNum)) {
                    if (LoginController.getActiveUser().getUsername().equals(DatabaseHandler.getTaskLeaderByTaskId(taskNum))) {
                        if ((DatabaseHandler.doesTaskAddedToBoard(taskNum, boardName))) {
                            if (!(DatabaseHandler.getStateOfTask(taskNum) == 0 || DatabaseHandler.getStateOfTask(taskNum) == 1)) {
                                if (DatabaseHandler.doesTeamExistForUser(TeamMenuController.getTeam().getName(), teamMember)) {
                                    if (DatabaseHandler.getBoardState(boardName, TeamMenuController.getTeam().getId()).equals("yes")) {
                                        if (DatabaseHandler.isUsernameAssigned(taskNum, teamMember)) {
                                            DatabaseHandler.assignUser(taskNum, teamMember);
                                            System.out.println("task assigned to member");
                                        } else
                                            System.out.println("user assigned already");
                                    } else
                                        System.out.println("finish board first");
                                } else
                                    System.out.println("Invalid teammate");
                            } else
                                System.out.println("This task has already finished");
                        } else
                            System.out.println("This task has not added to this board");
                    } else
                        System.out.println("you don't have access to do this action!");
                } else
                    System.out.println("Invalid task id!");
            } else
                System.out.println("There is not any board with that name in this team");
        } else
            System.out.println("You do not have the permission to do this action!");
    }

    public static void forceTaskToCategorySelect(String category, String taskTitle) throws SQLException {
        String take = BoardMenuController.getActiveBoard();
        if (take != null)
            forceTaskToCategory(category, taskTitle, take);
        else
            System.out.println("No board is selected");

    }

    public static void forceTaskToCategory(String category, String taskTitle, String boardName) throws SQLException {
        ArrayList<Integer> taskIds = DatabaseHandler.getAllTasks();
        for (Integer list : taskIds) {
            BoardMenuController.updateTasks(list);
        }
        if (DatabaseHandler.doesBoardExist(boardName, TeamMenuController.getTeam().getId())) {
            if (DatabaseHandler.doesTaskExist(DatabaseHandler.getTaskIdByTaskTitle(taskTitle, TeamMenuController.getTeam().getId()))) {
                ArrayList<Integer> tasksId = DatabaseHandler.getAllTasks();
                for (Integer list : tasksId) {
                    BoardMenuController.updateTasks(list);
                }
                int taskNum = DatabaseHandler.getTaskIdByTaskTitle(taskTitle, TeamMenuController.getTeam().getId());
                if (DatabaseHandler.isUsernameAssigned(taskNum, LoginController.getActiveUser().getUsername())) {
                    if ((DatabaseHandler.doesTaskAddedToBoard(taskNum, boardName))) {
                        if (DatabaseHandler.doesCategoryExist(category, boardName, TeamMenuController.getTeam().getId())) {
                            if (!(DatabaseHandler.getStateOfTask(taskNum) == 0 || DatabaseHandler.getStateOfTask(taskNum) == 1)) {
                                if (DatabaseHandler.getBoardState(boardName, TeamMenuController.getTeam().getId()).equals("yes")) {
                                    DatabaseHandler.addToCategory(category, taskTitle, boardName, TeamMenuController.getTeam().getId());
                                    System.out.println("task added to category");
                                } else
                                    System.out.println("finish board first");
                            } else
                                System.out.println("This task has already finished");
                        } else
                            System.out.println("category does not exit");
                    } else
                        System.out.println("This task has not added to this board");
                } else
                    System.out.println("This task is not assigned to you!");
            } else
                System.out.println("There is no task with given information");
        } else
            System.out.println("There is not any board with that name in this team");
    }

    public static void taskToNextSelect(String taskTitle) throws SQLException {
        String take = BoardMenuController.getActiveBoard();
        if (take != null)
            taskToNext(taskTitle, take);
        else
            System.out.println("No board is selected");
    }

    //we get task title instead task id
    public static void taskToNext(String taskTitle, String boardName) throws SQLException {
        if (DatabaseHandler.getBoardState(boardName, TeamMenuController.getTeam().getId()).equals("yes")) {
            ArrayList<Integer> taskIds = DatabaseHandler.getAllTasks();
            for (Integer list : taskIds) {
                BoardMenuController.updateTasks(list);
            }
            if (DatabaseHandler.doesBoardExist(boardName, TeamMenuController.getTeam().getId())) {
                if (DatabaseHandler.doesTaskExist(DatabaseHandler.getTaskIdByTaskTitle(taskTitle, TeamMenuController.getTeam().getId()))) {
                    ArrayList<Integer> tasksId = DatabaseHandler.getAllTasks();
                    for (Integer list : tasksId) {
                        BoardMenuController.updateTasks(list);
                    }
                    int taskNum = DatabaseHandler.getTaskIdByTaskTitle(taskTitle, TeamMenuController.getTeam().getId());
                    if (DatabaseHandler.isUsernameAssigned(taskNum, LoginController.getActiveUser().getUsername())) {
                        if ((DatabaseHandler.doesTaskAddedToBoard(taskNum, boardName))) {
                            if (!(DatabaseHandler.getStateOfTask(taskNum) == 0 || DatabaseHandler.getStateOfTask(taskNum) == 1)) {
                                String categoryName = DatabaseHandler.getCategory(DatabaseHandler.getTaskIdByTaskTitle(taskTitle, TeamMenuController.getTeam().getId()));
                                ArrayList<String> help = DatabaseHandler.getCategories(boardName, TeamMenuController.getTeam().getId());
                                if (help.size() != 0) {
                                    int i = help.indexOf(categoryName);
                                    if (i != help.size() - 1) {
                                        String passToMethod = help.get(i + 1);
                                        DatabaseHandler.addToCategory(passToMethod, taskTitle, boardName, TeamMenuController.getTeam().getId());
                                        System.out.println("task move on");
                                    } else {
                                        System.out.println("There is no category after this category and this task will set to done");
                                        DatabaseHandler.setStateOfTask(taskNum, 1);
                                        ArrayList<String> assigned = DatabaseHandler.getMembersOfTask(taskNum);
                                        for (String j : assigned)
                                            DatabaseHandler.setPointOfUser(j, DatabaseHandler.getPointsOfUser(j) + 10);
                                    }
                                } else
                                    System.out.println("There is no category in this board");
                            } else
                                System.out.println("This task has already finished");
                        } else
                            System.out.println("This task has not added to this board");
                    } else
                        System.out.println("This task is not assigned to you!");
                } else
                    System.out.println("There is no task with given information");
            } else
                System.out.println("There is not any board with that name in this team");
        } else
            System.out.println("finish board first");
    }


    public static void showFailedAndDoneSelect(String condition) throws SQLException {
        String take = BoardMenuController.getActiveBoard();
        if (take != null)
            showFailedAndDone(condition, take);
        else
            System.out.println("No board is selected");
    }

    public static void showFailedAndDone(String condition, String boardName) throws SQLException {
        if (DatabaseHandler.getBoardState(boardName, TeamMenuController.getTeam().getId()).equals("yes")) {
            ArrayList<Integer> tasksId = DatabaseHandler.getAllTasks();
            for (Integer list : tasksId) {
                BoardMenuController.updateTasks(list);
            }
            if (condition.equals("done")) {
                ArrayList<Integer> arrayList = DatabaseHandler.getDoneTasks(boardName, TeamMenuController.getTeam().getId());
                for (Integer i : arrayList) {
                    System.out.println(i);
                }
            } else {
                ArrayList<Integer> taskIds = DatabaseHandler.getFailedTasks(boardName, TeamMenuController.getTeam().getId());
                for (Integer i : taskIds) {
                    System.out.println(i);
                }
            }
        } else
            System.out.println("finish board first");
    }

    public static void renewFailedTaskSelect(String taskTitle, String username, String deadLine, String category) throws SQLException {
        String take = BoardMenuController.getActiveBoard();
        if (take != null)
            renewFailedTask(taskTitle, username, deadLine, category, take);
        else
            System.out.println("No board is selected");
    }

    public static void renewFailedTask(String taskTitle, String username, String deadLine, String category, String boardName) throws SQLException {
        if (DatabaseHandler.getBoardState(boardName, TeamMenuController.getTeam().getId()).equals("yes")) {
            ArrayList<Integer> tasksId = DatabaseHandler.getAllTasks();
            for (Integer list : tasksId) {
                BoardMenuController.updateTasks(list);
            }
            int taskId = DatabaseHandler.getTaskIdByTaskTitle(taskTitle, TeamMenuController.getTeam().getId());
            int teamId = TeamMenuController.getTeam().getId();
            if (LoginController.getActiveUser().getRole().equals("leader")) {
                if (DatabaseHandler.doesTaskExist(taskTitle, teamId)) {
                    if (DatabaseHandler.getStateOfTask(DatabaseHandler.getTaskIdByTaskTitle(taskTitle, teamId)) == 0) {
                        if (DatabaseHandler.doesBoardExist(boardName, teamId)) {
                            if (category != null) {
                                if (DatabaseHandler.doesCategoryExist(category, boardName, teamId)) {
                                    if (username == null) {
                                        DatabaseHandler.reliveFailedTaskHaveCategory(taskTitle, deadLine, teamId, category);
                                        System.out.println("relived");
                                    } else {
                                        if (DatabaseHandler.isUsernameAssigned(taskId, username)) {
                                            if (DatabaseHandler.isUsernameTeamMate(username, teamId)) {
                                                DatabaseHandler.reliveFailedTask(taskTitle, username, deadLine, category, teamId);
                                                System.out.println("relived");
                                            } else {
                                                System.out.println("Invalid team member");
                                            }
                                        } else
                                            System.out.println("already assigned");
                                    }
                                } else {
                                    System.out.println("Invalid category");
                                }
                            } else {
                                if (username == null) {
                                    DatabaseHandler.reliveFailedTask(taskTitle, deadLine, teamId);
                                    System.out.println("relived");
                                } else {
                                    if (DatabaseHandler.isUsernameAssigned(taskId, username)) {
                                        if (DatabaseHandler.isUsernameTeamMate(username, teamId)) {
                                            DatabaseHandler.reliveFailedTaskJustUsername(taskTitle, deadLine, teamId, username);
                                            System.out.println("relived");
                                        } else {
                                            System.out.println("Invalid team member");
                                        }
                                    } else
                                        System.out.println("already assigned");
                                }

                            }
                        } else {
                            System.out.println("invalid board name");
                        }

                    } else {
                        System.out.println("This task is not in failed section");
                    }

                } else {
                    System.out.println("Invalid task");
                }
            } else {
                System.out.println("You do not have the permission to this action");
            }
        } else
            System.out.println("finish board first");

    }

    public static void showTasksInCategorySelect(String categoryName) throws SQLException {
        String take = BoardMenuController.getActiveBoard();

        if (take != null)
            showTasksInCategory(categoryName, take);
        else
            System.out.println("No board is selected");
    }

    public static void showTasksInCategory(String categoryName, String boardName) throws SQLException {
        if (DatabaseHandler.getBoardState(boardName, TeamMenuController.getTeam().getId()).equals("yes")) {
            if (DatabaseHandler.doesCategoryExist(categoryName, boardName, TeamMenuController.getTeam().getId())) {
                ArrayList<String> tasks = DatabaseHandler.getTaskOfCategory(categoryName, boardName, TeamMenuController.getTeam().getId());
                if (tasks.size() != 0) {
                    for (String str : tasks)
                        System.out.println("Task" + str + "by" + DatabaseHandler.getLeaderByTeamName(TeamMenuController.getTeam().getName())
                                + "is in progress");
                } else
                    System.out.println("no task for this category");
            } else
                System.out.println("no category with this name is existing in this board");
        } else
            System.out.println("finish board first");
    }

    public static void showBoardDetailsSelect() throws SQLException {
        String take = BoardMenuController.getActiveBoard();
        if (take != null)
            showBoardDetails(take);
        else
            System.out.println("No board is selected");
    }

    public static void showBoardDetails(String boardName) throws SQLException {
        if (DatabaseHandler.getBoardState(boardName, TeamMenuController.getTeam().getId()).equals("yes")) {
            ArrayList<Integer> tasksId = DatabaseHandler.getAllTasks();
            for (Integer list : tasksId) {
                BoardMenuController.updateTasks(list);
            }
            if (DatabaseHandler.doesBoardExist(boardName, TeamMenuController.getTeam().getId())) {
                System.out.println(DatabaseHandler.showBoard(boardName, TeamMenuController.getTeam().getId()));
            } else
                System.out.println("board does not exist");
        } else
            System.out.println("finish board first");
    }

    public static String getSelectedTask() {
        return selectedTask;
    }

    public static void setSelectedTask(String selectedTask) {
        BoardMenuController.selectedTask = selectedTask;
    }
}
