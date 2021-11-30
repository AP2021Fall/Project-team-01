package models;

import java.util.ArrayList;

public class Category {
    private String name;
    private ArrayList<Task> inputs;

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Task> getInputs() {
        return inputs;
    }

    private void addToInputs(Task task) {
        inputs.add(task);
    }
}
