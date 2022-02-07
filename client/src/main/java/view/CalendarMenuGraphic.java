package view;

import appController.AppController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CalendarMenuGraphic implements Initializable {
    public ObservableList<String> items = FXCollections.observableArrayList(AppController.getArraylistResult("DgetTasksByUsername " + User.getActiveUsername()));
    public VBox taskShow;
    public ScrollPane scroll;
    public TextField searchBar;
    public SceneController sceneController = new SceneController();
    public CalendarMenuGraphic() throws SQLException, IOException {
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
            AppController.getOutputStream().writeUTF("getTasksByUsername " + User.getActiveUsername());
            AppController.getOutputStream().flush();
            ArrayList<String> tasks = new Gson().fromJson(AppController.getInputStream().readUTF(),
                    new TypeToken<List<String>>() {
                    }.getType());
            for (String str : tasks) {
                HBox hBox = new HBox();
                Text text = new Text('\n' + str);
                text.setFill(Color.WHITE);
                Font font = new Font("Book Antiqua", 20);
                text.setFont(font);
                hBox.setPrefHeight(60);
                hBox.getChildren().add(text);
                String[] get ;
                get = str.split(" ");
                int id = Integer.parseInt(get[0]);
                String result = AppController.getResult("DgetNumDeadline " + id);
                int state = Integer.parseInt(result);
                if ( state == 0)
                hBox.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                if ( state == 1)
                    hBox.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
                if ( state == 2)
                    hBox.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                taskShow.getChildren().add(hBox);
            }
            tasks.add("\n");
            tasks.add("\n");
            taskShow.setAlignment(Pos.TOP_LEFT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void search(ActionEvent actionEvent) {
        taskShow.getChildren().clear();
        List<String> list = searchList(searchBar.getText(), items);
        for (String str : list)
            taskShow.getChildren().add(new Text(str));
    }

    private List<String> searchList(String searchWords, List<String> listOfStrings) {
        List<String> searchWordsArray = Arrays.asList(searchWords.trim());
        return listOfStrings.stream().filter(input -> { //input = test
            return searchWordsArray.stream().allMatch(word -> //word = te
                    input.contains(word));
        }).collect(Collectors.toList());
    }

    public void back(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.MEMBER_MAIN_MENU.getLabel());
    }

    public void sortDeadline(ActionEvent actionEvent) throws SQLException, IOException {
        taskShow.getChildren().clear();
        ArrayList<String> tasks = AppController.getArraylistResult("DsortTaskTitlesByDeadline " + User.getActiveUsername());
        for (String str : tasks) {
            HBox hBox = new HBox();
            Text text = new Text('\n' + str);
            text.setFill(Color.WHITE);
            Font font = new Font("Book Antiqua", 20);
            text.setFont(font);
            hBox.setPrefHeight(60);
            hBox.getChildren().add(text);
            String[] get ;
            get = str.split(" ");
            int id = Integer.parseInt(get[0]);
            String result = AppController.getResult("DgetNumDeadline " + id);
            int state = Integer.parseInt(result);
            if ( state == 0)
                hBox.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
            if ( state == 1)
                hBox.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
            if ( state == 2)
                hBox.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
            taskShow.getChildren().add(hBox);
        }
    }
}
