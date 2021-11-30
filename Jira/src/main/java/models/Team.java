package models;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Team {
    private static ArrayList<Team> allTeams = new ArrayList<>();
    private int id;
    private String name;
    private ArrayList<User> users;
    private ArrayList<String> chatroom;
    private ArrayList<Task> tasks;
    private ArrayList<Board> boards;
    private LocalDateTime creatingDate;

    public Team(String name, ArrayList<User> users) {
        this.name = name;
        this.users = users;
        tasks = new ArrayList<>();
        boards = new ArrayList<>();
        creatingDate = LocalDateTime.now();
        id = idGenerator();
    }

    public static ArrayList<Team> getAllTeams() {
        return allTeams;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<String> getChatroom() {
        return chatroom;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public ArrayList<Board> getBoards() {
        return boards;
    }

    public LocalDateTime getCreatingDate() {
        return creatingDate;
    }


    private static int idGenerator() {
        if (allTeams.isEmpty())
            return 1;
        return allTeams.get(allTeams.size() - 1).id + 1;
    }
}