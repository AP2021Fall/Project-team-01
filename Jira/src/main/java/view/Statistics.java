package view;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import models.DatabaseHandler;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

public class Statistics implements Initializable {

    public TextArea textArea;
    public SceneController sceneController = new SceneController();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {

            String text = ("number of all users : " + DatabaseHandler.getAllUsers().size() + "\n"
                    + "number of all teams : " + DatabaseHandler.getTeams().size() + "\n"
                    + "number of done tasks : " + DatabaseHandler.allDoneTasks().size() + "\n"
                    + "number of failed tasks : " + DatabaseHandler.allFailedTasks().size() + "\n"
                    + "top 3 users : \n");
            ArrayList<String> arrayList = DatabaseHandler.getAllUsersSortedByScore();
            Iterator iterator =  arrayList.iterator();
            for (int i = 0; i < 3; i++) {
                if (iterator.hasNext())
                    text = text + arrayList.get(i) + "\n";
            }
            textArea.setText(text);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void back(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.ADMIN_MAIN_MENU.getLabel());
    }
}

