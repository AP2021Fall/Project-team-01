package view.ProfileMenu;

import controller.LoginController;
import controller.ProfileMenuController.ProfileMenuController;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import view.MenusFxml;
import view.SceneController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
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
        String username = LoginController.getActiveUser().getUsername();
        String path = "D:\\Project-team-01\\Jira\\src\\main\\resources\\images\\" + username + ".png";
        File file = new File(path);
        if (file.exists()){
            try {
                InputStream inputStream = new FileInputStream(path);
                Image image = new Image(inputStream);
                profileImage.setImage(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void showTeams(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SHOW_TEAMS_MENU.getLabel());
    }

    public void changeUsername(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.CHANGE_USERNAME_MENU.getLabel());
    }

    public void changePassword(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.CHANGE_PASSWORD_MENU.getLabel());
    }

    public void showLogs(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SHOW_LOGS_MENU.getLabel());
    }

    public void showNotifications(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.SHOW_NOTIFICATION_MENU.getLabel());
    }

    public void handleDroppedImage(DragEvent dragEvent) {
        List<File> files = dragEvent.getDragboard().getFiles();
        try {
            Image image = new Image(new FileInputStream(files.get(files.size() - 1)));
            profileImage.setImage(image);
            saveToFile(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void handleDragOver(DragEvent dragEvent) {
        if (dragEvent.getDragboard().hasFiles())
            dragEvent.acceptTransferModes(TransferMode.ANY);
    }

    public void goToMainMenu(ActionEvent actionEvent) {
        sceneController.switchScene(MenusFxml.MEMBER_MAIN_MENU.getLabel());
    }

    public static void saveToFile(Image image) {
        String username = LoginController.getActiveUser().getUsername();
        File outputFile = new File("D:\\Project-team-01\\Jira\\src\\main\\resources\\images\\" + username + ".png");
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(bImage, "png", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
