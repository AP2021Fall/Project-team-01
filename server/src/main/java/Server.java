import com.google.gson.Gson;
import controller.LoginController;
import controller.MainMenuController;
import controller.ProfileMenuController.ChangePasswordMenuController;
import controller.ProfileMenuController.ProfileMenuController;
import controller.TasksPageController;
import controller.TeamMenuController.TeamMenuController;
import controller.TeamMenuController.TeamSelectionController;
import models.DatabaseHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;


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

        }if (command[0].equals("GetTasksTitleByTeamName")) {
            return new Gson().toJson(DatabaseHandler.getTasksTitleByTeamName(TeamMenuController.getCurrentTeam().get(command[1]).getName()));
        }if (command[0].equals("SetTaskIdAndTaskTitle")) {
            String taskName = command[1];
            String token = command [2];
            int currentTeamId = DatabaseHandler.getTeamIdByTeamName(TeamMenuController.getCurrentTeam().get(token).getName());
            TasksPageController.getTaskIdAndTaskTitle().put(token, DatabaseHandler.getTaskIdByTaskTitle(taskName, currentTeamId)  + " " + taskName);
            return " ";
        }
        return " ";
    }
}
