package view.TeamMenu;

import appController.AppController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.User;
import view.LoginMenuGraphic;
import view.MenusFxml;
import view.SceneController;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class addMemberGraphic implements Initializable {
    public VBox taskShow;
    public ScrollPane scroll;
    public TextField searchBar;
    public SceneController sceneController = new SceneController();
    public Label alert;

    public addMemberGraphic() throws SQLException, IOException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scroll.setBackground(new Background(new BackgroundFill(Color.PURPLE, CornerRadii.EMPTY, Insets.EMPTY)));
        taskShow = new VBox();
        taskShow.getStyleClass().add("color-palette");
        taskShow.setBackground(new Background(new BackgroundFill(Color.PURPLE, CornerRadii.EMPTY, Insets.EMPTY)));
        scroll.setContent(taskShow);
        taskShow.setPrefWidth(320);
        try {
            int teamId = Integer.parseInt(AppController.getResult("CurrentTeamId " + User.getToken()));
            ArrayList<String> tasks = AppController.getArraylistResult("DgetAllUsers ");
            for (String str : tasks) {
                Boolean isUsernameTeamMate = Boolean.parseBoolean(AppController.getResult("DisUsernameTeamMate " + str + " " + teamId));
                if ((!isUsernameTeamMate) && LoginMenuGraphic.getRole(str).equals("member")) {
                    HBox hBox = new HBox();
                    Text text = new Text('\n' + str);
                    text.setFill(Color.WHITE);
                    Font font = new Font("Book Antiqua", 20);
                    hBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            try {

                                if ((!isUsernameTeamMate) && LoginMenuGraphic.getRole(str).equals("member")) {
                                    AppController.getResult("DaddMemberToTeam " + str + " " + teamId);
                                    alert.setText("member added to team successfully");

                                } else
                                    alert.setText("you can not add twice");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    text.setFont(font);
                    hBox.setPrefHeight(60);
                    hBox.getChildren().add(text);
                    taskShow.getChildren().add(hBox);
                }
            }
            tasks.add("\n");
            tasks.add("\n");
            taskShow.setAlignment(Pos.TOP_LEFT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void search(ActionEvent actionEvent) throws SQLException, IOException {
        ObservableList<String> items = FXCollections.observableArrayList(AppController.getArraylistResult("DgetAllUsers "));
        int teamId = Integer.parseInt(AppController.getResult("CurrentTeamId " + User.getToken()));
        taskShow.getChildren().clear();
        List<String> list = searchList(searchBar.getText(), items);
        for (String str : list) {
            Boolean isUsernameTeamMate = Boolean.parseBoolean(AppController.getResult("DisUsernameTeamMate " + str + " " + teamId));
            if ((!isUsernameTeamMate) && LoginMenuGraphic.getRole(str).equals("member")) {
                HBox hBox = new HBox();
                Text text = new Text(str);
                hBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        try {

                            if ((!isUsernameTeamMate) && LoginMenuGraphic.getRole(str).equals("member")) {
                                AppController.getResult("DaddMemberToTeam " + str + " " + teamId);
                                alert.setText("member added to team successfully");

                            } else
                                alert.setText("you can not add twice");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                hBox.setPrefHeight(60);
                hBox.getChildren().add(text);
                taskShow.getChildren().add(hBox);
            }
        }
    }

    private List<String> searchList(String searchWords, List<String> listOfStrings) {
        List<String> searchWordsArray = Arrays.asList(searchWords.trim());
        return listOfStrings.stream().filter(input -> { //input = test
            return searchWordsArray.stream().allMatch(word -> //word = te
                    input.contains(word));
        }).collect(Collectors.toList());
    }

    public void back(ActionEvent actionEvent) throws IOException {
        if (LoginMenuGraphic.getRole(User.getActiveUsername()).equals("leader"))
            sceneController.switchScene(MenusFxml.SELECTED_TEAM_MENU_LEADER.getLabel());
        else
            sceneController.switchScene(MenusFxml.SELECTED_TEAM_MENU.getLabel());
    }

    public void sortByPoints(ActionEvent actionEvent) throws SQLException, IOException {
        int teamId = Integer.parseInt(AppController.getResult("CurrentTeamId " + User.getToken()));
        taskShow.getChildren().clear();
        ArrayList<String> tasks = AppController.getArraylistResult("DgetAllUsersSortedByScore ");
        for (String str : tasks) {
            Boolean isUsernameTeamMate = Boolean.parseBoolean(AppController.getResult("DisUsernameTeamMate " + str + " " + teamId));
            if ((!isUsernameTeamMate) && LoginMenuGraphic.getRole(str).equals("member")) {

                HBox hBox = new HBox();
                Text text = new Text('\n' + str);
                hBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        try {
                            if ((!isUsernameTeamMate) && LoginMenuGraphic.getRole(str).equals("member")) {
                                AppController.getResult("DaddMemberToTeam " + str + " " + teamId);
                                alert.setText("member added to team successfully");
                            } else
                                alert.setText("you can not add twice");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                text.setFill(Color.WHITE);
                Font font = new Font("Book Antiqua", 20);
                text.setFont(font);
                hBox.setPrefHeight(60);
                hBox.getChildren().add(text);
                taskShow.getChildren().add(hBox);
            }
        }

    }
}

