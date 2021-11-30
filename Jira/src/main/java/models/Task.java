package models;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Task {
    private static ArrayList<Task> allTasks = new ArrayList<>();
    private int id;
    private String title;
    private String description;
    private String priority;
    private LocalDateTime creationDate;
    private LocalDateTime deadLineDate;
    private ArrayList<User> assignedUsers;

    public Task(String title, String description, String priority,
                LocalDateTime creationDate, LocalDateTime deadLineDate,
                ArrayList<User> assignedUsers) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.creationDate = creationDate;
        this.deadLineDate = deadLineDate;
        this.assignedUsers = assignedUsers;
    }

    public static ArrayList<Task> getAllTasks() {
        return allTasks;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPriority() {
        return priority;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getDeadLineDate() {
        return deadLineDate;
    }

    public ArrayList<User> getAssignedUsers() {
        return assignedUsers;
    }
}
