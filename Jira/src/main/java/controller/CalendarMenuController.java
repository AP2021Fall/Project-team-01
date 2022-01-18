package controller;

import models.DatabaseHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class CalendarMenuController {
    public static void showDeadlines(){
        DatabaseHandler.updateDeadlines();
        System.out.println(DatabaseHandler.getDeadlinesByUsername(LoginController.getActiveUser().getUsername()));
    }

}
