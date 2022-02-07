import com.google.gson.Gson;
import controller.LoginController;
import controller.MainMenuController;
import controller.ProfileMenuController.ChangePasswordMenuController;
import controller.ProfileMenuController.ProfileMenuController;
import controller.TasksPageController;
import controller.TeamMenuController.*;
import models.DatabaseHandler;
import models.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;


public class Server {
    public static void execute() {
        try {
            ServerSocket serverSocket = new ServerSocket(7777);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    try {
                        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                        while (true) {
                            String input = dataInputStream.readUTF();
                            String result = process(input);
                            if (input.isEmpty()) break;
                            dataOutputStream.writeUTF(result);
                            dataOutputStream.flush();
                        }
                        dataInputStream.close();
                        socket.close();
                    } catch (IOException | SQLException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String process(String input) throws SQLException {
        String[] command = input.split("\\s");
        if (command[0].equals("login")) {
            String username = command[1];
            String password = command[2];
            return LoginController.loginUser(username, password);
        }if (command[0].equals("role")) {
            return DatabaseHandler.getRoleByUsername(command[1]);
        }if (command[0].equals("register")) {
            return LoginController.createUser(command[1], command[2],
                    command[3], command[4], command[5]);
        }if (command[0].equals("ShowMyProfile")){
            return ProfileMenuController.showMyProfile(command[1]);
        }if (command[0].equals("ShowLogs")){
            return ProfileMenuController.showLogs(command[1]);
        }if (command[0].equals("ChangePassword")){
            return ChangePasswordMenuController.changePassword(command[1], command[2], command[3]);
        }if (command[0].equals("ChangeUsername")){
            return ProfileMenuController.changeUsername(command[1], command[2]);
        }if (command[0].equals("ShowNotifications")){
            return ProfileMenuController.showNotifications(command[1]).toString();
        }if (command[0].equals("ShowMyTeams")){
            return new Gson().toJson(ProfileMenuController.showTeams(command[1]));
        }if (command[0].equals("ShowTeam")){
            return ProfileMenuController.showTeam(command[1]);
        }if (command[0].equals("getTasksByUsername")) {
            return  new Gson().toJson(DatabaseHandler.getTasksByUsername(command[1]));
        }if (command[0].equals("createTask")) {
            return TeamMenuController.createTask(command[1], command[2] + " " + command[3], command[4] + " " + command[5], command[6]);
        }if (command[0].equals("createTeam")) {
            return MainMenuController.createTeam(command[1], command[2]);
        }if (command[0].equals("changeRole")) {
            return MainMenuController.changeRoleToMember(command[1], command[2]);
        }if (command[0].equals("SelectTeam")) {
            TeamSelectionController.enterTeam(command[1], command[2]);
            return " ";
        }if (command[0].equals("ShowMembersAndLeader")) {
            return new Gson().toJson(TeamMenuController.showMembersLeader(command[1]));
        }if (command[0].equals("GetTasksTitleByTeamName")) {
            return new Gson().toJson(DatabaseHandler.getTasksTitleByTeamName(TeamMenuController.getCurrentTeam().get(command[1]).getName()));
        }if (command[0].equals("SetTaskIdAndTaskTitle")) {
            String taskName = command[1];
            String token = command [2];
            int currentTeamId = DatabaseHandler.getTeamIdByTeamName(TeamMenuController.getCurrentTeam().get(token).getName());
            TasksPageController.getTaskIdAndTaskTitle().put(token, DatabaseHandler.getTaskIdByTaskTitle(taskName, currentTeamId)  + " " + taskName);
            return "";
        }
        if (command[0].equals("changeRole1")) {
            MainMenuController.changeRole(ScoreBoardController.getUsernameToRemove().get(command[2]), command[1], command[2]);
            return "done";
        }
        if (command[0].equals("acceptTeams")) {
            MainMenuController.acceptTeams(MainMenuController.pendingTeam.split("      "));
            return "done";
        }
        if (command[0].equals("rejectTeams")) {
            MainMenuController.rejectTeams(MainMenuController.pendingTeam.split("      "));
            return "done";
        }
        if (command[0].equals("pendingTeam")) {
            MainMenuController.pendingTeam = command[1];
            return "done";
        }
        if (command[0].equals("choice")) {
            return MainMenuController.choice.get(command[1]);
        }
        if (command[0].equals("sendToUser")) {
            int i = input.lastIndexOf(' ');
            MainMenuController.sendNotificationToUser(input.substring(11, i),MainMenuController.username.get(input.substring(i+1)),input.substring(i+1));
            return "done";
        }
        if (command[0].equals("sendToTeam")) {
            int i = input.lastIndexOf(' ');
            MainMenuController.sendNotificationToTeam(input.substring(11, i), MainMenuController.team.get(input.substring(i+1)), input.substring(i+1));
            return "done";
        }
        if (command[0].equals("sendToAll")) {
            int i = input.lastIndexOf(' ');
            MainMenuController.sendNotificationToAll(input.substring(10));
            return "done";
        }
        if (command[0].equals("setTeamChoice")) {
            String teamName = command[1];
            String token = command[2];
            String choice = "2";
            MainMenuController.team.put(token, teamName);
            MainMenuController.choice.put(token, choice);
            return "done";
        }
        if (command[0].equals("setUserChoice")) {
            MainMenuController.username.put(command[2], command[1]);
            MainMenuController.choice.put(command[2], "1");
            return "done";
        }
        if (command[0].equals("setChoice")) {
            MainMenuController.choice.put(command[1], "3");
            return "done";
        }
        if (command[0].equals("taskIdTaskPage")) {
            return TasksPageController.getTaskId(command[1]) + "";
        }
        if (command[0].equals("editTitle")) {
            return TasksPageController.editTitle(TasksPageController.getTaskId(command[2]), command[1], command[2]);
        }
        if (command[0].equals("editDescription")) {
            return TasksPageController.editDescription(TasksPageController.getTaskId(command[2]), command[1], command[2]);
        }
        if (command[0].equals("editPriority")) {
            return TasksPageController.editPriority(TasksPageController.getTaskId(command[2]), Integer.parseInt(command[1]), command[2]);
        }
        if (command[0].equals("editDeadLine")) {
            return TasksPageController.editDeadline(TasksPageController.getTaskId(command[2]), command[1], command[2]);
        }
        if (command[0].equals("editAssigned")) {
            return TasksPageController.addAssignedUser(TasksPageController.getTaskId(command[2]), command[1], command[2]);
        }
        if (command[0].equals("editRemoveAssigned")) {
            return TasksPageController.removeAssignedUser(TasksPageController.getTaskId(command[2]), command[1], command[2]);
        }
        if (command[0].equals("setTaskIdAndTaskTitle2")) {
            TasksPageController.getTaskIdAndTaskTitle().put(command[2], command[1]);
        }
        if (command[0].equals("CurrentTeamName")){
            return TeamMenuController.getCurrentTeam().get(command[1]).getName();
        }
        if (command[0].equals("CurrentTeamId")){
            Integer teamId = TeamMenuController.getCurrentTeam().get(command[1]).getId();
            return teamId.toString();
        }
        if (command[0].equals("SelectUsernameToRemove")){
            return ScoreBoardController.getUsernameToRemove().put(command[2], command[1]);
        }
        if (command[0].equals("banUser")) {
            MainMenuController.banUser(ScoreBoardController.getUsernameToRemove().get(command[1]));
        }
        if (command[0].equals("SendMessage")){
            ChatroomController.sendMessage(command[1], command[2]);
            return "";
        }
        if (command[0].equals("ShowChatroom")){
            return new Gson().toJson(DatabaseHandler.showChatroom(TeamMenuController.getCurrentTeam().get(command[1]).getId()));
        }
        if (command[0].equals("ShowProfile")){
            return ProfileMenuController.showMyProfile();
        }
        if (command[0].equals("UsernameToRemove")){
            return ScoreBoardController.getUsernameToRemove().get(command[1]);
        }
        if (command[0].equals("SetActiveBoard")){
            BoardMenuController.getActiveBoard().put(command[2], command[1]);
            return "";
        }
        if (command[0].equals("GetActiveBoard")){
            return BoardMenuController.getActiveBoard().get( command[1]);
        }
        if (command[0].equals("CreateBoard")){
            return BoardMenuController.createBoard(command[1], command[2]);
        }
        if (command[0].equals("SelectBoard")){
            BoardMenuController.selectBoard(command[1], command[2]);
            return "";
        }
        if (command[0].equals("AddCategorySelect")){
            return BoardMenuController.addCategorySelect(command[1], command[2]);
        }
        if (command[0].equals("AddCategoryToColumnSelect")){
            return BoardMenuController.addCategoryToColumnSelect(command[1], command[2], command[3]);
        }


        //DataBase commands :(
        if (command[0].equals("DgetTasksByUsername")) {
            return getJson(DatabaseHandler.getTasksByUsername(command[1]));
        }
        if (command[0].equals("DgetNumDeadline")) {
            return DatabaseHandler.getNumDeadline(Integer.parseInt(command[1])) + "";
        }
        if (command[0].equals("DsortTaskTitlesByDeadline")) {
            return getJson(DatabaseHandler.sortTaskTitlesByDeadline(command[1]));
        }



        return " ";
    }

    private static String getJson(ArrayList<String> a) {
        return new Gson().toJson(a);
    }
}
