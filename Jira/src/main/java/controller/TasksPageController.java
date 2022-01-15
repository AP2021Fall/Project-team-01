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

    public static String editDeadline(int taskId, String newdeadline) {
        if (DatabaseHandler.doesTaskExist(id)) {
            if (LoginController.getActiveUser().getUsername().equals(DatabaseHandler.getTaskLeaderByTaskId(id))) {
                if (isDeadlineValid(DatabaseHandler.getCreationDateByTaskId(taskId), newdeadline) != null) {
                    DatabaseHandler.setDeadline(taskId, isDeadlineValid(DatabaseHandler.getCreationDateByTaskId(taskId), newdeadline));
                    return "deadline changed successfully";
                }
                return "new deadline is invalid";
            }
            return "you don't have access to do this action!";
        }
        return "task with id: " + id + " doesn't exist!";
    }

    public static String addAssignedUser(int taskId, String username) {
        if (DatabaseHandler.doesTaskExist(id)) {
            if (LoginController.getActiveUser().getUsername().equals(DatabaseHandler.getTaskLeaderByTaskId(id))) {
                if (DatabaseHandler.doesUsernameExist(username)) {
                    if (!DatabaseHandler.isUsernameAssigned(taskId, username)) {
                        DatabaseHandler.assignUser(taskId, username);
                        return "user:" + username + "added successfully!";
                        //send notif
                    }
                    return "username:" + username + "already assigned!";
                }
                return "there is not any user with this username:" + username;
            }
            return "you don't have access to do this action!";
        }
        return "task with id: " + id + " doesn't exist!";
    }

    public static String removeAssignedUser(int taskId, String username) {
        if (DatabaseHandler.doesTaskExist(id)) {
            if (LoginController.getActiveUser().getUsername().equals(DatabaseHandler.getTaskLeaderByTaskId(id))) {
                if (DatabaseHandler.isUsernameAssigned(taskId, username)) {
                    DatabaseHandler.removeUserFromTask(taskId, username);
                    return "user:" + username + "removed successfully!";
                }
                return "there is not any user with this username:" + username + "in list!";
            }
            return "you don't have access to do this action!";
        }
        return "task with id: " + id + " doesn't exist!";
    }

    //for edit deadline func
    private static LocalDateTime isDeadlineValid(LocalDateTime creationDate, String deadline) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm");

        try {
            LocalDateTime dead = LocalDateTime.parse(deadline, formatter);
            if (dead.isBefore(now))
                return null;
            if (dead.isBefore(creationDateTime))
                return null;

            return dead;
        } catch (DateTimeParseException exception) {
            return null;
        }
    }

}
