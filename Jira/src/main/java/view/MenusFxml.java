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
    SHOW_NOTIFICATION_MENU("ProfileMenu/showNotifications.fxml"),
    OPTIONS_MENU ("TeamMenu/OptionsMenu.fxml"),
    SCOREBOARD("TeamMenu/scoreboardAndMembersMenu.fxml"),
    SHOW_PROFILE_MENU("TeamMenu/showProfile.fxml"),
    MEMBER_OPTION_MENU("TeamMenu/memberOptionsMenu.fxml"),
    CHATROOM_MENU("TeamMenu/chatroomMenu.fxml"),
    BOARD_MENU("TeamMenu/boardMenu.fxml"),
    BOARD_MENU_L("TeamMenu/boardMenuL.fxml"),
    SELECTED_BOARD_MENU("TeamMenu/selectedBoardMenu.fxml"),
    CALENDAR_MENU("CalendarMenu.fxml"),
    TASKS_MENU("TasksMenu.fxml"),
    ROADMAP_MENU("TeamMenu/roadMapMenu.fxml"),
    FORCE_TASK("TeamMenu/forceTask.fxml"),
    SELECTED_TASK_OPTIONS("TeamMenu/selectedTaskOptions.fxml");
    private final String label;

    MenusFxml(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
