package view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {


    public static Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input.trim());
    }

    public static boolean isPasswordStrong(String newPassword) {
        if(newPassword.length() >= 8 && newPassword.matches(".*[A-Z].*")
                && newPassword.matches(".*[a-z].*") && newPassword.matches(".*[0-9].*"))
            return true;
        return false;
    }
}
