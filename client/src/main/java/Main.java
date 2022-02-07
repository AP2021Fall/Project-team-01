import appController.AppController;
import javafx.application.Application;
import javafx.stage.Stage;
import view.*;
import java.sql.SQLException;

public class Main extends Application {
    public static void main(String[] args) throws SQLException {
        AppController.setupConnection();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneController.stage = primaryStage;
        new SceneController().switchScene(MenusFxml.LOGIN_MENU.getLabel());
        primaryStage.setTitle("Jira");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
