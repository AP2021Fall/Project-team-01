import models.DatabaseHandler;
import view.LoginMenu;
import view.MenuController;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        DatabaseHandler.connect();
        LoginMenu.showLogin();
        MenuController.execute();
    }
}
