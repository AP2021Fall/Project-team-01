package controller.ProfileMenuController;

import controller.LoginController;
import models.DatabaseHandler;
import view.LoginMenu;
import view.MenuController;
import view.Menus;
import view.ProfileMenu.ProfileMenu;
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

    public static String changePassword(String oldPassword, String newPassword) throws SQLException {
        if (!LoginController.getActiveUser().getPassword().equals(oldPassword)) {
            counter++;
            if (counter==2){
                counter = 0;
                return "login";
            } else
                return "wrong old password";
        } else if (oldPassword.equals(newPassword)) {
            return "please enter a new password";
        } else if (!Regex.isPasswordStrong(newPassword)) {
            return "Please Choose A strong Password (Containing at least 8 characters including 1 digit and 1 Capital Letter)";
        } else {
            DatabaseHandler.changePassword(LoginController.getActiveUser().getUsername(), newPassword);
            LoginController.getActiveUser().setPassword(newPassword);
            MenuController.currentMenu = Menus.PROFILE_MENU;
            ProfileMenu.showProfileMenu();
            return "password changed successfully";
        }
    }


    public static void showChangePasswordMenu(){
        System.out.println("please type Profile --change --oldpassword  --newpassword");
        System.out.println("type back to go into MainMenu");
    }


}
