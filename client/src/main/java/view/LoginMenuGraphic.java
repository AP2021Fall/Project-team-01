package view;

import appController.AppController;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.SQLException;

public class LoginMenuGraphic {
    public SceneController sceneController = new SceneController();
    public TextField username;
    public PasswordField password;
    public Label alert;

    public void login(MouseEvent mouseEvent) throws SQLException, IOException {
        if (!username.getText().isEmpty() && !password.getText().isEmpty()) {
            AppController.getOutputStream().writeUTF("login " + username.getText() + " " + password.getText());
            AppController.getOutputStream().flush();
            String result = AppController.getInputStream().readUTF();
            if (result.equals("user logged in successfully!")) {
                switch (getRole(username.getText())) {
                    case "member":

                    case "leader":
                        sceneController.switchScene(MenusFxml.MEMBER_MAIN_MENU.getLabel());
                        break;
                    case "admin":
                        sceneController.switchScene(MenusFxml.ADMIN_MAIN_MENU.getLabel());
                        break;
                    default:

                }
                return;
            }
            alert.setText(result);
            return;
        }
        alert.setText("please fill out all fields");

    }

    private String getRole(String username) throws IOException {
        AppController.getOutputStream().writeUTF("role " + username);
        AppController.getOutputStream().flush();
        return AppController.getInputStream().readUTF();
    }

    public void signUp(MouseEvent mouseEvent) throws Exception {
        sceneController.switchScene("registerMenu.fxml");
    }
}
