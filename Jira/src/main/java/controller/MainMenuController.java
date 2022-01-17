package controller;

import models.DatabaseHandler;
import models.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class MainMenuController {

    public static void showTeams() throws SQLException {
        User user = LoginController.getActiveUser();
        if (user.getRole().equalsIgnoreCase("leader")) {
            ArrayList<String> arrayList = DatabaseHandler.getUserTeams(user.getUsername());
            if (arrayList.isEmpty())
                System.out.println("There is no team for you!");
            else {
                int counter = 0;
                while (counter < arrayList.size()) {
                    System.out.println((counter + 1) + "- " + arrayList.get(counter));
                    counter++;
                }
            }
        } else {
            System.out.println("you are not leader");
        }
    }

    public static void showTeam(String teamName) {
        if (LoginController.getActiveUser().getRole().equals("leader")) {

        } else {
            System.out.println("you are not leader");
        }
    }
}
