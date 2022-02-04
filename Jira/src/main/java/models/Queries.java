package models;

import java.util.Locale;

public class Queries {
    public static final String CREATE_USER =
            "INSERT INTO users (username, password, email, role, notification, logs) VALUES ('%s','%s','%s','%s','[]','[]')";
    public static final String CREATE_TEAM =
            "INSERT INTO teams (name, `creating date`,leader) VALUES ('%s', '%s', '%s')";
    public static final String CREATE_TASK =
            "INSERT INTO tasks (id,title,`creation date`,`deadline date`,priority,state,category,board_name,comments,description,team_id) VALUES (DEFAULT,'%s','%s','%s',1,3,null,null,'[]',null,%d)";
    public static final String CREATE_BOARD =
            "INSERT INTO boards (name, team_id, categories, finished) VALUES ('%s',%d, '[]', 'no')";
    public static final String DOES_USERNAME_EXIST =
            "SELECT * FROM users WHERE username = '%s'";
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
            "SELECT %s FROM `username-team_id` `u-ti` JOIN teams t on t.id = `u-ti`.team_id WHERE %s = '%s'  AND confirmation = 'yes' ORDER BY %s";
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
            "SELECT name FROM teams WHERE confirmation = 'no' ORDER BY `creating date` DESC";
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
            "SELECT leader FROM tasks JOIN teams ON tasks.team_id = teams.id WHERE tasks.id = %d";

    //change task options
    public static final String UPDATE_PRIORITY =
            "UPDATE tasks SET priority = %d WHERE id = %d";
    public static final String UPDATE_DESCRIPTION =
            "UPDATE tasks SET description = '%s' WHERE id = %d";
    public static final String UPDATE_TITLE =
            "UPDATE tasks SET title = '%s' WHERE id = %d";

    public static final String ADD_LOGS =
            "UPDATE users SET logs = '%s' WHERE username = '%s'";

    public static final String ROAD_MAP =
            "SELECT title, GREATEST(LEAST(100*(now() - `creation date`)/(`deadline date` - `creation date`), 100),0) AS pecent " +
                    "FROM tasks WHERE team_id = %d ORDER BY `creation date` ASC, title";
    public static final String GET_CHATROOM =
            "SELECT chatroom FROM teams WHERE id = %d";

