package view.TeamMenu;

import controller.TeamMenuController.TeamMenuController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import view.MenusFxml;
import view.SceneController;

import java.net.URL;
import java.util.ResourceBundle;

public class SelectedTeamMenuGraphic implements Initializable {
    public SceneController sceneController = new SceneController();

    public Label welcome;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        welcome.setText("Team: "+ TeamMenuController.getTeam().getName());
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Grapefruit", 13),
                        new PieChart.Data("Oranges", 25),
                        new PieChart.Data("Plums", 10),
                        new PieChart.Data("Pears", 22),
                        new PieChart.Data("Apples", 30));
        final PieChart chart = new PieChart(pieChartData);
    }

    public void goToRoadMap(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.ROADMAP_MENU.getLabel());
    }

    public void goToChatroom(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.CHATROOM_MENU.getLabel());
    }

    public void goToBoardMenu(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.BOARD_MENU.getLabel());
    }

    public void goToMainMenu(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.MEMBER_MAIN_MENU.getLabel());
    }

    public void goToScoreBoard(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SCOREBOARD.getLabel());
    }

    public void backToTeamMenu(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.TEAM_MENU.getLabel());
    }
}
