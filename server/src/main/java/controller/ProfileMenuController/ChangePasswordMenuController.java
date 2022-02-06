package controller.ProfileMenuController;

import models.DatabaseHandler;
import models.User;
import view.Regex;

import java.sql.SQLException;

public class ChangePasswordMenuController {
    private static int counter = 0;

    public static void setCounter(int counter) {
        ChangePasswordMenuController.counter = counter;
    }

    public static int getCounter() {
        return counter;
    }

    public static String changePassword(String oldPassword, String newPassword, String token) throws SQLException {
        User activeUser = User.getLoginUsers().get(token);
        if (!activeUser.getPassword().equals(oldPassword)) {
            counter++;
            if (counter == 2) {
                counter = 0;
                return "login";
            } else
                return "wrong old password";
        } else if (oldPassword.equals(newPassword)) {
            return "please enter a new password";
        } else if (!Regex.isPasswordStrong(newPassword)) {
            return "Please Choose A strong Password (Containing at least 8 characters including 1 digit and 1 Capital Letter)";
        } else {
            DatabaseHandler.changePassword(activeUser.getUsername(), newPassword);
            activeUser.setPassword(newPassword);
            return "password changed successfully";
        }
    }

}
