package view;

import controller.MainMenuController;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class NewRoleGraphic {
    public SceneController sceneController = new SceneController();
    public ChoiceBox choiceBox;
    public Label alert;

    public void back(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.USERS_OPTIONS.getLabel());
    }

    public void next(ActionEvent actionEvent) {
        String choice = (String) choiceBox.getValue();

    }
}
