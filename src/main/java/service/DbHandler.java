package service;

import model.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbHandler {
    private static final String CON_STR = "jdbc:sqlite:server_json.db";
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;
    JwtService jwtService = new JwtService();

    public void generateJwt(String nameUser, String passwordUser) throws ClassNotFoundException, SQLException {
        statmt = statmt();
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
            statmt.executeUpdate("UPDATE users SET jwtKey = '" + jwtToken + "' WHERE user='" + nameUser + "'");
            System.out.println(nameUser + " - " + jwtToken);
        } else {
            System.out.println("пароль или имя неверны");
        }
    }
    public void addSendMessage (String jwtKey, String name, String message)
            throws SQLException, ClassNotFoundException {
        statmt = statmt();
        resSet = statmt.executeQuery("SELECT * FROM users WHERE user='"+name+"'");
        String jwtKeyFromBd = resSet.getString("jwtKey");
        /*if (jwtKey.equals(jwtKeyFromBd)) {
            statmt.executeUpdate("INSERT INTO messages (user, message) VALUES ('"+name+"','"+message+"')");
        }*/
        if (jwtService.validateAccessToken(jwtKeyFromBd)) {
            if (jwtKey.equals(jwtKeyFromBd)) {
                statmt.executeUpdate("INSERT INTO messages (user, message) VALUES ('"+name+"','"+message+"')");
                System.out.println("запись:  "+ message+"  добавлена");
            }
        }
    }
    public List<String> getLastMessages(String jwtKey,String name, int count)
            throws SQLException, ClassNotFoundException {
        ArrayList<String> lastMessages = new ArrayList<>();
        statmt = statmt();
        resSet = statmt.executeQuery("SELECT * FROM messages ORDER BY id DESC LIMIT '"+count+"'");
        while (resSet.next()) {
            String message = resSet.getString("message");
            lastMessages.add(message);
        }
        return lastMessages;
    }
    private Statement statmt() throws ClassNotFoundException, SQLException {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection(CON_STR);
        statmt = conn.createStatement();
        return statmt;
    }

}
