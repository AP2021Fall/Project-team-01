package controller.TeamMenuController;

import models.DatabaseHandler;

import java.util.ArrayList;

public class TasksController {
    public static void showTasks(){
        ArrayList<String>show = DatabaseHandler.getTeamTasksByTeamName(TeamMenuController.getTeam().getName());
        for (int i = 0 ; i < show.size(); i++ )
            System.out.println(i + " " + show.get(i));
        if (show.size()==0)
            System.out.println("no task yet");

    }
    public static void showTaskById( String taskId){
        String show = DatabaseHandler.getTeamTasksByTaskId(Integer.parseInt(taskId) , TeamMenuController.getTeam().getName());
        if (show != null)
            System.out.println(show);
        else
            System.out.println("there is no task with this Id for this team");
    }
    public static void showTaskMenu(){
        System.out.println("type show tasks to see all tasks of user");
        System.out.println("type show task --id to see a task by id");
        System.out.println("type back to go back into TeamMenu");
    }
}
