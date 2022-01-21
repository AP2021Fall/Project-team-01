package view;

import controller.MainMenuController;

import java.sql.SQLException;

public class ChangeRoleMenu {
    public static void execute(String command) throws SQLException {
        if (command.equals("back")){
            MenuController.currentMenu = Menus.MAIN_MENU;
            MainMenu.showMainMenu();
        }
        else {
            MainMenuController.changeRoleToMember(command);
        }
    }

    public static void showChangeRoleMenu(){
        System.out.println("enter back to go nack to main menu");
    }

}
