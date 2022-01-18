package view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    //TODO team name in page 34 doc
    public static final String TEAM_NAME = "";

    public static final String LOGIN_USER = "^user\\slogin\\s--username\\s(.+)--password\\s(.+)\\s";
    public static final String SHOW_PROFILE = "^Profile\\s--show\\s--myProfile$";
    public static final String SHOW_LOG = "^Profile\\s--show\\slogs$";
    public static final String SHOW_NOTIFICATIONS = "^Profile\\s--show\\snotifications$";
    public static final String EDIT_TITLE = "^edit\\s--task\\s--id(.+)\\s--title\\s(.+)$";
    public static final String EDIT_DESCRIPTION = "^edit\\s--task\\s--id(.+)\\s--description\\s(.+)$";
    public static final String EDIT_PRIORITY = "^edit\\s--task\\s--id(.+)\\s--priority\\s(.+)$";
    public static final String EDIT_DEADLINE = "^edit\\s--task\\s--id(.+)\\s--deadline\\s(.+)$";
    public static final String REMOVE_ASSIGNED_USER = "^edit\\s--task\\s--id(.+)\\s--assignedUsers\\s(.+)\\s--remove$";
    public static final String ADD_ASSIGNED_USER = "^edit\\s--task\\s--id(.+)\\s--assignedUsers\\s(.+)\\s--add$";
    public static final String CREATE_USER = "^user\\screate\\s--username\\s(.+)--password1\\s(.+)\\s" +
            "--password2\\s(.+)\\s--email\\sAddress\\s(.+)\\s--role\\s(.+)$";
    public static final String CHANGE_PASSWORD = "^Profile\\schange\\s--oldpassword\\s(.+)\\s" +
            "--newpassword\\s(.+)";
    public static final String CHANGE_USERNAME = "^Profile\\schange\\s--username\\s(.+)$";
    public static final String SHOW_TEAMS = "^Profile\\s--showTeams$";
    public static final String SHOW_TEAM = "^Profile\\s--showTeam\\s(.+)$";
    public static final String SHOW_DEADLINES = "^calendar --show deadlines$";
    public static final String SHOW_COMMENTS = "^task (\\d+) comments --show$";
    public static final String ADD_COMMENT = "^task (\\d+) comment (.+) --add$";

    public static final String CREATE_BOARD = "^board --new --name (.+)$";
    public static final String REMOVE_BOARD = "^board --remove --name (.+)$";
    public static final String SELECT_BOARD = "^board --select --name (.+)$";
    public static final String DESELECT_BOARD = "^board --deselect$";
    public static final String ADD_CATEGORY = "^board --new --category (.+) --name (.+)$";
    public static final String ADD_CATEGORY_TO_COLUMN = "^board --new --category (.+) --column (.+) --name (.+)$";
    public static final String COMPLETE_BOARD_FIRST_STEP = "^board --done --name (.+)$";
    public static final String ADD_TASK_TO_BOARD = "^board --add (.+) --name (.+)$";
    public static final String ASSIGN_TASK_TO_MEMBER = "^board --assign (.+) --task (.+) --name (.+)$";
    public static final String FORCE_TASK_TO_CATEGORY = "^board --force --category (.+) --task (.+) --name (.+)$";
    public static final String TASK_TO_NEXT = "^board --category next --task (.+) --name (.+)$";
    public static final String SHOW_TASKS_IN_CATEGORY = "^board --show --category (.+) --board (.+)$";
    public static final String SHOW_FAILED_AND_DONE = "^board --show (done|failed) --name --board (.+)$";
    public static final String RENEW_FAILED_TASK = "^board --open --task (.+) (--assign (.+))? --deadline (.+) (--category (.+))? --name (.+)$";
    public static final String SHOW_BOARD = "^Board --show --name (.+)$";
    public static final String INVALID_COMMAND = "^invalid command!$";

    public static final String ENTER_TEAM = "^Enter Team (.+)$";
    public static final String ENTER_MENU = "^Enter Menu (.+)$";
    public static final String SHOW_SCOREBOARD = "^Scoreboard --show$";
    public static final String BACK = "^back$";
    public static final String SHOW_ROADMAP = "^Roadmap --show$";
    public static final String SHOW_CHAT = "^Chatroom --show$";
    public static final String SEND_MESSAGE = "^send --message (.+)$";
    public static final String SHOW_TASKS = "^show tasks$";
    public static final String SHOW_TASK_BY_ID = "^show task --id (.+)$";
    public static Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input.trim());
    }

    public static boolean isPasswordStrong(String newPassword) {
        if (newPassword.length() >= 8 && newPassword.matches(".*[A-Z].*") && newPassword.matches(".*[0-9].*"))
            return true;
        return false;
    }
}
