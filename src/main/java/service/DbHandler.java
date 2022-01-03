package service;

import java.sql.*;

public class DbHandler {
    private static final String CON_STR = "jdbc:sqlite:server_json.db";
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;
    JwtService jwtService = new JwtService();
    public void generateJwt(String nameUser, String passwordUser) throws ClassNotFoundException, SQLException {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection(CON_STR);
        statmt = conn.createStatement();
        resSet = statmt.executeQuery("SELECT * FROM users");
        boolean check = false;
        while (resSet.next()) {
            String user = resSet.getString("user");
            String password = resSet.getString("password");
            if (user.equals(nameUser) && password.equals(passwordUser)) {
                check = true;
            }

        }
        if (check) {
            String jwtToken = jwtService.generateToken(nameUser);
            System.out.println(nameUser + " - " + jwtToken);
        } else {
            System.out.println("пароль или имя неверны");
        }


    }


}
