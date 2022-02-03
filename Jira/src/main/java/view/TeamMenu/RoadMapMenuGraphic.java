package view.TeamMenu;

import controller.TeamMenuController.TeamMenuController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import models.DatabaseHandler;
import view.MenusFxml;
import view.SceneController;

import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class RoadMapMenuGraphic implements Initializable {
    String teamName = TeamMenuController.getTeam().getName();
    ObservableList<String> items = FXCollections.observableArrayList(DatabaseHandler.getTasksTitleByTeamName(teamName));
    public ListView<String> listViewTasks;
    public TextField searchBar;
    public Label TasksInfo;
    public PieChart TasksChart;
    SceneController sceneController = new SceneController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showTasksInfo();
        ObservableList<String> items = FXCollections.observableArrayList(DatabaseHandler.getTasksTitleByTeamName(teamName));
        listViewTasks.setItems(items);
        listViewTasks.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String taskName = listViewTasks.getSelectionModel().getSelectedItem();
                //                        sceneController.switchScene(MenusFxml.SELECTED_TASK_MENU.getLabel());
            }
        });
    }

    //member or leader or admin
    public void goToMainMenu(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.MEMBER_MAIN_MENU.getLabel());
    }

    public void search(ActionEvent actionEvent) {
        listViewTasks.getItems().clear();
        listViewTasks.getItems().addAll(searchList(searchBar.getText(), items));
    }

    private List<String> searchList(String searchWords, List<String> listOfStrings) {
        List<String> searchWordsArray = Arrays.asList(searchWords.trim());
        return listOfStrings.stream().filter(input -> {
            return searchWordsArray.stream().allMatch(word ->
                    input.contains(word));
        }).collect(Collectors.toList());
    }

    public void backToSelectedTeamMenu(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SELECTED_TEAM_MENU.getLabel());
    }

    public void showTasksInfo (){
        TasksInfo.setText("Total Tasks: " + DatabaseHandler.getTasksTitleByTeamName(teamName).size() +
                "\nDone Tasks: " + DatabaseHandler.getDoneTasksTitleByTeamName(teamName).size() +
                "\nIn Progress Tasks: "+ DatabaseHandler.getInProgressTasksTitleByTeamName(teamName) +
                "\nFailed Tasks: " + DatabaseHandler.getFailedTasksTitleByTeamName(teamName));
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList( new PieChart.Data("Done", DatabaseHandler.getDoneTasksTitleByTeamName(teamName).size()),
                new PieChart.Data("InProgress", DatabaseHandler.getDoneTasksTitleByTeamName(teamName).size()),
                new PieChart.Data("Failed", DatabaseHandler.getFailedTasksTitleByTeamName(teamName).size()));

    }
}
