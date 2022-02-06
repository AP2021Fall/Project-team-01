package view;

import appController.AppController;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


public class RegisterMenuGraphic {
    public ChoiceBox role;
    public TextField username;
    public PasswordField password1;
    public PasswordField password2;
    public TextField email;
    public Label alert;
    public SceneController sceneController = new SceneController();

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
        AppController.getOutputStream().writeUTF("register " + usernameString + " " + password1String + " " + password2String + " " + emailString + " " + roleString);
        AppController.getOutputStream().flush();
        String result = AppController.getInputStream().readUTF();
        if (result.equals("userCreatedSuccessfully")) {
            sceneController.switchScene(MenusFxml.LOGIN_MENU.getLabel());
            return;
        }
        alert.setText(result);
    }

    public void goToLoginMenu(MouseEvent mouseEvent) throws Exception {
        sceneController.switchScene(MenusFxml.LOGIN_MENU.getLabel());
    }
}
