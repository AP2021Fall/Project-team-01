import com.google.gson.Gson;
import controller.LoginController;
import controller.ProfileMenuController.ChangePasswordMenuController;
import controller.ProfileMenuController.ProfileMenuController;
import models.DatabaseHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
        }
        if (command[0].equals("role")) {
            return DatabaseHandler.getRoleByUsername(command[1]);
        }
        if (command[0].equals("register")) {
            return LoginController.createUser(command[1], command[2],
                    command[3], command[4], command[5]);
        }
        if (command[0].equals("ChangePassword")){
            return ChangePasswordMenuController.changePassword(command[1], command[2], command[3]);
        }if (command[0].equals("ChangeUsername")){
            return ProfileMenuController.changeUsername(command[1], command[2]);
        }if (command[0].equals("ShowNotifications")){
            return ProfileMenuController.showNotifications(command[1]).toString();
        }if (command[0].equals("ShowMyTeams")){
            return new Gson().toJson(ProfileMenuController.showTeams(command[1]));
        }if (command[0].equals("ShowTeam")){
            return ProfileMenuController.showTeam(command[1]);
        }
        return " ";
    }
}
