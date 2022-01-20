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
                DatabaseHandler.createBoard(boardName , TeamMenuController.getTeam().getName());
            else
                System.out.println("There is already a board with this name");
        } else
            System.out.println("You do not have the permission to do this action!");
    }

    public static void removeBoard(String boardName) throws SQLException {
        if (LoginController.getActiveUser().getUsername().equals("leader")){
            if (DatabaseHandler.doesBoardExist(boardName, TeamMenuController.getTeam().getName()))
                DatabaseHandler.removeBoard(boardName , TeamMenuController.getTeam().getName());
            else
                System.out.println("There is no board with this name");
        }else
            System.out.println("You do not have the permission to do this action!");
    }

    public static void selectBoard(String boardName) {
        if (DatabaseHandler.doesBoardExist(boardName, TeamMenuController.getTeam().getName()))
            setActiveBoard(boardName);
        else
            System.out.println("There is no board with this name");
    }

    public static void deselectBoard() {
        if (DatabaseHandler.doesBoardExist(boardName, TeamMenuController.getTeam().getName())) {
            if(getActiveBoard() != null)
            setActiveBoard(null);
            else
                System.out.println("No board is selected");
        } else
            System.out.println("There is no board with this name");
    }

    public static void addCategory() {
    }

    public static void addCategoryToColumn() {
    }

    public static void completeBoardFirstStep() {

    }

    public static void addTaskToBoard() {
    }

    public static void assignTaskToMemeber() {
    }

    public static void forceTaskToCategory() {
    }

    public static void taskToNext() {
    }

    public static void showTaskInCategory() {
    }

    public static void showFailedAndDone() {
    }

    public static void renewFailedTask() {
    }

}
