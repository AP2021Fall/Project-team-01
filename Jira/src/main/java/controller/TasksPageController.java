package controller;

import models.DatabaseHandler;

public class TasksPageController {
    public static String editTitle(int id, String newTitle) {
        if (DatabaseHandler.doesTaskExist(id)) {
            if (LoginController.getActiveUser().getUsername().equals(DatabaseHandler.getTaskLeaderByTaskId(id))) {
                DatabaseHandler.changeTaskTitle(id, newTitle);
                return "title updated successfully!";
            }
            return "you don't have access to do this action!";
        }
        return "task with id: " + id + " doesn't exist!";
    }

    public static String editDescription(int id, String newDescription) {
        if (DatabaseHandler.doesTaskExist(id)) {
            if (LoginController.getActiveUser().getUsername().equals(DatabaseHandler.getTaskLeaderByTaskId(id))) {
                DatabaseHandler.changeTaskDescription(id, newDescription);
                return "Description updated successfully!";
            }
            return "you don't have access to do this action!";
        }
        return "task with id: " + id + " doesn't exist!";
    }

    public static String editPriority(int id, String newPriority) {
        if (DatabaseHandler.doesTaskExist(id)) {
            if (LoginController.getActiveUser().getUsername().equals(DatabaseHandler.getTaskLeaderByTaskId(id))) {
                DatabaseHandler.changeTaskPriority(id, newPriority);
                return "Priority updated successfully!";
            }
            return "you don't have access to do this action!";
        }
        return "task with id: " + id + " doesn't exist!";
    }

    public static String editDeadline() {
    }

    public static String addAssignedUser() {
    }

    public static String removeAssignedUser() {
    }
}
