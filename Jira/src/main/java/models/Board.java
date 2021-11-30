package models;

import java.util.ArrayList;

public class Board {
    private static ArrayList<Board> allBoards = new ArrayList<>();
    private static boolean isFirstStepOfCreationCompleted = false;
    private String name;
    private ArrayList<Category> categories;
    private ArrayList<Task> doneTasks;
    private ArrayList<Task> failedTasks;

    public Board(String name, ArrayList<Category> categories) {
        this.name = name;
        this.categories = categories;
        doneTasks = new ArrayList<>();
        failedTasks = new ArrayList<>();
    }

    public static ArrayList<Board> getAllBoards() {
        return allBoards;
    }

    public static boolean isIsFirstStepOfCreationCompleted() {
        return isFirstStepOfCreationCompleted;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public ArrayList<Task> getDoneTasks() {
        return doneTasks;
    }

    public ArrayList<Task> getFailedTasks() {
        return failedTasks;
    }

    public void addToDone(Task task) {
        doneTasks.add(task);
    }

    public void addToFailed(Task task) {
        failedTasks.add(task);
    }
}
