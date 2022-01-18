package models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
    private static final String JDB_URL = "jdbc:mysql://localhost:3306/apjira";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection connection;

    public static void connect() throws SQLException {
        connection = DriverManager.getConnection(JDB_URL, USER, PASSWORD);
    }

    public static void connectAndExecute(String sql) throws SQLException {
        connect();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }


    public static void createUser(String username, String password, String email, String role) throws SQLException {
        String sql = String.format(Queries.CREATE_USER, username, password, email, role);
        connectAndExecute(sql);
    }

    public static void createTeam(String name, LocalDateTime creatingDate, String leader) throws SQLException {
        DateTimeFormatter d = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String creatingDateString = creatingDate.format(d);
        String sql = String.format(Queries.CREATE_TEAM, name, creatingDateString, leader);
        connectAndExecute(sql);
    }

    public static void createTask(String title, String description, String priority, LocalDateTime creatingDate,
                                  LocalDateTime deadlineDate, String category) throws SQLException {
        String creatingDateJson = new Gson().toJson(creatingDate);
        String deadlineDateJson = new Gson().toJson(deadlineDate);
        String sql = String.format(Queries.CREATE_TASK, title, description, priority, creatingDateJson, deadlineDateJson,
                category);
        connectAndExecute(sql);
    }

    //TODO when categories initialize
    public static void createBoard() throws SQLException {
        String sql = Queries.CREATE_BOARD;
        connectAndExecute(sql);
    }


    public static boolean doesUsernameExist(String username) throws SQLException {
        connect();
        String sql = String.format(Queries.DOES_USERNAME_EXIST, username);
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        boolean bool = result.next();
        statement.close();
        connection.close();
        return bool;
    }

    public static boolean doesTeamExistForUser(String teamName, String username) throws SQLException {

        String sql = String.format(Queries.DOES_TEAM_EXIST, teamName);
        boolean bool = false;
        connect();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        if (result.next()) {
            String leader = result.getString(1);
            if (username.equals(leader))
                bool = true;
        }
        statement.close();
        connection.close();
        return bool;
    }

    public static boolean isTeamInPending(String teamName) throws SQLException {
        String sql = String.format(Queries.IS_TEAM_IN_PENDING, teamName);
        connect();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        boolean bool = result.next();
        statement.close();
        connection.close();
        return bool;
    }

    public static boolean doesEmailExist(String email) throws SQLException {
        String sql = String.format(Queries.DOES_Email_EXIST, email);
        connect();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        boolean bool = result.next();
        statement.close();
        connection.close();
        return bool;
    }

    public static String getPasswordByUsername(String username) throws SQLException {
        return getSell("users", "password", "username", username);
    }

    public static String getEmailByUsername(String username) throws SQLException {
        return getSell("users", "email", "username", username);
    }

    public static String getRoleByUsername(String username) throws SQLException {
        return getSell("users", "role", "username", username);
    }

    public static String getLeaderByTeamName(String teamName) throws SQLException {
        return getSell("teams", "leader", "name", teamName);
    }

    public static String getSell(String table, String column, String conditionalColumn, String condition) throws SQLException {
        String sql = String.format(Queries.GET_SELL, column, table, conditionalColumn, condition);
        String answer = null;
        connect();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        if (result.next())
            answer = result.getString(1);
        statement.close();
        connection.close();
        return answer;
    }

    public static void changePassword(String username, String newPassword) throws SQLException {
        String sql = String.format(Queries.CHANGE_PASSWORD, newPassword, username);
        connectAndExecute(sql);
    }

    public static void changeUsername(String oldUsername, String newUsername) throws SQLException {
        String sql = String.format(Queries.CHANGE_USERNAME, newUsername, oldUsername);
        connectAndExecute(sql);
    }

    public static ArrayList<LocalDateTime> getLogsByUsername(String username) throws SQLException {
        String sql = String.format(Queries.GET_LOGS, username);
        connect();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        result.next();
        String json = result.getString(1);
        ArrayList<LocalDateTime> arraylist = new Gson().fromJson(json,
                new TypeToken<List<LocalDateTime>>() {
                }.getType());
        statement.close();
        connection.close();
        return arraylist;
    }

    public static ArrayList<String> getNotifications(String username) throws SQLException {
        String sql = String.format(Queries.GET_NOTIFICATION, username);
        return getArraylistString(sql);
    }

    public static ArrayList<String> getUserTeams(String username) throws SQLException {
        return getTeamsOrMembers("name", "username", username, "creating date");
    }

    public static ArrayList<String> getMembersByTeamName(String teamName) throws SQLException {
        return getTeamsOrMembers("username", "name", teamName, "username");
    }

    public static ArrayList<String> getTeamsOrMembers(String column, String conditionColumn,
                                                      String condition, String orderedBy) throws SQLException {
        String sql = String.format(Queries.GET_TEAMS_MEMBERS, column, conditionColumn, condition, orderedBy);
        return getArraylistString(sql);
    }

    private static ArrayList<String> getArraylistString(String sql) throws SQLException {
        ArrayList<String> answer = new ArrayList<>();
        connect();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        while (result.next()) {
            answer.add(result.getString(1));
        }
        statement.close();
        connection.close();
        return answer;
    }

    public static void sendNotificationToUser(String notification, String username) throws SQLException {
        String sql = String.format(Queries.SEND_NOTIFICATION_TO_USER, username, username);
        connectAndExecute(sql);
    }

    public static void sendNotificationToTeam(String notification, String teamName) throws SQLException {
        ArrayList<String> members = getMembersByTeamName(teamName);
        for (String i : members) {
            sendNotificationToUser(notification, i);
        }
    }

    public static ArrayList<Integer> getTeamsIdByUsername(String username) throws SQLException {
        String sql = String.format(Queries.GET_TEAM_ID_BY_USERNAME, username);
        return getIntArraylist(sql);
    }

    public static ArrayList<Integer> getTasksIdByTeamId(int teamId) throws SQLException {
        String sql = String.format(Queries.GET_TASK_ID_BY_TEAM_ID, teamId);
        return getIntArraylist(sql);
    }

    private static ArrayList<Integer> getIntArraylist(String sql) throws SQLException {
        ArrayList<Integer> answer = new ArrayList<>();
        connect();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        while (result.next()) {
            answer.add(result.getInt(1));
        }
        statement.close();
        connection.close();
        return answer;
    }

    private static void deleteBoardByTeamId(int teamId) throws SQLException {
        String sql = String.format(Queries.DELETE_BOARD_BY_TEAM_ID, teamId);
        connectAndExecute(sql);
    }

    private static void deleteNotificationByUsername(String username) throws SQLException {
        String sql = String.format(Queries.DELETE_FROM_NOTIFICATION, username);
        connectAndExecute(sql);
    }

    private static void deleteTaskByTaskID(int taskId) throws SQLException {
        String sql = String.format(Queries.DELETE_TASKS, taskId);
        connectAndExecute(sql);
    }

    private static void deleteTeamByTeamId(int teamId) throws SQLException {
        String sql = String.format(Queries.DELETE_TEAM, teamId);
        connectAndExecute(sql);
    }

    private static void deleteUsernameTaskIdByTaskId(int taskId) throws SQLException {
        String sql = String.format(Queries.DELETE_FROM_USERNAME_TASK_ID_BY_TASK_ID, taskId);
        connectAndExecute(sql);
    }

    private static void deleteUsernameTaskIdByUsername(String username) throws SQLException {
        String sql = String.format(Queries.DELETE_FROM_USERNAME_TASK_ID_BY_USERNAME, username);
        connectAndExecute(sql);
    }

    private static void deleteUsernameTeamIdByTeamId(int teamId) throws SQLException {
        String sql = String.format(Queries.DELETE_FROM_USERNAME_TEAM_ID_BY_TEAM_ID, teamId);
        connectAndExecute(sql);
    }

    private static void deleteUsernameTeamIdByUsername(String username) throws SQLException {
        String sql = String.format(Queries.DELETE_FROM_USERNAME_TEAM_ID_BY_USERNAME, username);
        connectAndExecute(sql);
    }

    private static void deleteFromUsers(String username) throws SQLException {
        String sql = String.format(Queries.DELETE_FROM_USERS, username);
        connectAndExecute(sql);
    }

    public static void banLeader(String username) throws SQLException {
        deleteFromUsers(username);
        deleteNotificationByUsername(username);
        ArrayList<Integer> teamIds = getTeamsIdByUsername(username);
        for (int i : teamIds) {
            deleteUsernameTeamIdByTeamId(i);
            deleteTeamByTeamId(i);
            deleteBoardByTeamId(i);
            ArrayList<Integer> tasksId = getTasksIdByTeamId(i);
            for (int j : tasksId) {
                deleteTaskByTaskID(j);
                deleteUsernameTaskIdByTaskId(j);
            }
        }
    }

    public static void banMember(String username) throws SQLException {
        deleteFromUsers(username);
        deleteNotificationByUsername(username);
        deleteUsernameTaskIdByUsername(username);
        deleteUsernameTeamIdByUsername(username);
    }

    public static ArrayList<String> getAllUsers() throws SQLException {
        String sql = Queries.GET_ALL_USERNAMES;
        return getArraylistString(sql);
    }

    public static void sendNotificationToAll(String notification) throws SQLException {
        ArrayList<String> usernames = getAllUsers();
        for (String i : usernames) {
            sendNotificationToUser(notification, i);
        }
    }

    public static ArrayList<String> getPendingTeams() throws SQLException {
        String sql = Queries.GET_PENDING_TEAMS;
        return getArraylistString(sql);
    }

    public static String acceptPendingTeams(String[] teams) throws SQLException {
        for (String i : teams) {
            if (!isTeamInPending(i))
                return "Some teams are not in pending status! Try again";
        }
        for (String i : teams) {
           String sql = String.format(Queries.ACCEPT_TEAM, i);
           connectAndExecute(sql);
        }
        return "teams Accepted";

    }

//    public static LocalDateTime getCreationDateByTaskId(int taskId) {
//    }
//
//    public static void setDeadline(int taskId, LocalDateTime newDeadline) {
//    }
//
//    public static void assignUser(int taskId, String username) {
//    }
//
//    public static void removeUserFromTask(int taskId, String username) {
//    }
//
//    public static boolean isUsernameAssigned(int taskId, String username) {
//    }
//
//    public static boolean doesTaskExist(int taskId) {
//    }
//
//    public static String getTaskLeaderByTaskId(int taskId) {
//    }
//
//    public static void changeTaskPriority(int id, String newPriority) {
//
//    }
//
//    public static void changeTaskDescription(int id, String newDescription) {
//    }
//
//    public static void changeTaskTitle(int id, String newTitle) {
//    }
//
//    public static void logLogin (String username, LocalDateTime log){}
}
