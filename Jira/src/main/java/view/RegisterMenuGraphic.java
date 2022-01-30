package view;

import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.SQLException;


public class RegisterMenuGraphic extends Application {
    public static Stage stage;
    public ChoiceBox role;
    public TextField username;
    public PasswordField password1;
    public PasswordField password2;
    public TextField email;
    public Label alert;


    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Pane pane = FXMLLoader.load(getClass().getResource("registerMenu.fxml"));
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
    }

    public void register(MouseEvent mouseEvent) throws Exception {
        String usernameString = username.getText();
        String password1String = password1.getText();
        String password2String = password2.getText();
        String emailString = email.getText();
        String roleString = (String) role.getValue();
        if (usernameString.isEmpty() || password1String.isEmpty() ||
                password2String.isEmpty() || emailString.isEmpty()) {
            alert.setText("please fill out all fields");
            return;
        }
        String result = LoginController.createUser(usernameString, password1String,
                password2String, emailString, roleString);
        if (result.equals("user created successfully!")) {
            new LoginMenuGraphic().start(stage);
        }
        alert.setText(result);
    }

    public void goToLoginMenu(MouseEvent mouseEvent) throws Exception {
        new LoginMenuGraphic().start(stage);
    }
}
