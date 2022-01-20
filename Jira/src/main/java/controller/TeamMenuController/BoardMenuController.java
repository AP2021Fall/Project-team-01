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

    public static void createBoard(String boardName) throws SQLException {
        if (LoginController.getActiveUser().getUsername().equals("leader")) {
            if (!(DatabaseHandler.doesBoardExist(boardName, TeamMenuController.getTeam().getName())))
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

    public static void selectBoard(String boardName) {
        if (DatabaseHandler.doesBoardExist(boardName, TeamMenuController.getTeam().getId()))
            setActiveBoard(boardName);
        else
            System.out.println("There is no board with this name");
    }

    public static void deselectBoard() {
        if (DatabaseHandler.doesBoardExist(boardName, TeamMenuController.getTeam().getId())) {
            if (getActiveBoard() != null)
                setActiveBoard(null);
            else
                System.out.println("No board is selected");
        } else
            System.out.println("There is no board with this name");
    }
    public static void addCategorySelect(String categoryName){
        String take = BoardMenuController.getActiveBoard();
        if (take != null)
           addCategory(categoryName , take);
        else
            System.out.println("No board is selected");
    }
    public static void addCategory(String categoryName, String boardName) {

        if (LoginController.getActiveUser().getUsername().equals("leader")) {
            if (DatabaseHandler.doesBoardExist(boardName, TeamMenuController.getTeam().getName()))
                DatabaseHandler.addCategory(categoryName, boardName, TeamMenuController.getTeam().getId());
            else
                System.out.println("There is not a board with this name");
        } else
            System.out.println("You do not have the permission to do this action!");
    }

    public static void addCategoryToColumnSelect(String categoryName , String columnNum){
        String take = BoardMenuController.getActiveBoard();
        if (take != null)
            addCategoryToColumn(categoryName , columnNum , take);
        else
            System.out.println("No board is selected");
    }

    public static void addCategoryToColumn(String categoryName , String columnNum , String boardName) {
        if (LoginController.getActiveUser().getUsername().equals("leader")) {
            if (DatabaseHandler.doesBoardExist(boardName, TeamMenuController.getTeam().getName())) {
                int help =  DatabaseHandler.numOfBoardCategories(boardName , TeamMenuController.getTeam().getId());
                if ( help >= Integer.parseInt(columnNum)- 1 && help > 0)
                DatabaseHandler.addCategoryToColumn(categoryName, columnNum, boardName, TeamMenuController.getTeam().getId());
                else
                    System.out.println("wrong column");
            } else
                System.out.println("There is not a board with this name");
        } else
            System.out.println("You do not have the permission to do this action!");
    }
    public static void completeBoardFirstStepSelect(){
        String take = BoardMenuController.getActiveBoard();
        if (take != null)
            completeBoardFirstStep(take);
        else
            System.out.println("No board is selected");
    }
    public static void completeBoardFirstStep( String boardName) {
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
    public static void addTaskToBoardSelect(){

    }
    public static void addTaskToBoard() {
    }
    public static void assignTaskToMemberSelect(){

    }
    public static void assignTaskToMember() {
    }
    public static void forceTaskToCategorySelect(){

    }
    public static void forceTaskToCategory() {
    }
    public static void taskToNextSelect(){

    }
    public static void taskToNext() {
    }
    public static void showTaskInCategorySelect(){

    }
    public static void showTaskInCategory() {
    }
    public static void showFailedAndDoneSelect(){

    }
    public static void showFailedAndDone() {
    }
    public static void renewFailedTaskSelect(){

    }
    public static void renewFailedTask() {
    }
    public static void showBoardDetailsSelect(){

    }
    public static void showBoardDetails(){

    }
}
