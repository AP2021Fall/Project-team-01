package models;

import com.google.gson.Gson;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

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
        String sql = String.format(Queries.CREATE_USER, username,password,email,role);
        connectAndInsert(sql);
    }

    public static void createTeam(String name, LocalDateTime creatingDate) throws SQLException {
        String creatingDateJson = new Gson().toJson(creatingDate);
        String sql = String.format(Queries.CREATE_TEAM, name, creatingDateJson);
        connectAndInsert(sql);
    }

    public static void createTask(String title, String description, String priority, LocalDateTime creatingDate,
                                  LocalDateTime deadlineDate, String category) throws SQLException {
        String creatingDateJson = new Gson().toJson(creatingDate);
        String deadlineDateJson = new Gson().toJson(deadlineDate);
        String sql = String.format(Queries.CREATE_TASK,title,description,priority,creatingDateJson,deadlineDateJson,
                category);
        connectAndInsert(sql);
    }

    //TODO when categories initialize
    public static void createBoard() throws SQLException {
        String sql = Queries.CREATE_BOARD;
        connectAndInsert(sql);
    }

    public static boolean doesUsernameExist(String username){}
    public static boolean doesEmailExist(String email){}
    public static String getPasswordByUsername(String username){}
    public static String getEmailByUsername(String username){}
    public static String getRoleByUsername(String username){}
    public static void changePassword(String username, String newPassword){}
    public static void changeUsername(String oldUsername, String newPassword){}

    //string or whatever!
    public static String getLogsByUsername(String username){}
    public static String getNotifications(String username){}

    public static ArrayList<String> getUserTeams(String username){}

    public static String getLeaderByTeamName(String teamName){}
    public static ArrayList<String> getMembersByTeamName(String teamName){}

    public static boolean doesTaskExist(int id){}
    public static boolean getTaskLeaderByTaskId(int id){}
    public static void changeTaskTitle(int id, String newTitle){}
    public static void changeTaskDescription(int id, String newDescription){}
    public static void changeTaskPriority(int id, String newPriority) {
    }
}
