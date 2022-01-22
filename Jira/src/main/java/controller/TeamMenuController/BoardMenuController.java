package controller.TeamMenuController;

import controller.LoginController;
import models.Board;
import models.DatabaseHandler;

import java.sql.SQLException;
import java.util.ArrayList;

public class BoardMenuController {
    private static String activeBoard = null;

    public static String getActiveBoard() {
        return activeBoard;
    }

    public static void setActiveBoard(String activeBoard) {
        BoardMenuController.activeBoard = activeBoard;
    }
    public static void updateTasks(int taskId) throws SQLException {
       int help = DatabaseHandler.getStateOfTask(taskId);
       if (help == 3){
          if ( DatabaseHandler.doesDeadlinePassed(taskId)){
              DatabaseHandler.setStateOfTask(taskId , 0);
              ArrayList<String>list =  DatabaseHandler.getMembersOfTask(taskId);
              for (String s : list){
                 int i = DatabaseHandler.getPointsOfUser(s);
                 DatabaseHandler.setPointOfUser(s , i-5);
              }
          }
       }

    }
    public static void createBoard(String boardName) throws SQLException {
        if (LoginController.getActiveUser().getUsername().equals("leader")) {
            if (!(DatabaseHandler.doesBoardExist(boardName, TeamMenuController.getTeam().getId())))
                DatabaseHandler.createBoard(boardName, TeamMenuController.getTeam().getId());
            else
                System.out.println("There is already a board with this name");
        } else
            System.out.println("You do not have the permission to do this action!");
    }

    public static void removeBoard(String boardName) throws SQLException {
        if (LoginController.getActiveUser().getUsername().equals("leader")) {
            if (DatabaseHandler.doesBoardExist(boardName, TeamMenuController.getTeam().getId()))
                DatabaseHandler.removeBoard(boardName, TeamMenuController.getTeam().getId());
            else
                System.out.println("There is no board with this name");
        } else
            System.out.println("You do not have the permission to do this action!");
    }

    public static void selectBoard(String boardName) throws SQLException {
        if (DatabaseHandler.doesBoardExist(boardName, TeamMenuController.getTeam().getId()))
            setActiveBoard(boardName);
        else
            System.out.println("There is no board with this name");
    }

    public static void deselectBoard() {
            if (getActiveBoard() != null)
                setActiveBoard(null);
            else
                System.out.println("No board is selected");
    }

    public static void addCategorySelect(String categoryName) throws SQLException {
        String take = BoardMenuController.getActiveBoard();
        if (take != null)
           addCategory(categoryName , take);
        else
            System.out.println("No board is selected");
    }
    public static void addCategory(String categoryName, String boardName) throws SQLException {

        if (LoginController.getActiveUser().getUsername().equals("leader")) {
            if (DatabaseHandler.doesBoardExist(boardName, TeamMenuController.getTeam().getId()))
                DatabaseHandler.addCategory(categoryName, boardName, TeamMenuController.getTeam().getId());
            else
                System.out.println("There is not a board with this name");
        } else
            System.out.println("You do not have the permission to do this action!");
    }

    public static void addCategoryToColumnSelect(String categoryName , String columnNum) throws SQLException {
        String take = BoardMenuController.getActiveBoard();
        if (take != null)
            addCategoryToColumn(categoryName , columnNum , take);
        else
            System.out.println("No board is selected");
    }

