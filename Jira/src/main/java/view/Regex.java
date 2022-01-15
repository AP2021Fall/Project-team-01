package view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static final String LOGIN_USER = "^user\\slogin\\s--username\\s(.+)--password\\s(.+)\\s";
    public static final String SHOW_PROFILE = "^Profile\\s--show\\s--myProfile$";
    public static final String SHOW_LOG = "^Profile\\s--show\\slogs$";
    public static final String SHOW_NOTIFICATIONS = "^Profile\\s--show\\snotifications$";
    public static final String EDIT_TITLE = "^edit\\s--task\\s--id(.+)\\s--title\\s(.+)$";
    public static final String EDIT_DISCRIPTION = "^edit\\s--task\\s--id(.+)\\s--description\\s(.+)$";
    public static final String EDIT_PRIORITY = "^edit\\s--task\\s--id(.+)\\s--priority\\s(.+)$";
    public static final String EDIT_DEADLINE = "^edit\\s--task\\s--id(.+)\\s--deadline\\s(.+)$";
    public static final String REMOVE_ASSIGNED_USER = "^edit\\s--task\\s--id(.+)\\s--assignedUsers\\s(.+)\\s--remove$";
    public static final String ADD_ASSIGNED_USER = "^edit\\s--task\\s--id(.+)\\s--assignedUsers\\s(.+)\\s--add$";
    public static String CREATE_USER = "^user\\screate\\s--username\\s(.+)--password1\\s(.+)\\s" +
            "--password2\\s(.+)\\s--email\\sAddress\\s(.+)\\s--role\\s(.+)$";
    public static String CHANGE_PASSWORD = "^Profile\\schange\\s--oldpassword\\s(.+)\\s" +
            "--newpassword\\s(.+)";
    public static String CHANGE_USERNAME = "^Profile\\schange\\s--username\\s(.+)";
    public static String SHOW_TEAMS = "^Profile\\s--showTeams";
    public static String SHOW_TEAM = "^Profile\\s--showTeam\\s(.+)";

    public static Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input.trim());
    }

    public static boolean isPasswordStrong(String newPassword) {
        if (newPassword.length() >= 8 && newPassword.matches(".*[A-Z].*")
                && newPassword.matches(".*[a-z].*") && newPassword.matches(".*[0-9].*"))
            return true;
        return false;
    }
}
