package view.ProfileMenu;

import controller.ProfileMenuController.ProfileMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import view.MenusFxml;
import view.SceneController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class showMyProfileGraphic implements Initializable {
    public TextArea textMyProfile ;
    public SceneController sceneController = new SceneController();
    public ImageView profileImage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textMyProfile.setText(ProfileMenuController.showMyProfile());
    }

    public void showTeams(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SHOW_TEAMS_MENU.getLabel());
    }

    public void showMyProfile(ActionEvent actionEvent) {
    }

    public void changeUsername(ActionEvent actionEvent) {
    }

    public void changePassword(ActionEvent actionEvent) {
    }

    public void showLogs(ActionEvent actionEvent) {
    }

    public void showNotifications(ActionEvent actionEvent) {
    }

    public void handleDroppedImage(DragEvent dragEvent) {
        List<File> files = dragEvent.getDragboard().getFiles();
        try {
            Image image = new Image(new FileInputStream(files.get(files.size() - 1)));
            profileImage.setImage(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void handleDragOver(DragEvent dragEvent) {
        if (dragEvent.getDragboard().hasFiles())
            dragEvent.acceptTransferModes(TransferMode.ANY);
    }

}