    public static final String ADD_CHAT =
            "UPDATE teams SET chatroom = '%s' WHERE id = %d";
    public static final String GET_TASKS_BY_TEAM_ID =
            "SELECT title,ta.id,`creating date`,`deadline date`,priority FROM teams te JOIN tasks ta ON ta.team_id = te.id WHERE team_id = %d";
    public static final String GET_USERS_ASSIGNED_TASK =
            "SELECT username FROM `username-task_id` WHERE task_id = %d";
    public static final String DOES_BOARD_EXIST =
            "SELECT * FROM boards WHERE team_id = %d AND name = '%s'";
    public static final String REMOVE_BOARD =
            "DELETE FROM boards WHERE team_id = %d AND name = '%s'";
    public static final String GET_CATEGORIES =
            "SELECT categories FROM boards WHERE team_id = %d AND name = '%s'";
    public static final String ADD_CATEGORIES =
            "UPDATE boards SET categories = '%s' WHERE team_id = %d AND name = '%s'";
    public static final String UPDATE_FINISHED_BOARD =
            "UPDATE boards SET finished = 'yes' WHERE team_id = %d AND name = '%s'";
    public static final String ADD_TASK_TO_BOARD =
            "UPDATE tasks SET board_name = '%s' WHERE id = %d";
    public static final String DOES_TASK_ADDED_TO_BOARD =
            "SELECT * FROM tasks WHERE id = %d AND board_name = '%s'";
    public static final String DOES_DEADLINE_PASSED =
            "SELECT * FROM tasks WHERE id = %d AND (`deadline date` < NOW())";
    public static final String IS_TASK_ASSIGNED =
            "SELECT * FROM `username-task_id` WHERE task_id = %d";
    public static final String USER_TEAMS_NUMBER =
            "SELECT team_id FROM `username-team_id` WHERE username = '%s'";
    public static final String CHANGE_ROLE =
            "UPDATE users SET role = '%s' WHERE username = '%s'";
    public static final String GET_TASK_ID_BY_TASK_TITLE =
            "SELECT id FROM tasks WHERE team_id = %d AND title = '%s'";
    public static final String SHOW_BOARD =
            "SELECT leader FROM boards b JOIN teams t ON b.team_id = t.id WHERE b.name = '%s' AND team_id = %d";
    public static final String GET_DONE_TASKS_OF_BOARD =
            "SELECT id FROM tasks WHERE board_name = '%s' AND team_id = %d AND state = 1";
    public static final String GET_DONE_TASKS_TITLE_OF_BOARD =
            "SELECT title FROM tasks WHERE board_name = '%s' AND team_id = %d AND state = 1";
    public static final String GET_FAILED_TASKS_OF_BOARD =
            "SELECT id FROM tasks WHERE board_name = '%s' AND team_id = %d AND state = 0";
    public static final String GET_FAILED_TASKS_TITLE_OF_BOARD =
            "SELECT title FROM tasks WHERE board_name = '%s' AND team_id = %d AND state = 0";
    public static final String GET_TASKS_BY_BOARD_NAME_TEAM_ID =
            "SELECT title, category, description,`creation date`,`deadline date`,state,id FROM tasks WHERE board_name = '%s' AND team_id = %d AND priority = %d";
    public static final String ADD_MEMBER_TO_TEAM =
            "INSERT INTO `username-team_id` (username, team_id) VALUES ('%s', %d)";
    public static final String REMOVE_USER_FROM_TEAM =
            "DELETE FROM `username-team_id` WHERE username = '%s' AND team_id = %d";
    public static final String REMOVE_USER_FROM_TASK_OF_TEAM_BY_TEAM_ID =
            "DELETE FROM `username-task_id` WHERE username = '%s' AND task_id = %d";
    public static final String GET_USER_ROLE =
            "SELECT role FROM users WHERE username = '%s'";
    public static final String ADD_SUSPENDS =
            "INSERT INTO suspends (username, team_Id) VALUES ('%s', %d)";
    public static final String GET_CATEGORY_BY_TASK_ID =
            "SELECT category FROM tasks WHERE id = %d";
    public static final String SET_CATEGORY =
            "UPDATE tasks SET category = '%s' WHERE id = %d";
    public static final String GET_TASK_STATE =
            "SELECT state FROM tasks WHERE id = %d";
    public static final String GET_POINT =
            "SELECT point FROM users WHERE username = '%s'";
    public static final String SET_POINT =
            "UPDATE users SET point = %d WHERE username = '%s'";
    public static final String GET_TASK_COMMENT =
            "SELECT comments FROM tasks WHERE id = %d";
    public static final String ADD_COMMENT =
            "UPDATE tasks SET comments = '%s' WHERE id = %d";
    public static final String SET_STATE =
            "UPDATE tasks SET state = %d WHERE id = %d";
    public static final String IS_MEMBER_IN_TEAM =
            "SELECT * FROM `username-team_id` WHERE username = '%s' AND team_id = %d";
    public static final String GET_DEADLINES =
            "SELECT `deadline date`, '*' AS type, team_id FROM tasks JOIN `username-task_id` ON tasks.id = `username-task_id`.task_id WHERE state = 3 AND DATEDIFF(`deadline date`, NOW()) > 10 AND username = '%s'\n" +
                    "UNION\n" +
                    "SELECT `deadline date`, '**' AS type, team_id FROM tasks JOIN `username-task_id` ON tasks.id = `username-task_id`.task_id WHERE state = 3 AND DATEDIFF(`deadline date`,NOW()) >= 4 AND DATEDIFF(`deadline date`, NOW()) <= 10 AND username = '%s'\n" +
                    "UNION\n" +
                    "SELECT `deadline date`, '***' AS type, team_id FROM tasks JOIN `username-task_id` ON tasks.id = `username-task_id`.task_id WHERE state = 3 AND DATEDIFF(`deadline date`,NOW()) < 4 AND username = '%s'\n" +
                    "ORDER BY `deadline date` ASC";
    public static final String GET_TASK =
            "SELECT title,ta.id,`creating date`,`deadline date`,priority FROM teams te JOIN tasks ta ON ta.team_id = te.id WHERE ta.id = %d";
    public static final String GET_TEAM_ID_BY_USERNAME_AND_TEAM_NAME =
            "SELECT id FROM teams  WHERE name = '%s'";
    public static final String DOES_TEAM_EXIST_FOR_USER =
            "SELECT id FROM teams JOIN `username-team_id` ON teams.id = `username-team_id`.team_id WHERE username = '%s' AND name = '%s'";
    public static final String GET_TEAM_NAME_BY_TEAM_ID =
            "SELECT name FROM teams WHERE id = %d";
    public static final String GET_TEAM_ID_BY_TASK_ID =
            "SELECT team_id FROM tasks WHERE id = %d";
    public static final String DOES_TEAM_NAME_EXIST =
            "SELECT name FROM teams WHERE name = '%s'";
    public static final String GET_BOARD_STATE =
            "SELECT finished FROM boards WHERE team_id = %d AND name = '%s'";
    public static final String CHANGE_LEADER_OF_TEAM =
            "UPDATE teams SET leader = '%s' WHERE id = %d";
    public static final String CHANGE_USERNAME_IN_NOTIFICATIONS =
            "UPDATE notification SET username = '%s' WHERE username = '%s'";
    public static final String CHANGE_USERNAME_IN_TEAMS =
            "UPDATE teams SET leader = '%s' WHERE leader = '%s'";

