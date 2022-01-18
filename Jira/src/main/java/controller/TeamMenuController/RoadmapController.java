package controller.TeamMenuController;

import models.DatabaseHandler;

import java.util.ArrayList;

public class RoadmapController {
    public static void showRoadmap(){
        ArrayList<String> show = DatabaseHandler.showRoadmap(TeamMenuController.getTeam().getName());
        for (int i = 0; i < show.size();i++)
            System.out.println(i + " " + show.get(i));
        if (show.size()==0)
            System.out.println("no task yet");
    }

    public static void showRoadmapMenu(){
        System.out.println("type Roadmap --show to see tasks");
        System.out.println("type back to get back into TeamMenu");
    }
}

