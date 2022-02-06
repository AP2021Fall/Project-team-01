package controller;

import models.DatabaseHandler;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

public class TasksPageController {
    private static HashMap<String, String> taskIdAndTaskTitle = new HashMap<>();

    public static HashMap<String, String> getTaskIdAndTaskTitle() {
        return taskIdAndTaskTitle;
    }
    //    private static String taskIdAndTaskTitle;
//
//    public static String getTaskIdAndTaskTitle() {
//        return taskIdAndTaskTitle;
//    }
//
//    public static void setTaskIdAndTaskTitle(String taskIdAndTaskTitle) {
//        TasksPageController.taskIdAndTaskTitle = taskIdAndTaskTitle;
//    }
    
    public static int getTaskId(){
        String[] strings = getTaskIdAndTaskTitle().split(" ");
        return Integer.parseInt(strings[0]);
    }

    public static String getTaskTitle(){
        String[] strings = TasksPageController.getTaskIdAndTaskTitle().split(" ");
        return strings[1];
    }

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

    public static String editDeadline(int taskId, String newDeadLine) throws SQLException {
        if (DatabaseHandler.doesTaskExist(taskId)) {
            if (LoginController.getActiveUser().getUsername().equals(DatabaseHandler.getTaskLeaderByTaskId(taskId))) {
                if (isDeadlineValid(DatabaseHandler.getCreationDateByTaskId(taskId), newDeadLine) != null) {
                    DatabaseHandler.setDeadline(taskId, isDeadlineValid(DatabaseHandler.getCreationDateByTaskId(taskId), newDeadLine));
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
        if (DatabaseHandler.doesTaskExist(taskId)) {
            if (DatabaseHandler.isUsernameTeamMate(LoginController.getActiveUser().getUsername(), DatabaseHandler.getTeamIdByTaskId(taskId))) {
                System.out.println(DatabaseHandler.showCommentsByTaskId(taskId));
            } else
                System.out.println("this task doesn't belong to your team");
        } else
            System.out.println("task does not exist!");
    }

    public static void addComment(int taskId, String comment) throws SQLException {
        if (DatabaseHandler.doesTaskExist(taskId)) {
            if (DatabaseHandler.isUsernameTeamMate(LoginController.getActiveUser().getUsername(), DatabaseHandler.getTeamIdByTaskId(taskId))) {
                DatabaseHandler.addCommentByTaskId(taskId, comment, LoginController.getActiveUser().getUsername());
                System.out.println("your comment added successfully!");
            } else
                System.out.println("you are not a member of this task's team!");
        } else
            System.out.println("task does not exist!");
    }

}
