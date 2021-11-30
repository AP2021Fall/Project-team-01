package models;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class User {
    private static ArrayList<User> allUsers = new ArrayList<>();
    private ArrayList<Team> teamsOfUser;
    private ArrayList<String> notifications;
    private ArrayList<LocalDateTime> loginTimes;
    private String username;
    private String password;
    private String email;
    private String role;
    private int point;

    public User(String username, String password, String email, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.teamsOfUser = new ArrayList<>();
        this.notifications = new ArrayList<>();
        this.loginTimes = new ArrayList<>();
        allUsers.add(this);
    }

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public ArrayList<Team> getTeamsOfUser() {
        return teamsOfUser;
    }

    public ArrayList<String> getNotifications() {
        return notifications;
    }

    public ArrayList<LocalDateTime> getLoginTimes() {
        return loginTimes;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public int getPoint() {
        return point;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
