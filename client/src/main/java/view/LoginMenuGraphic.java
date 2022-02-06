package view;

import appController.AppController;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import models.User;
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
            String[] results = result.split("\\s");
            if (results[0].equals("userLoggedInSuccessfully")) {
                User.setToken(results[1]);
                User.setActiveUsername(username.getText());
                switch (getRole(username.getText())) {
                    case "member":
                        sceneController.switchScene(MenusFxml.MEMBER_MAIN_MENU.getLabel());
                    case "leader":
                        sceneController.switchScene(MenusFxml.LEADER_MAIN_MENU.getLabel());
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

    public static String getRole(String username) throws IOException {
        AppController.getOutputStream().writeUTF("role " + username);
        AppController.getOutputStream().flush();
        return AppController.getInputStream().readUTF();
    }

    public void signUp(MouseEvent mouseEvent) throws Exception {
        sceneController.switchScene("registerMenu.fxml");
    }
}
