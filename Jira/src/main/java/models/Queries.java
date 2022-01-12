package models;

public class Queries {
    public static final String CREATE_USER =
            "INSERT INTO users (username, password, email, role, notification) VALUES ('%s','%s','%s','%s','[]')";
    public static final String CREATE_TEAM =
            "INSERT INTO teams (name, `creating date`) VALUES ('%s', '%s')";
    public static final String CREATE_TASK =
            "INSERT INTO tasks VALUES (DEFAULT,'%s','%s','%s','%s','%s','%s')";
    public static final String CREATE_BOARD =
            "INSERT INTO boards VALUES (DEFAULT, '[]', '[]', '[]')";

}
