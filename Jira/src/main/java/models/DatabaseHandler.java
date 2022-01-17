package models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.sql.*;
import java.time.LocalDateTime;
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

    public static void connectAndInsert(String sql) throws SQLException {
        connect();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }


    public static void createUser(String username, String password, String email, String role) throws SQLException {
        String sql = String.format(Queries.CREATE_USER, username, password, email, role);
        connectAndInsert(sql);
    }

    public static void createTeam(String name, LocalDateTime creatingDate, String leader) throws SQLException {
        String creatingDateJson = new Gson().toJson(creatingDate);
        String sql = String.format(Queries.CREATE_TEAM, name, creatingDateJson, leader);
        connectAndInsert(sql);
    }

    public static void createTask(String title, String description, String priority, LocalDateTime creatingDate,
                                  LocalDateTime deadlineDate, String category) throws SQLException {
        String creatingDateJson = new Gson().toJson(creatingDate);
        String deadlineDateJson = new Gson().toJson(deadlineDate);
        String sql = String.format(Queries.CREATE_TASK, title, description, priority, creatingDateJson, deadlineDateJson,
                category);
        connectAndInsert(sql);
    }

    //TODO when categories initialize
    public static void createBoard() throws SQLException {
        String sql = Queries.CREATE_BOARD;
        connectAndInsert(sql);
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

    public static boolean doesTeamExist(String teamName, String username) throws SQLException {
        
        String sql = String.format(Queries.DOES_TEAM_EXIST_FOR_USER, teamName);
        connect();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        if (result.next()) {
            String leader = result.getString(1);

        }else {
        }
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
        connectAndInsert(sql);
    }

    public static void changeUsername(String oldUsername, String newUsername) throws SQLException {
        String sql = String.format(Queries.CHANGE_USERNAME, newUsername, oldUsername);
        connectAndInsert(sql);
    }

    //    //string or whatever!
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
        connect();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        result.next();
        String json = result.getString(1);
        ArrayList<String> arraylist = new Gson().fromJson(json,
                new TypeToken<List<String>>() {
                }.getType());
        statement.close();
        connection.close();
        return arraylist;
    }

    public static ArrayList<String> getUserTeams(String username) throws SQLException {
        return getTeamsOrMembers("name", "username", username);
    }

    public static ArrayList<String> getMembersByTeamName(String teamName) throws SQLException {
        return getTeamsOrMembers("username", "name", teamName);
    }

    public static ArrayList<String> getTeamsOrMembers(String column, String conditionColumn, String condition) throws SQLException {
        String sql = String.format(Queries.GET_TEAMS_MEMBERS, column, conditionColumn, condition);
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

    public static LocalDateTime getCreationDateByTaskId(int taskId) {
    }

    public static void setDeadline(int taskId, LocalDateTime newDeadline) {
    }

    public static void assignUser(int taskId, String username) {
    }

    public static void removeUserFromTask(int taskId, String username) {
    }

    public static boolean isUsernameAssigned(int taskId, String username) {
    }

    public static boolean doesTaskExist(int taskId) {
    }

    public static String getTaskLeaderByTaskId(int taskId) {
    }

    public static void changeTaskPriority(int id, String newPriority) {

    }

    public static void changeTaskDescription(int id, String newDescription) {
    }

    public static void changeTaskTitle(int id, String newTitle) {
    }

    public static void logLogin (String username, LocalDateTime log){}

    public static ArrayList<String> getTeamByUsername (String username){

    }
}
