package models;

import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class DatabaseHandler {
    private static final String JDB_URL = "jdbc:mysql://localhost:3306/apjira";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection connection;

    public static void connect() throws SQLException {
        connection = DriverManager.getConnection(JDB_URL, USER, PASSWORD);
    }


    public static void createUser(String username, String password, String email, String role) throws SQLException {
        connect();
        String sql = String.format(Queries.CREATE_USER, username,password,email,role);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }

    public static void createTeam(String name, LocalDateTime creatingDate) throws SQLException {
        connect();
        String json = new Gson().toJson(creatingDate);
        String sql = String.format(Queries.CREATE_TEAM, name, json);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }






}
