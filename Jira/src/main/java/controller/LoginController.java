package controller;

import models.User;
import view.Regex;

public class LoginController {
    public static User currentUser;
    public static boolean isLoggedIn = false;

    public String loginUser(String username, String password) {
        if (User.getUserByUsername(username) == null)
            return ("There is not any user with username: " + username + "!");
        else if (!User.getUserByUsername(username).getPassword().equals(password))
            return ("Username and password didn't match!");
        currentUser = User.getUserByUsername(username);
        isLoggedIn = true;
        return ("user logged in successfully!");
    }

    public String createUser(String username, String password, String confirmPassword, String email) {
        String EMAIL_FORMAT = "^[a-zA-Z0-9.]+@(gmail||yahoo).com$";

        if (User.isUsernameTaken(username))
            return ("user with username " + username + " already exists!");
        else if (!password.equals(confirmPassword))
            return ("Your passwords are not the same!");
        else if (User.isEmailTaken(email))
            return ("User with this email already exists!");
        else if (!Regex.getCommandMatcher(email, EMAIL_FORMAT).matches())
            return ("Email address is invalid!");
        else {
            User user = new User(username, password, email, "Team Member");
            return ("user created successfully!");
        }

    }

}
