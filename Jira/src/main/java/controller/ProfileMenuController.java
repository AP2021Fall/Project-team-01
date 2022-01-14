package controller;

import models.DatabaseHandler;
import models.User;
import view.Regex;

public class ProfileMenuController {

    public static String changePassword(String oldPassword, String newPassword){
        if(!LoginController.getActiveUser().getPassword().equals(oldPassword)){
            return "wrong old password";
        }else if(oldPassword.equals(newPassword)){
            return "please enter a new password";
        }else if(!Regex.isPasswordStrong(newPassword)){
            return "Please Choose A strong Password (Containing at least 8 characters including 1 digit and 1 Capital Letter)";
        }else{
            DatabaseHandler.changePassword(LoginController.getActiveUser().getUsername(), newPassword);
            LoginController.getActiveUser().setPassword(newPassword);
            return "password changed successfully";
        }
    }

    public static String changeUsername(String newUsername){
        if(newUsername.length()<4)
            return "Your new username must include at least 4 characters!";
        else if(DatabaseHandler.doesUsernameExist(newUsername))
            return "username already taken!";
        else if(!Regex.getCommandMatcher(newUsername, "([\\w\\d_]{4,})").matches())
            return "New username contains Special Characters! Please remove them and try again";
        else if(LoginController.getActiveUser().getUsername().equals(newUsername))
            return "you already have this username !";
        else{
            DatabaseHandler.changeUsername(LoginController.getActiveUser().getUsername(), newUsername);
            LoginController.getActiveUser().setUsername(newUsername);
            return "username successfully changed";
        }
    }

    //be tartib alephba va esm user avval biyad
    public static void showTeams(){
        System.out.println( DatabaseHandler.getUserTeams(LoginController.getActiveUser().getUsername())) ;
    }

    public void showTeam(String teamName){
        System.out.println(teamName + ":");
        System.out.println("leader: "+ DatabaseHandler.getLeaderByTeamName(teamName));
        System.out.println("members:" + DatabaseHandler.getMembersByTeamName(teamName));
    }


    public void showMyProfile(){
        User user = LoginController.getActiveUser();
        System.out.println("username: " + user.getUsername() + "email address: " + user.getEmail() +"role: " + user.getRole()) ;
        //birthdate and name?
    }

    public void showLogs(){
        System.out.println(DatabaseHandler.getLogsByUsername(LoginController.getActiveUser().getUsername()));
    }

    public void shoeNotifications(){
        System.out.println(DatabaseHandler.getNotifications(LoginController.getActiveUser().getUsername()));
    }
}
