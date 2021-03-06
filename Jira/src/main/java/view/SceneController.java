package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    public static Stage stage;
    public void switchScene(String sceneName) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(sceneName));
        Pane pane = null;
        try {
            pane = fxmlLoader.load();
            stage.setScene(new Scene(pane));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}