    public static final String CHANGE_USERNAME_IN_USERNAME_TASK_ID =
            "UPDATE `username-task_id` SET username = '%s' WHERE username = '%s'";
    public static final String CHANGE_USERNAME_IN_USERNAME_TEAM_ID =
            "UPDATE `username-team_id` SET username = '%s' WHERE username = '%s'";
    public static final String GET_TASK_OF_CATEGORY =
            "SELECT title FROM tasks WHERE category = '%s' AND team_id = %d AND board_name = '%s'";
    public static final String GET_BOARDS_OF_TEAM =
            "SELECT name FROM boards WHERE team_id = %d";
    public static final String GET_DONE_TASKS_TITLE_OF_TEAM =
            "SELECT title FROM tasks WHERE team_id = %d AND state = 1";
    public static final String GET_FAILED_TASKS_TITLE_OF_TEAM =
            "SELECT title FROM tasks WHERE team_id = %d AND state = 0";
    public static final String GET_IN_PROGRESS_TASKS_TITLE_OF_TEAM =
            "SELECT title FROM tasks WHERE team_id = %d AND state = 3";
    public static final String GET_TASKS_TITLE_OF_TEAM =
            "SELECT title FROM tasks WHERE team_id = %d";
    public static final String GET_TASK_BY_USERNAME =
            "SELECT id, title FROM tasks JOIN `username-task_id` `u-ti` on tasks.id = `u-ti`.task_id WHERE username = '%s'";
    public static final String GET_TASK_BY_USERNAME_SORTED_BY_DEADLINE =
            "SELECT id, title FROM tasks JOIN `username-task_id` `u-ti` on tasks.id = `u-ti`.task_id WHERE username = '%s' ORDER BY `deadline date`";
    public static final String GET_TASK_BY_USERNAME_SORTED_BY_TITLE =
            "SELECT id, title FROM tasks JOIN `username-task_id` `u-ti` on tasks.id = `u-ti`.task_id WHERE username = '%s' ORDER BY title";
    public static final String GET_TASK_BY_USERNAME_SORTED_BY_PRIORITY =
            "SELECT id, title FROM tasks JOIN `username-task_id` `u-ti` on tasks.id = `u-ti`.task_id WHERE username = '%s' ORDER BY priority";
}