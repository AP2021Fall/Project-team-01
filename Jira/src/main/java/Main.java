import javafx.application.Application;
import javafx.stage.Stage;
import models.DatabaseHandler;
import view.*;

import java.sql.SQLException;

public class Main extends Application {
    public static void main(String[] args) throws SQLException {
        DatabaseHandler.connect();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneController.stage = primaryStage;
        new SceneController().switchScene("loginMenu.fxml");
        primaryStage.setTitle("Jira");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
