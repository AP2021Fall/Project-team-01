package models;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class User {
    private static ArrayList<User> allUsers = new ArrayList<>();
    private static ArrayList<String> allEmails = new ArrayList<>();
    private static String token;
    private static String activeUser;
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
        allEmails.add(email);
    }

    public static ArrayList<String> getAllEmails() {
        return allEmails;
    }

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public static User getUserByUsername(String username) {
        for (User user : allUsers) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public static boolean isEmailTaken(String email) {
        for (String emailString : allEmails) {
            if (emailString.equals(email))
                return true;
        }
        return false;
    }

    public static boolean isUsernameTaken(String username){
        for (User user : allUsers) {
            if (user.getUsername().equals(username))
                return true;
        }
        return false;
    }

    public void setLoginTimes(ArrayList<LocalDateTime> loginTimes) {
        this.loginTimes = loginTimes;
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

    public void setUsername(String username) {
        this.username = username;
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

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        User.token = token;
    }

    public static String getActiveUser() {
        return activeUser;
    }

    public static void setActiveUser(String activeUser) {
        User.activeUser = activeUser;
    }
}
