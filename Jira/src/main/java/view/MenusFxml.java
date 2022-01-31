package view;

public enum MenusFxml {

    LOGIN_MENU ("loginMenu.fxml"),
    REGISTER_MENU ("registerMenu.fxml"),
    MEMBER_MAIN_MENU ("MemberMainMenu.fxml"),
    PROFILE_MENU ("ProfileMenu/profileMenu.fxml"),
    TEAM_MENU ("TeamMenu/TeamMenu.fxml"),
    SELECTED_TEAM_MENU ("TeamMenu/SelectedTeamMenu.fxml"),
    SHOW_TEAMS_MENU ("ProfileMenu/showTeamsMenu.fxml"),
    SHOW_MY_PROFILE ("ProfileMenu/showMyProfile.fxml"),
    CHANGE_USERNAME_MENU("ProfileMenu/changeUsernameMenu.fxml"),
    CHANGE_PASSWORD_MENU ("ProfileMenu/changePasswordMenu.fxml"),
    SHOW_LOGS_MENU("ProfileMenu/showLogs.fxml"),
    SHOW_NOTIFICATION_MENU("ProfileMenu/showNotificationMenu.fxml");
    private final String label;

    MenusFxml(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
