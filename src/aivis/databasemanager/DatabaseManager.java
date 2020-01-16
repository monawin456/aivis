package aivis.databasemanager;

import java.sql.*;

import aivis.common.User;

public class DatabaseManager {
    private String JDBC_DRIVER;
    private String DB_URL;
    private String USER_NAME;
    private String USER_PASSWORD;

    public DatabaseManager(String JDBC_DRIVER, String DB_URL, String USER_NAME, String USER_PASSWORD) {
        try {
            this.JDBC_DRIVER = JDBC_DRIVER;
            Class.forName(this.JDBC_DRIVER);
            this.DB_URL = DB_URL;
            this.USER_NAME = USER_NAME;
            this.USER_PASSWORD = USER_PASSWORD;
        } catch (ClassNotFoundException e) {
            System.out.println("DB Driver not found");
            e.printStackTrace();
        }
    }

    public void createUser(User user) {
        Connection connection;
        connection = null;
        Statement statement;
        statement = null;
        ResultSet resultSet;
        resultSet = null;
        
        String sql;
        sql = "INSERT INTO User_Info VALUES ('"+user.uID+"','"+user.Password+"','"+user.uName+"','"+user.uEmail+"');";
        
        try {
            connection = DriverManager.getConnection(DB_URL, USER_NAME, USER_PASSWORD);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            System.out.println("uID: " + user.uID);
            System.out.println("Password: " + user.Password);
            System.out.println("uName: " + user.uName);
            System.out.println("uEmail: " + user.uEmail);
        } catch (SQLException e) {
            System.out.println("DB Connection failed");
            e.printStackTrace();
        } finally {
            try { resultSet.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { statement.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public void readUser(String uID, User user) {
        Connection connection;
        connection = null;
        Statement statement;
        statement = null;
        ResultSet resultSet;
        resultSet = null;

        String sql;
        sql = "SELECT * FROM User_Info WHERE uID = '"+uID+"'";

        try {
            connection = DriverManager.getConnection(DB_URL, USER_NAME, USER_PASSWORD);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            resultSet.next();
            user.uID = uID;
            user.Password = resultSet.getString("Password");
            user.uName = resultSet.getString("uName");
            user.uEmail = resultSet.getString("uEmail");
            System.out.println("uID: " + user.uID);
            System.out.println("Password: " + user.Password);
            System.out.println("uName: " + user.uName);
            System.out.println("uEmail: " + user.uEmail);
        } catch (SQLException e) {
            System.out.println("DB Connection failed");
            e.printStackTrace();
        } finally {
            try { resultSet.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { statement.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public void updateUser(User user) {
        Connection connection;
        connection = null;
        Statement statement;
        statement = null;
        ResultSet resultSet;
        resultSet = null;

        String sql;
        sql = "UPDATE User_Info SET Password = '"+user.Password+"', uName = '"+user.uName+"', uEmail = '"+user.uEmail+"' WHERE uID = '"+user.uID+"';";

        try {
            connection = DriverManager.getConnection(DB_URL, USER_NAME, USER_PASSWORD);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            System.out.println("uID: " + user.uID);
            System.out.println("Password: " + user.Password);
            System.out.println("uName: " + user.uName);
            System.out.println("uEmail: " + user.uEmail);
        } catch (SQLException e) {
            System.out.println("DB Connection failed");
            e.printStackTrace();
        } finally {
            try { resultSet.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { statement.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public void deleteUser(String uID) {
        Connection connection;
        connection = null;
        Statement statement;
        statement = null;
        ResultSet resultSet;
        resultSet = null;

        String sql;
        sql = "DELETE User_Info WHERE uID = '"+uID+"';";

        try {
            connection = DriverManager.getConnection(DB_URL, USER_NAME, USER_PASSWORD);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            System.out.println(uID+"(uID) is Deleted");
        } catch (SQLException e) {
            System.out.println("DB Connection failed");
            e.printStackTrace();
        } finally {
            try { resultSet.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { statement.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}
