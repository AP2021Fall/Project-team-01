package view;

import appController.AppController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PendingTeamsGraphic implements Initializable {
    public ListView listView;
    public SceneController sceneController = new SceneController();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                Runnable updater = new Runnable() {

                    @Override
                    public void run() {
                        update();
                    }
                };

                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }

                    // UI update is run on the Application thread
                    Platform.runLater(updater);
                }
            }

        });
        thread.setDaemon(true);
        thread.start();
        ObservableList<String> items = null;
        try {
            items = FXCollections.observableArrayList (AppController.getArraylistResult("DgetPendingTeams"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        listView.setItems(items);
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String pendingTeam = (String) listView.getSelectionModel().getSelectedItem();
                try {
                    AppController.getOutputStream().writeUTF("pendingTeam " + pendingTeam);
                    AppController.getOutputStream().flush();
                    AppController.getInputStream().readUTF();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sceneController.switchScene(MenusFxml.PENDING_OPTIONS.getLabel());
            }
        });
    }

    private void update() {
        listView.getItems().clear();
        ObservableList<String> items = null;
        try {
            items = FXCollections.observableArrayList (AppController.getArraylistResult("DgetPendingTeams"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        listView.setItems(items);
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String pendingTeam = (String) listView.getSelectionModel().getSelectedItem();
                try {
                    AppController.getOutputStream().writeUTF("pendingTeam " + pendingTeam);
                    AppController.getOutputStream().flush();
                    AppController.getInputStream().readUTF();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sceneController.switchScene(MenusFxml.PENDING_OPTIONS.getLabel());
            }
        });
    }

    public void back(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.ADMIN_MAIN_MENU.getLabel());
    }
}

