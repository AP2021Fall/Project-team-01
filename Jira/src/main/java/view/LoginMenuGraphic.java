package view;

import controller.LoginController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import models.DatabaseHandler;
import javafx.scene.image.Image;

import java.awt.*;
import java.sql.SQLException;

public class LoginMenuGraphic extends Application {
    public static Stage stage;
    public TextField username;
    public PasswordField password;
    public Label alert;



    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Pane pane = FXMLLoader.load(getClass().getResource("loginMenu.fxml"));
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws SQLException {
        DatabaseHandler.connect();
        launch(args);
    }

    public void login(MouseEvent mouseEvent) throws SQLException {
        if (!username.getText().isEmpty() && !password.getText().isEmpty()) {
            alert.setText(LoginController.loginUser(username.getText(), password.getText()));
            return;
        }
        alert.setText("please fill out all fields");

    }

    public void signUp(MouseEvent mouseEvent) throws Exception {
        new RegisterMenuGraphic().start(stage);
    }
}
