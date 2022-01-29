package controller;

import models.DatabaseHandler;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TasksPageController {
    public static String editTitle(int id, String newTitle) throws SQLException {
        if (DatabaseHandler.doesTaskExist(id)) {
            if (LoginController.getActiveUser().getUsername().equals(DatabaseHandler.getTaskLeaderByTaskId(id))) {
                DatabaseHandler.changeTaskTitle(id, newTitle);
                return "title updated successfully!";
            }
            return "you don't have access to do this action!";
        }
        return "task with id: " + id + " doesn't exist!";
    }

    public static String editDescription(int id, String newDescription) throws SQLException {
        if (DatabaseHandler.doesTaskExist(id)) {
            if (LoginController.getActiveUser().getUsername().equals(DatabaseHandler.getTaskLeaderByTaskId(id))) {
                DatabaseHandler.changeTaskDescription(id, newDescription);
                return "Description updated successfully!";
            }
            return "you don't have access to do this action!";
        }
        return "task with id: " + id + " doesn't exist!";
    }

    public static String editPriority(int id, int newPriority) throws SQLException {
        if (DatabaseHandler.doesTaskExist(id)) {
            if (LoginController.getActiveUser().getUsername().equals(DatabaseHandler.getTaskLeaderByTaskId(id))) {
                DatabaseHandler.changeTaskPriority(id, newPriority);
                return "Priority updated successfully!";
            }
            return "you don't have access to do this action!";
        }
        return "task with id: " + id + " doesn't exist!";
    }

    public static String editDeadline(int taskId, String newdeadline) throws SQLException {
        if (DatabaseHandler.doesTaskExist(taskId)) {
            if (LoginController.getActiveUser().getUsername().equals(DatabaseHandler.getTaskLeaderByTaskId(taskId))) {
                if (isDeadlineValid(DatabaseHandler.getCreationDateByTaskId(taskId), newdeadline) != null) {
                    DatabaseHandler.setDeadline(taskId, isDeadlineValid(DatabaseHandler.getCreationDateByTaskId(taskId), newdeadline));
                    return "deadline changed successfully";
                }
                return "new deadline is invalid";
            }
            return "you don't have access to do this action!";
        }
        return "task with id: " + taskId + " doesn't exist!";
    }

    public static String addAssignedUser(int taskId, String username) throws SQLException {
        if (DatabaseHandler.doesTaskExist(taskId)) {
            if (LoginController.getActiveUser().getUsername().equals(DatabaseHandler.getTaskLeaderByTaskId(taskId))) {
                if (DatabaseHandler.doesUsernameExist(username)) {
                    if (!DatabaseHandler.isUsernameAssigned(taskId, username)) {
                        DatabaseHandler.assignUser(taskId, username);
                        return "user:" + username + "added successfully!";
                    }
                    return "username:" + username + "already assigned!";
                }
                return "there is not any user with this username:" + username;
            }
            return "you don't have access to do this action!";
        }
        return "task with id: " + taskId + " doesn't exist!";
    }

    public static String removeAssignedUser(int taskId, String username) throws SQLException {
        if (DatabaseHandler.doesTaskExist(taskId)) {
            if (LoginController.getActiveUser().getUsername().equals(DatabaseHandler.getTaskLeaderByTaskId(taskId))) {
                if (DatabaseHandler.isUsernameAssigned(taskId, username)) {
                    DatabaseHandler.removeUserFromTask(taskId, username);
                    return "user:" + username + " removed successfully!";
                }
                return "there is not any user with this username: " + username + " in list!";
            }
            return "you don't have access to do this action!";
        }
        return "task with id: " + taskId + " doesn't exist!";
    }

    //null if newDeadline is invalid & LocalDateTime if is valid
    public static LocalDateTime isDeadlineValid(LocalDateTime creationDate, String deadline) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm");
        try {
            LocalDateTime dead = LocalDateTime.parse(deadline, formatter);
            if (dead.isBefore(now))
                return null;
            if (dead.isBefore(creationDate))
                return null;
            return dead;
        } catch (DateTimeParseException exception) {
            return null;
        }
    }

    public static void showComments(int taskId) throws SQLException {
        System.out.println(DatabaseHandler.showCommentsByTaskId(taskId));
    }

    public static void addComment(int taskId, String comment) throws SQLException {
        DatabaseHandler.addCommentByTaskId(taskId, comment, LoginController.getActiveUser().getUsername());
    }

}
