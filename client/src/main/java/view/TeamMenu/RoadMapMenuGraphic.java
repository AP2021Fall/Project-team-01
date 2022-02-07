package view.TeamMenu;

import appController.AppController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import models.User;
import view.LoginMenuGraphic;
import view.MenusFxml;
import view.SceneController;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class RoadMapMenuGraphic implements Initializable {
    public AnchorPane anchorPane;
    public ListView<String> listViewTasks;
    public TextField searchBar;
    public Label TasksInfo;
    public PieChart TasksChart;
    public SceneController sceneController = new SceneController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            showTasksInfo();
            ArrayList<String> taskTitles = AppController.getArraylistResult("GetTasksTitleByTeamName " + User.getToken());
            ObservableList<String> items = FXCollections.observableArrayList (taskTitles);
            listViewTasks.setItems(items);
            listViewTasks.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    String taskName = listViewTasks.getSelectionModel().getSelectedItem();
                    try {
                        AppController.getResult("SetTaskIdAndTaskTitle " + taskName + " " + User.getToken());
                        if (LoginMenuGraphic.getRole(User.getActiveUsername()).equals("leader")) {
                            sceneController.switchScene(MenusFxml.TASK_PAGE_LEADER.getLabel());
                            return;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    sceneController.switchScene(MenusFxml.TASK_PAGE.getLabel());
                }
            });
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    //member or leader or admin
    public void goToMainMenu(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.MEMBER_MAIN_MENU.getLabel());
    }

    public void search(ActionEvent actionEvent) throws IOException {
        ArrayList<String> taskTitles = AppController.getArraylistResult("GetTasksTitleByTeamName " + User.getToken());
        ObservableList<String> items = FXCollections.observableArrayList (taskTitles);
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

    public void showTasksInfo() throws SQLException, IOException {
        String teamName = AppController.getResult("CurrentTeamName " + User.getToken());
        TasksInfo.setText("Total Tasks: " + AppController.getArraylistResult("DgetTasksTitleByTeamName " + teamName).size() +
                "\nDone Tasks: " + AppController.getArraylistResult("DgetDoneTasksTitleByTeamName " + teamName).size() +
                "\nIn Progress Tasks: " + AppController.getArraylistResult("DgetInProgressTasksTitleByTeamName " + teamName).size() +
                "\nFailed Tasks: " + AppController.getArraylistResult("DgetFailedTasksTitleByTeamName " + teamName).size());
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(new PieChart.Data("Done", AppController.getArraylistResult("DgetDoneTasksTitleByTeamName " + teamName).size()),
                new PieChart.Data("InProgress", AppController.getArraylistResult("DgetInProgressTasksTitleByTeamName " + teamName).size()),
                new PieChart.Data("\nFailed", AppController.getArraylistResult("DgetFailedTasksTitleByTeamName " + teamName).size()));
        TasksChart.setData(pieChartData);
    }

}