    public static void addCategoryToColumn(String categoryName , String columnNum , String boardName) throws SQLException {
        if (LoginController.getActiveUser().getUsername().equals("leader")) {
            if (DatabaseHandler.doesBoardExist(boardName, TeamMenuController.getTeam().getId())) {
                int help =  DatabaseHandler.numOfBoardCategories(boardName , TeamMenuController.getTeam().getId());
                if ( help >= Integer.parseInt(columnNum)- 1 && help > 0)
                DatabaseHandler.addCategoryToColumn(categoryName, Integer.parseInt(columnNum), boardName, TeamMenuController.getTeam().getId());
                else
                    System.out.println("wrong column");
            } else
                System.out.println("There is not a board with this name");
        } else
            System.out.println("You do not have the permission to do this action!");
    }
    public static void completeBoardFirstStepSelect() throws SQLException {
        String take = BoardMenuController.getActiveBoard();
        if (take != null)
            completeBoardFirstStep(take);
        else
            System.out.println("No board is selected");
    }
    public static void completeBoardFirstStep( String boardName) throws SQLException {
        if (LoginController.getActiveUser().getUsername().equals("leader")) {
            if (DatabaseHandler.doesBoardExist(boardName, TeamMenuController.getTeam().getId())) {
                if (DatabaseHandler.numOfBoardCategories(boardName , TeamMenuController.getTeam().getId()) != 0)
                DatabaseHandler.finishBoard(boardName, TeamMenuController.getTeam().getId());
                else
                    System.out.println("Please make a category first");
            }
            else
                System.out.println("There is no board with this name");
        } else
            System.out.println("You do not have the permission to do this action!");
    }
    public static void addTaskToBoardSelect(String taskId) throws SQLException {
        String take = BoardMenuController.getActiveBoard();
        if (take != null)
            addTaskToBoard(taskId,take);
        else
            System.out.println("No board is selected");
    }
    public static void addTaskToBoard(String taskId, String boardName) throws SQLException {
        if (LoginController.getActiveUser().getUsername().equals("leader")) {
            int taskNum = Integer.parseInt(taskId);
            if (DatabaseHandler.doesBoardExist(boardName, TeamMenuController.getTeam().getId())) {
                if (DatabaseHandler.doesTaskExist(taskNum)) {
                    if (LoginController.getActiveUser().getUsername().equals(DatabaseHandler.getTaskLeaderByTaskId(taskNum))) {
                        if (!(DatabaseHandler.doesTaskAddedToBoard(taskNum , boardName))){
                            if (!(DatabaseHandler.doesDeadlinePassed(taskNum))) {
                                if (DatabaseHandler.isTaskAssigned(taskNum)) {
                                    DatabaseHandler.addTaskToBoard(taskNum, boardName);
                                }else
                                    System.out.println("Please assign this task to someone first");
                            }else
                                System.out.println("The deadline of this task has already passed");
                        }else
                            System.out.println("This task has already been added to this board");
                    }else
                    System.out.println("you don't have access to do this action!");
                }else
                    System.out.println("Invalid task id!");
            }
            else
                System.out.println("There is not any board with that name in this team");
        } else
            System.out.println("You do not have the permission to do this action!");

    }
    public static void assignTaskToMemberSelect(String teamMember , String taskId) throws SQLException {
        String take = BoardMenuController.getActiveBoard();
        if (take != null)
            assignTaskToMember(teamMember,taskId,take);
        else
            System.out.println("No board is selected");
    }
    public static void assignTaskToMember(String teamMember , String taskId , String boardName) throws SQLException {
        if (LoginController.getActiveUser().getUsername().equals("leader")) {
            int taskNum = Integer.parseInt(taskId);
            if (DatabaseHandler.doesBoardExist(boardName, TeamMenuController.getTeam().getId())) {
                if (DatabaseHandler.doesTaskExist(taskNum)) {
                    if (LoginController.getActiveUser().getUsername().equals(DatabaseHandler.getTaskLeaderByTaskId(taskNum))) {
                        if ((DatabaseHandler.doesTaskAddedToBoard(taskNum , boardName))){
                            if (!(DatabaseHandler.isTaskInDoneCategory(taskNum))) {
                                if (DatabaseHandler.doesTeamExistForUser(TeamMenuController.getTeam().getName() , teamMember))
                                    DatabaseHandler.assignUser(taskNum , teamMember);
                                else
                                    System.out.println("Invalid teammate");
                            }else
                                System.out.println("This task has already finished");
                        }else
                            System.out.println("This task has not added to this board");
                    }else
                        System.out.println("you don't have access to do this action!");
                }else
                    System.out.println("Invalid task id!");
            }
            else
                System.out.println("There is not any board with that name in this team");
        } else
            System.out.println("You do not have the permission to do this action!");
    }
    public static void forceTaskToCategorySelect(){

    }
    public static void forceTaskToCategory(String category , String taskTitle , String boardName) throws SQLException {

        if (DatabaseHandler.doesBoardExist(boardName, TeamMenuController.getTeam().getId())) {
            if (DatabaseHandler.doesTaskExist(DatabaseHandler.getTaskIdByTaskTitle(taskTitle,TeamMenuController.getTeam().getId()))) {
                int taskNum = DatabaseHandler.getTaskIdByTaskTitle(taskTitle , TeamMenuController.getTeam().getId());
                if (DatabaseHandler.isUsernameAssigned(taskNum , LoginController.getActiveUser().getUsername())) {
                    if ((DatabaseHandler.doesTaskAddedToBoard(taskNum , boardName))){
                        if (DatabaseHandler.doesCategoryExist(category , boardName , TeamMenuController.getTeam().getId()))
                        if (!(DatabaseHandler.isTaskInDoneCategory(taskNum))) {
                            DatabaseHandler.addToCategory(category, taskTitle , boardName ,TeamMenuController.getTeam().getId());
                        }else
                            System.out.println("This task has already finished");
                    }else
                        System.out.println("This task has not added to this board");
                }else
                    System.out.println("This task is not assigned to you!");
            }else
                System.out.println("There is no task with given information");
        }
        else
            System.out.println("There is not any board with that name in this team");
    }
    public static void taskToNextSelect(){
    }
    public static void taskToNext(String taskTitle , String boardName) throws SQLException {

        if (DatabaseHandler.doesBoardExist(boardName, TeamMenuController.getTeam().getId())) {
            if (DatabaseHandler.doesTaskExist(DatabaseHandler.getTaskIdByTaskTitle(taskTitle,TeamMenuController.getTeam().getId())))  {
                int taskNum = DatabaseHandler.getTaskIdByTaskTitle(taskTitle, TeamMenuController.getTeam().getId());
                if (DatabaseHandler.isUsernameAssigned(taskNum, LoginController.getActiveUser().getUsername())) {
                    if ((DatabaseHandler.doesTaskAddedToBoard(taskNum, boardName))) {
                        if (!(DatabaseHandler.isTaskInDoneCategory(taskNum))) {
                            String categoryName = DatabaseHandler.getCategory(DatabaseHandler.getTaskIdByTaskTitle(taskTitle ,TeamMenuController.getTeam().getId()));
                            ArrayList<String> help = DatabaseHandler.getCategories(boardName , TeamMenuController.getTeam().getId());
                            if (help.size() != 0) {
                                int i = help.indexOf(categoryName);
                                if (i != help.size()-1) {
                                    String passToMethod = help.get(i + 1);
                                    DatabaseHandler.addToCategory(passToMethod, taskTitle, boardName, TeamMenuController.getTeam().getId());
                                }else
                                    System.out.println("There is no category after this category");
                            }else
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
    }
    public static void showTaskInCategorySelect( String categoryName){

    }
    public static void showTaskInCategory( String categoryName , String boardName) {

    }
    public static void showFailedAndDoneSelect(){

    }
    public static void showFailedAndDone( String condition , String boardName) throws SQLException {
        if (condition.equals("done")){
            DatabaseHandler.getDoneTasks(boardName  , TeamMenuController.getTeam().getId());
        }else{
            DatabaseHandler.getFailedTasks(boardName , TeamMenuController.getTeam().getId());
        }
    }
    public static void renewFailedTaskSelect(){

    }
    public static void renewFailedTask() {

    }
    public static void showBoardDetailsSelect(){

    }
    public static void showBoardDetails(String boardName) throws SQLException {
        if (DatabaseHandler.doesBoardExist(boardName, TeamMenuController.getTeam().getId())) {
            System.out.println(DatabaseHandler.showBoard(boardName, TeamMenuController.getTeam().getId()));
        } else
            System.out.println("board does not exist");
    }
}
