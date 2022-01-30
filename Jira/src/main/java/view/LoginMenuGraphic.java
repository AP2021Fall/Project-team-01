package view;

import controller.LoginController;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.SQLException;

public class LoginMenuGraphic {
    public static Stage stage;
    public SceneController sceneController = new SceneController();
    public TextField username;
    public PasswordField password;
    public Label alert;

    public void login(MouseEvent mouseEvent) throws SQLException {
        if (!username.getText().isEmpty() && !password.getText().isEmpty()) {
            alert.setText(LoginController.loginUser(username.getText(), password.getText()));
            return;
        }
        alert.setText("please fill out all fields");

    }

    public void signUp(MouseEvent mouseEvent) throws Exception {
        sceneController.switchScene("registerMenu.fxml", stage);
    }
}
