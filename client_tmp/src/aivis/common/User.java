package aivis.common;

import java.sql.*;

import aivis.database.DatabaseInfo;

public class User {
    public String uID;
    public String Password;
    public String uName;
    public String uEmail;
    public DatabaseInfo databaseInfo;

    public User() {
        uID = null;
        Password = null;
        uName = null;
        uEmail = null;

        databaseInfo = null;
    }

    public User(String uID, String Password, String uName, String uEmail) {
        this.uID = uID;
        this.Password = Password;
        this.uName = uName;
        this.uEmail = uEmail;

        databaseInfo = null;
    }

    public User(String uID, String Password, String uName, String uEmail, DatabaseInfo databaseInfo) {
        this.uID = uID;
        this.Password = Password;
        this.uName = uName;
        this.uEmail = uEmail;

        this.databaseInfo = databaseInfo;
    }

    public void DBInsert() {
        Connection connection;
        connection = null;
        Statement statement;
        statement = null;
        ResultSet resultSet;
        resultSet = null;

        if(uID != null && databaseInfo != null) {
            String sql;
            sql = "INSERT INTO User_Info VALUES ('"+uID+"','"+Password+"','"+uName+"','"+uEmail+"');";
            
            try {
                connection = DriverManager.getConnection(databaseInfo.DB_URL, databaseInfo.USER_NAME, databaseInfo.USER_PASSWORD);
                statement = connection.createStatement();
                resultSet = statement.executeQuery(sql);

                System.out.println("uID: " + uID);
                System.out.println("Password: " + Password);
                System.out.println("uName: " + uName);
                System.out.println("uEmail: " + uEmail);
            } catch (SQLException e) {
                System.out.println("DB Connection failed");
                e.printStackTrace();
            } finally {
                try { resultSet.close(); } catch (SQLException e) { e.printStackTrace(); }
                try { statement.close(); } catch (SQLException e) { e.printStackTrace(); }
                try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        else {
            System.out.println("Null Data Error");
        }
    }

    public void DBRead(String uID) {
        Connection connection;
        connection = null;
        Statement statement;
        statement = null;
        ResultSet resultSet;
        resultSet = null;

        if(databaseInfo != null) {
            String sql;
            sql = "SELECT * FROM User_Info WHERE uID = '"+uID+"';";

            try {
                connection = DriverManager.getConnection(databaseInfo.DB_URL, databaseInfo.USER_NAME, databaseInfo.USER_PASSWORD);
                statement = connection.createStatement();
                resultSet = statement.executeQuery(sql);

                resultSet.next();
                this.uID = resultSet.getString("uID");
                this.Password = resultSet.getString("Password");
                this.uName = resultSet.getString("uName");
                this.uEmail = resultSet.getString("uEmail");
                System.out.println("uID: " + this.uID);
                System.out.println("Password: " + this.Password);
                System.out.println("uName: " + this.uName);
                System.out.println("uEmail: " + this.uEmail);
            } catch (SQLException e) {
                System.out.println("DB Connection failed");
                e.printStackTrace();
            } finally {
                try { resultSet.close(); } catch (SQLException e) { e.printStackTrace(); }
                try { statement.close(); } catch (SQLException e) { e.printStackTrace(); }
                try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        else {
            System.out.println("Null Data Error");
        }
    }

    public void DBUpdate() {
        Connection connection;
        connection = null;
        Statement statement;
        statement = null;
        ResultSet resultSet;
        resultSet = null;

        if(uID != null && databaseInfo != null) {
            String sql;
            sql = "UPDATE User_Info SET Password = '"+Password+"', uName = '"+uName+"', uEmail = '"+uEmail+"' WHERE uID = '"+uID+"';";

            try {
                connection = DriverManager.getConnection(databaseInfo.DB_URL, databaseInfo.USER_NAME, databaseInfo.USER_PASSWORD);
                statement = connection.createStatement();
                resultSet = statement.executeQuery(sql);

                System.out.println("uID: " + uID);
                System.out.println("Password: " + Password);
                System.out.println("uName: " + uName);
                System.out.println("uEmail: " + uEmail);
            } catch (SQLException e) {
                System.out.println("DB Connection failed");
                e.printStackTrace();
            } finally {
                try { resultSet.close(); } catch (SQLException e) { e.printStackTrace(); }
                try { statement.close(); } catch (SQLException e) { e.printStackTrace(); }
                try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        else {
            System.out.println("Null Data Error");
        }
    }

    public void DBDelete() {
        Connection connection;
        connection = null;
        Statement statement;
        statement = null;
        ResultSet resultSet;
        resultSet = null;

        if(uID != null && databaseInfo != null) {
            String sql;
            sql = "DELETE User_Info WHERE uID = '"+uID+"';";

            try {
                connection = DriverManager.getConnection(databaseInfo.DB_URL, databaseInfo.USER_NAME, databaseInfo.USER_PASSWORD);
                statement = connection.createStatement();
                resultSet = statement.executeQuery(sql);

                uID = null;
                Password = null;
                uName = null;
                uEmail = null;

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
        else {
            System.out.println("Null Data Error");
        }
    }
}
