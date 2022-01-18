package controller.TeamMenuController;

import models.Board;

import java.sql.SQLException;

public class BoardMenuController {
    private static Board activeBoard;

    public static Board getActiveBoard() {
        return activeBoard;
    }

    public static void setActiveBoard(Board activeBoard) {
        BoardMenuController.activeBoard = activeBoard;
    }

    public static void createBoard(String boardName) throws SQLException{
        
    }
    public static void removeBoard(String boardName) throws SQLException{

    }

    public static void selectBoard(String boardName) {
    }
    public static void deselectBoard(){

    }

    public static void addCategory() {
    }

    public static void addCategoryToColumn() {
    }
    public static void completeBoardFirstStep(){
        
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

    public static void showBoard() {
    }
}
