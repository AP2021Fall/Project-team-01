package view;

import appController.AppController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.io.IOException;
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

            String text = ("number of all users : " + AppController.getArraylistResult("DgetAllUsers").size() + "\n"
            + "number of all teams : " + AppController.getArraylistResult("DgetTeams").size() + "\n"
            + "number of done tasks : " + AppController.getArraylistResult("DallDoneTasks").size() + "\n"
            + "number of failed tasks : " + AppController.getArraylistResult("DallFailedTasks").size() + "\n"
            + "top 3 users : \n");
            ArrayList<String> arrayList = AppController.getArraylistResult("DgetAllUsersSortedByScore");
            Iterator iterator =  arrayList.iterator();
            for (int i = 0; i < 3; i++) {
                if (iterator.hasNext())
                    text = text + arrayList.get(i) + "\n";
            }
            textArea.setText(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void back(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.ADMIN_MAIN_MENU.getLabel());
    }
}
