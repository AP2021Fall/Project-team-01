package view.TeamMenu;

import controller.LoginController;
import controller.TasksPageController;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import models.DatabaseHandler;
import view.MenusFxml;
import view.SceneController;

import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class RoadMapMenuGraphic implements Initializable {
    public AnchorPane anchorPane;
    public String teamName = TeamMenuController.getTeam().getName();
    public ObservableList<String> items = FXCollections.observableArrayList(DatabaseHandler.getTasksTitleByTeamName(teamName));
    public ListView<String> listViewTasks;
    public TextField searchBar;
    public Label TasksInfo;
    public PieChart TasksChart;
    public SceneController sceneController = new SceneController();

    public RoadMapMenuGraphic() throws SQLException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            showTasksInfo();
            ObservableList<String> items = FXCollections.observableArrayList(DatabaseHandler.getTasksTitleByTeamName(teamName));
            listViewTasks.setItems(items);
            listViewTasks.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    String taskName = listViewTasks.getSelectionModel().getSelectedItem();
                    try {
                        TasksPageController.setTaskIdAndTaskTitle(DatabaseHandler.getTaskIdByTaskTitle(taskName, DatabaseHandler.getTeamIdByTeamName(teamName)) + " " + taskName);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (LoginController.getActiveUser().getRole().equals("leader")) {
                        sceneController.switchScene(MenusFxml.TASK_PAGE_LEADER.getLabel());
                        return;
                    }
                    sceneController.switchScene(MenusFxml.TASK_PAGE.getLabel());
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public void showTasksInfo() throws SQLException {
        TasksInfo.setText("Total Tasks: " + DatabaseHandler.getTasksTitleByTeamName(teamName).size() +
                "\nDone Tasks: " + DatabaseHandler.getDoneTasksTitleByTeamName(teamName).size() +
                "\nIn Progress Tasks: " + DatabaseHandler.getInProgressTasksTitleByTeamName(teamName).size() +
                "\nFailed Tasks: " + DatabaseHandler.getFailedTasksTitleByTeamName(teamName).size());
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(new PieChart.Data("Done", DatabaseHandler.getDoneTasksTitleByTeamName(teamName).size()),
                new PieChart.Data("InProgress", DatabaseHandler.getInProgressTasksTitleByTeamName(teamName).size()),
                new PieChart.Data("\nFailed", DatabaseHandler.getFailedTasksTitleByTeamName(teamName).size()));
        TasksChart.setData(pieChartData);
    }
}
