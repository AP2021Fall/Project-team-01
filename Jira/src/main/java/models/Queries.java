package models;

import java.util.Locale;

public class Queries {
    public static final String CREATE_USER =
            "INSERT INTO users (username, password, email, role, notification) VALUES ('%s','%s','%s','%s','[]')";
    public static final String CREATE_TEAM =
            "INSERT INTO teams (name, `creating date`,leader) VALUES ('%s', '%s', '%s')";
    public static final String CREATE_TASK =
            "INSERT INTO tasks VALUES (DEFAULT,'%s','%s','%s','%s','%s','%s')";
    public static final String CREATE_BOARD =
            "INSERT INTO boards VALUES (DEFAULT, '[]', '[]', '[]')";
    public static final String DOES_USERNAME_EXIST =
            "SELECT username FROM users WHERE username = '%s'";
    public static final String DOES_TEAM_EXIST =
            "SELECT leader FROM teams WHERE name = '%s'";
    public static final String IS_TEAM_IN_PENDING =
            "SELECT leader FROM teams WHERE name = '%s' AND confirmation = 'no'";
    public static final String DOES_Email_EXIST =
            "SELECT email FROM users WHERE email = '%s'";
    public static final String CHANGE_PASSWORD =
            "UPDATE users SET password = '%s' WHERE username = '%s'";
    public static final String CHANGE_USERNAME =
            "UPDATE users SET username = '%s' WHERE username = '%s'";
    public static final String GET_NOTIFICATION =
            "SELECT message FROM notification WHERE username = '%s'";
    public static final String GET_SELL =
            "SELECT %s FROM %s WHERE %s = '%s'";
    public static final String GET_TEAMS_MEMBERS =
            "SELECT %s FROM `username-team_id` `u-ti` JOIN teams t on t.id = `u-ti`.team_id WHERE %s = '%s' ORDER BY %s";
    public static final String GET_LOGS =
            "SELECT logs FROM users WHERE username = '%s'";
    public static final String SEND_NOTIFICATION_TO_USER =
            "INSERT INTO notification (username, message) VALUES ('%s', '%s')";
    //ban member
    public static final String DELETE_FROM_USERS =
            "DELETE FROM users WHERE username = '%s'";
    public static final String DELETE_FROM_NOTIFICATION =
            "DELETE FROM notification WHERE username = '%s'";
    public static final String DELETE_FROM_USERNAME_TEAM_ID_BY_USERNAME =
            "DELETE FROM `username-team_id` WHERE username = '%s'";
    public static final String DELETE_FROM_USERNAME_TASK_ID_BY_USERNAME =
            "DELETE FROM `username-task_id` WHERE username = '%s'";
    //ban leader
    public static final String GET_TEAM_ID_BY_USERNAME =
            "SELECT team_id FROM `username-team_id` WHERE username = '%s'";
    public static final String GET_TASK_ID_BY_TEAM_ID =
            "SELECT id FROM tasks WHERE team_id = '%s'";
    public static final String DELETE_FROM_USERNAME_TASK_ID_BY_TASK_ID =
            "DELETE FROM `username-task_id` WHERE task_id = %d";
    public static final String DELETE_FROM_USERNAME_TEAM_ID_BY_TEAM_ID =
            "DELETE FROM `username-team_id` WHERE team_id = %d";
    public static final String DELETE_TEAM =
            "DELETE FROM teams WHERE id = %d";
    public static final String DELETE_TASKS =
            "DELETE FROM tasks WHERE id = %d";
    public static final String DELETE_BOARD_BY_TEAM_ID =
            "DELETE FROM tasks WHERE team_id = %d";
    public static final String GET_ALL_USERNAMES =
            "SELECT username FROM users";
    public static final String GET_PENDING_TEAMS =
            "SELECT * FROM teams WHERE confirmation = 'no' ORDER BY `creating date` DESC";
    public static final String ACCEPT_TEAM =
            "UPDATE teams SET confirmation = 'yes' WHERE name = '%s'";
    public static final String REJECT_TEAM =
            "DELETE FROM teams WHERE name = '%s'";
    public static final String showScoreBoard =
            "SELECT u.username, point FROM users u JOIN `username-team_id` u2 ON u.username = u2.username" +
                    " JOIN teams t ON id = u2.team_id WHERE t.name = '%s' ORDER BY point DESC, username";

    public static final String GET_CREATING_DATE_BY_TASK_ID =
            "SELECT `creation date` FROM tasks WHERE id = %d";
    public static final String SET_DEAD_LINE_BY_TASK_ID =
            "UPDATE tasks SET `deadline date` = '%s' WHERE id = %d";
    public static final String ASSIGN_USER =
            "INSERT INTO `username-task_id` (username, task_id) VALUES ('%s',%d)";
    public static final String REMOVE_USER_FROM_TASK =
            "DELETE FROM `username-task_id` WHERE username = '%s' AND task_id = %d";
    public static final String IS_USERNAME_ASSIGNED =
            "SELECT username FROM `username-task_id` WHERE username = '%s' AND task_id = %d";
    public static final String DOES_TASK_EXIST =
            "SELECT id FROM tasks WHERE id = %d";
    public static final String GET_TASK_LEADER_BY_TASK_ID =
            "";
}
