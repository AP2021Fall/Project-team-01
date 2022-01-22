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
// insert teamId to
    public static void createTask(String title, String creatingDate, String deadlineDate) throws SQLException {
        String sql = String.format(Queries.CREATE_TASK, title, creatingDate, deadlineDate);
        connectAndExecute(sql);
    }

    //TODO when categories initialize
    //ino eslah kon faghat nameBoardro migire va misaze intory tarif she
    // public static void createBoard(String boardName , int teamId) throws SQLException {
    //  }
    public static void createBoard(String boardName, int teamId) throws SQLException {
        String sql = Queries.CREATE_BOARD;
        connectAndExecute(sql);
    }


    public static boolean doesUsernameExist(String username) throws SQLException {
        String sql = String.format(Queries.DOES_USERNAME_EXIST, username);
        return doesExist(sql);
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

    public static boolean doesTeamExist(String teamName) throws SQLException {
        String sql = String.format(Queries.DOES_TEAM_EXIST, teamName);
        return doesExist(sql);
    }


    public static boolean isTeamInPending(String teamName) throws SQLException {
        String sql = String.format(Queries.IS_TEAM_IN_PENDING, teamName);
        return doesExist(sql);
    }

    public static boolean doesEmailExist(String email) throws SQLException {
        String sql = String.format(Queries.DOES_Email_EXIST, email);
        return doesExist(sql);
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
        return getString(sql);
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

    public static String rejectPendingTeams(String[] teams) throws SQLException {
        for (String i : teams) {
            if (!isTeamInPending(i))
                return "Some teams are not in pending status! Try again";
        }
        for (String i : teams) {
            String sql = String.format(Queries.REJECT_TEAM, i);
            connectAndExecute(sql);
        }
        return "teams Rejected";
    }

    public static ArrayList<String> showScoreboard(String teamName) throws SQLException {
        String sql = String.format(Queries.showScoreBoard, teamName);
        ArrayList<String> answer = new ArrayList<>();
        connect();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        int i = 1;
        while (result.next()) {
            answer.add(i + " " + result.getString(1) + " : " + result.getString(2));
            i++;
        }
        statement.close();
        connection.close();
        return answer;
    }

    public static LocalDateTime getCreationDateByTaskId(int taskId) throws SQLException {
        String sql = String.format(Queries.GET_CREATING_DATE_BY_TASK_ID, taskId);
        connect();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        if (result.next()) {
            String creatingDate = result.getString(1);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            statement.close();
            connection.close();
            return LocalDateTime.parse(creatingDate, formatter);
        } else {
            statement.close();
            connection.close();
            return null;
        }
    }

    public static void setDeadline(int taskId, LocalDateTime newDeadline) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String deadLine = newDeadline.format(formatter);
        String sql = String.format(Queries.SET_DEAD_LINE_BY_TASK_ID, deadLine, taskId);
        connectAndExecute(sql);
    }

    public static void assignUser(int taskId, String username) throws SQLException {
        String sql = String.format(Queries.ASSIGN_USER, username, taskId);
        connectAndExecute(sql);
    }

    public static void removeUserFromTask(int taskId, String username) throws SQLException {
        String sql = String.format(Queries.REMOVE_USER_FROM_TASK, username, taskId);
        connectAndExecute(sql);
    }

    public static boolean isUsernameAssigned(int taskId, String username) throws SQLException {
        String sql = String.format(Queries.IS_USERNAME_ASSIGNED, username, taskId);
        return doesExist(sql);
    }

    public static boolean doesTaskExist(int taskId) throws SQLException {
        String sql = String.format(Queries.DOES_TASK_EXIST, taskId);
        return doesExist(sql);
    }

    public static String getTaskLeaderByTaskId(int taskId) throws SQLException {
        String sql = String.format(Queries.GET_TASK_LEADER_BY_TASK_ID, taskId);
        connect();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        if (result.next())
            return result.getString(1);
        else return null;
    }

    public static void changeTaskPriority(int id, String newPriority) throws SQLException {
        String sql = String.format(Queries.UPDATE_PRIORITY, newPriority, id);
        connectAndExecute(sql);
    }

    public static void changeTaskDescription(int id, String newDescription) throws SQLException {
        String sql = String.format(Queries.UPDATE_DESCRIPTION, newDescription, id);
        connectAndExecute(sql);
    }

    public static void changeTaskTitle(int id, String newTitle) throws SQLException {
        String sql = String.format(Queries.UPDATE_TITLE, newTitle, id);
        connectAndExecute(sql);
    }

    public static void logLogin(String username, LocalDateTime log) throws SQLException {
        String sql = String.format(Queries.GET_LOGS, username);
        connect();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        if (result.next()) {
            String logs = result.getString(1);
            ArrayList<LocalDateTime> localDateTimes = new Gson().fromJson(logs,
                    new TypeToken<List<LocalDateTime>>() {
                    }.getType());
            localDateTimes.add(log);
            logs = new Gson().toJson(localDateTimes);
            sql = String.format(Queries.ADD_LOGS, logs, username);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        statement.close();
        connection.close();
    }

    public static ArrayList<String> showRoadmap(int teamId) throws SQLException {
        String sql = String.format(Queries.ROAD_MAP, teamId);
        ArrayList<String> roadMaps = new ArrayList<>();
        connect();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        int i = 1;
        while (result.next()) {
            roadMaps.add(i + result.getString(1) + " : " + result.getString(2));
        }
        return roadMaps;
    }


    public static void sendMessage(int teamId, String message) throws SQLException {
        String sql = String.format(Queries.GET_CHATROOM, teamId);
        connect();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        if (result.next()) {
            String json = result.getString(1);
            ArrayList<String> chats = new Gson().fromJson(json,
                    new TypeToken<List<String>>() {
                    }.getType());
            chats.add(message);
            json = new Gson().toJson(chats);
            sql = String.format(Queries.ADD_CHAT, chats, teamId);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        statement.close();
        connection.close();
    }

    public static ArrayList<String> showChatroom(int teamId) throws SQLException {
        String sql = String.format(Queries.GET_CHATROOM, teamId);
        return getGsonArraylistStrings(sql);
    }

    public static ArrayList<String> getTeamTasksByTeamId(int teamId) throws SQLException {
        String sql = String.format(Queries.GET_TASKS_BY_TEAM_ID, teamId);
        ArrayList<String> answer = new ArrayList<>();
        connect();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        int i = 1;
        while (result.next()) {
            String taskTitle = result.getString(1);
            int taskId = result.getInt(2);
            String creatingDate = result.getString(3);
            String deadlineDate = result.getString(4);
            StringBuilder users = new StringBuilder();
            String priority = result.getString(5);
            sql = String.format(Queries.GET_USERS_ASSIGNED_TASK, taskId);
            Statement statement2 = connection.createStatement();
            ResultSet result2 = statement2.executeQuery(sql);
            while (result2.next()) {
                users.append(" " + result2.getString(1));
            }
            statement2.close();
            answer.add(i + taskTitle + ": id " + taskId + ",creating date : " + creatingDate + ",deadline : " + deadlineDate + "assign to:" + users.toString() + ",priority: " + priority);
            i++;
        }
        statement.close();
        connection.close();
        return answer;
    }

    public static boolean doesBoardExist(String boardName, int teamId) throws SQLException {
        String sql = String.format(Queries.DOES_BOARD_EXIST, teamId, boardName);
        connect();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        return result.next();
    }

    public static void removeBoard(String boardName, int teamId) throws SQLException {
        String sql = String.format(Queries.REMOVE_BOARD, teamId, boardName);
        connectAndExecute(sql);
    }

    public static void addCategory(String categoryName, String boardName, int teamId) throws SQLException {
        String sql = String.format(Queries.GET_CATEGORIES, teamId, boardName);
        connect();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        if (result.next()) {
            String json = result.getString(1);
            ArrayList<String> categories = new Gson().fromJson(json,
                    new TypeToken<List<String>>() {
                    }.getType());
            categories.add(categoryName);
            json = new Gson().toJson(categories);
            sql = String.format(Queries.ADD_CATEGORIES, json, teamId, boardName);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        statement.close();
        connection.close();
    }

    public static int numOfBoardCategories(String boardName, int teamId) throws SQLException {
        String sql = String.format(Queries.GET_CATEGORIES, teamId, boardName);
        connect();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        if (result.next()) {
            String json = result.getString(1);
            ArrayList<String> categories = new Gson().fromJson(json,
                    new TypeToken<List<String>>() {
                    }.getType());
            return categories.size();
        }
        return 0;
    }

    //     change finish field of a board
    public static void finishBoard(String boardName, int teamId) throws SQLException {
        String sql = String.format(Queries.UPDATE_FINISHED_BOARD, boardName, teamId);
        connectAndExecute(sql);
    }

    public static void addTaskToBoard(int taskId, String boardName) throws SQLException {
        String sql = String.format(Queries.ADD_TASK_TO_BOARD, boardName, taskId);
        connectAndExecute(sql);
    }

    public static boolean doesTaskAddedToBoard(int taskId, String boardName) throws SQLException {
        String sql = String.format(Queries.DOES_TASK_ADDED_TO_BOARD, taskId, boardName);
        return doesExist(sql);
    }

    public static boolean doesDeadlinePassed(int taskId) throws SQLException {
        String sql = String.format(Queries.DOES_DEADLINE_PASSED, taskId);
        return doesExist(sql);
    }

    public static boolean isTaskAssigned(int taskId) throws SQLException {
        String sql = String.format(Queries.IS_TASK_ASSIGNED, taskId);
        return doesExist(sql);

    }

    public static boolean doesExist(String sql) throws SQLException {
        connect();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        boolean bool = result.next();
        statement.close();
        connection.close();
        return bool;
    }

    // title task va id team ro migire va id task mored nazar ro bar migardune
      public static int getTaskIdByTaskTitle( String taskTitle , int teamId) throws SQLException {
        String sql = String.format(Queries.GET_TASK_ID_BY_TASK_TITLE, teamId, taskTitle);
        int a = 0;
        connect();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        if (result.next()) {
            a = result.getInt(1);
        }
        statement.close();
        connection.close();
        return a;
      }


    //can be member or leader
    public static int getNumberOfTeamsByUsername(String username) throws SQLException {
        String sql = String.format(Queries.USER_TEAMS_NUMBER, username);
        connect();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        int number = result.getRow();
        statement.close();
        connection.close();
        return number;
    }

    //change role from member to leader and leader to member
    public static void changeRole(String username) throws SQLException {
        String sql = new String();
        if (getRoleByUsername(username).equals("leader"))
            sql = String.format(Queries.CHANGE_ROLE, "leader", username);
        else
            sql = String.format(Queries.CHANGE_ROLE, "member", username);
        connectAndExecute(sql);
    }

    public static ArrayList<Integer> getDoneTasks(String boardName, int teamId) throws SQLException {
        String sql = String.format(Queries.GET_DONE_TASKS_OF_BOARD, boardName, teamId);
        return getIntArraylist(sql);
    }

    public static ArrayList<Integer> getFailedTasks(String boardName, int teamId) throws SQLException {
        String sql = String.format(Queries.GET_FAILED_TASKS_OF_BOARD, boardName, teamId);
        return getIntArraylist(sql);
    }

    public static ArrayList<String> getTasksOfBoardToShow(String boardName, int teamId, int priority) throws SQLException {
        String sql = String.format(Queries.GET_TASKS_BY_BOARD_NAME_TEAM_ID, boardName, teamId);
        ArrayList<String> answer = new ArrayList<>();
        connect();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        while (result.next()) {
            String taskTitle = result.getString(1);
            String category = result.getString(2);
            String description = result.getString(3);
            String creatingDate = result.getString(4);
            String deadlineDate = result.getString(5);
            int state = result.getInt(6);
            int taskId = result.getInt(7);
            StringBuilder users = new StringBuilder();
            sql = String.format(Queries.GET_USERS_ASSIGNED_TASK, taskId);
            Statement statement2 = connection.createStatement();
            ResultSet result2 = statement2.executeQuery(sql);
            while (result2.next()) {
                users.append(" " + result2.getString(1));
            }
            statement2.close();
            String row = "Title: " + taskTitle + "\nCategory: " + category + "\nDescription: " + description + "\nCreation date: " + creatingDate + "\nDeadline: " + deadlineDate + "\nAssigned to: " + users.toString() + "\nStatus: ";
            switch (state){
                case 0:
                    row = row + "Failed\n";
                    break;
                case 1:
                    row = row + "Done\n";
                    break;
                case 3:
                    row = row + "In progress\n";
                    break;
            }
            answer.add(row);
        }
        statement.close();
        connection.close();
        return answer;

    }

    public static String showBoard(String boardName, int teamId) throws SQLException {
        ArrayList<Integer> doneTasks = getDoneTasks(boardName, teamId);
        ArrayList<Integer> failedTasks = getFailedTasks(boardName, teamId);
        ArrayList<String> tasksHighest = getTasksOfBoardToShow(boardName, teamId, 4);
        ArrayList<String> tasksHigh = getTasksOfBoardToShow(boardName, teamId, 3);
        ArrayList<String> tasksLow = getTasksOfBoardToShow(boardName, teamId, 2);
        ArrayList<String> tasksLowest = getTasksOfBoardToShow(boardName, teamId, 1);
        int completion = 100*doneTasks.size()/(tasksHighest.size()+tasksHigh.size()+tasksLow.size()+tasksLowest.size());
        int failed = 100*failedTasks.size()/(tasksHighest.size()+tasksHigh.size()+tasksLow.size()+tasksLowest.size());
        StringBuilder stringBuilder = new StringBuilder();
        String sql = String.format(Queries.SHOW_BOARD, boardName, teamId);
        connect();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        if (result.next()) {
            String leader = result.getString(1);
            stringBuilder.append("Boardname: " + boardName + "\n");
            stringBuilder.append("Board completion" + completion + "\n");
            stringBuilder.append("Board failed" + failed + "\n");
            stringBuilder.append("Board leader" + leader + "\n");
            stringBuilder.append("Highest Priority :\n");
            for(String i : tasksHighest) {
                stringBuilder.append(i + "\n");
            }
            stringBuilder.append("Highest Priority :\n");
            for(String i : tasksHighest) {
                stringBuilder.append(i + "\n");
            }
            stringBuilder.append("High Priority :\n");
            for(String i : tasksHigh) {
                stringBuilder.append(i + "\n");
            }
            stringBuilder.append("Low Priority :\n");
            for(String i : tasksLow) {
                stringBuilder.append(i + "\n");
            }
            stringBuilder.append("Lowest Priority :\n");
            for(String i : tasksLowest) {
                stringBuilder.append(i + "\n");
            }
            return stringBuilder.toString();
        }
        statement.close();
        connection.close();
        return "";
    }

    public static void addMemberToTeam(String username, int teamId) throws SQLException {
        String sql = String.format(Queries.ADD_MEMBER_TO_TEAM, username, teamId);
        connectAndExecute(sql);
    }
    public static void removeMemberFromTeam(String username, int teamId) throws SQLException {
        String sql = String.format(Queries.REMOVE_USER_FROM_TEAM, username, teamId);
        connectAndExecute(sql);
        ArrayList<Integer> tasks = getTasksIdByTeamId(teamId);
        for (Integer i : tasks) {
            sql = String.format(Queries.REMOVE_USER_FROM_TASK_OF_TEAM_BY_TEAM_ID, username, i);
            connectAndExecute(sql);
        }
    }

    public static String getUserRole(String username) throws SQLException {
        String sql = String.format(Queries.GET_USER_ROLE, username);
        return getString(sql);
    }

    private static String getString(String sql) throws SQLException {
        String role = null;
        connect();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        if (result.next()) {
            role = result.getString(1);
        }
        statement.close();
        connection.close();
        return role;
    }

    //first get task id
     public static boolean doesTaskExist(String taskTitle, int teamId) throws SQLException {
        return getTaskIdByTaskTitle(taskTitle, teamId) != 0;
     }

    //get members of team instead
    public static boolean doesTeamHaveMember(String teamName) throws SQLException {
        return !getMembersByTeamName(teamName).isEmpty();
     }


    //get role
     public static boolean isUserMember ( String username) throws SQLException {
        return getUserRole(username).equals("member");
     }

     public static boolean isUserInTeam (String username , String teamName) throws SQLException {
        ArrayList<String> arrayList = getMembersByTeamName(teamName);
        for (String i : arrayList) {
            if (i.equals(username))
                return true;
        }
        return false;
    }

     public static void addToSuspendedList (String username , int teamId) throws SQLException {
        String sql = String.format(Queries.ADD_SUSPENDS, username, teamId);
        connectAndExecute(sql);
     }

     public static ArrayList<String> getCategories(String boardName, int teamId) throws SQLException {
         String sql = String.format(Queries.GET_CATEGORIES, teamId, boardName);
         return getGsonArraylistStrings(sql);
     }

    private static ArrayList<String> getGsonArraylistStrings(String sql) throws SQLException {
        ArrayList<String> categories = new ArrayList<>();
        connect();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        if (result.next()) {
            String json = result.getString(1);
            categories = new Gson().fromJson(json,
                    new TypeToken<List<String>>() {
                    }.getType());
        }
        statement.close();
        connection.close();
        return categories;
    }

     public static String getCategory(int taskId) throws SQLException {
        String sql = String.format(Queries.GET_CATEGORY_BY_TASK_ID, taskId);
        return getString(sql);
     }

     public static void addCategoryToColumn(String categoryName , int columnNum , String boardName , int teamId) throws SQLException {
         String sql = String.format(Queries.GET_CATEGORIES, teamId, boardName);
         connect();
         Statement statement = connection.createStatement();
         ResultSet result = statement.executeQuery(sql);
         if (result.next()) {
             String json = result.getString(1);
             ArrayList<String> categories = new Gson().fromJson(json,
                     new TypeToken<List<String>>() {
                     }.getType());
             categories.add(columnNum, categoryName);
             json = new Gson().toJson(categories);
             sql = String.format(Queries.ADD_CATEGORIES, json, teamId, boardName);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             preparedStatement.executeUpdate();
             preparedStatement.close();
         }
         statement.close();
         connection.close();
     }

     public static boolean isTaskInDoneCategory ( int taskId ) throws SQLException {
        int state = getStateOfTask(taskId);
        return state == 1;
     }

     public static void addToCategory ( String category , String taskTitle , String boardName , int teamId) throws SQLException {
        int taskId = getTaskIdByTaskTitle(taskTitle, teamId);
        String sql = String.format(Queries.SET_CATEGORY,category ,taskId);
        connectAndExecute(sql);
     }

     public static int getStateOfTask(int taskId) throws SQLException {
         String sql = String.format(Queries.GET_TASK_STATE, taskId);
         connect();
         Statement statement = connection.createStatement();
         ResultSet result = statement.executeQuery(sql);
         int state = result.getInt(1);
         statement.close();
         connection.close();
         return state;
     }
    //public static Arraylist<String> getMembersOfTask(int taskId){
    //}
    // public static int getPointsOfUser(String username){
    // }
    // public static void setPointOfUser(String username , int points){
    // }
    // public static Arraylist<String> getAllTasks(){
    // }
}
