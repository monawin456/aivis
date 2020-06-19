package aivis.common;

import java.sql.*;

import aivis.database.DatabaseInfo;

public class user_info {
    public String uID;
    public String Password;
    public String uName;
    public String uEmail;
    public DatabaseInfo databaseInfo;

    public user_info() {
        this.uID = null;
        this.Password = null;
        this.uName = null;
        this.uEmail = null;

        this.databaseInfo = null;
    }

    public user_info(DatabaseInfo databaseInfo) {
        this.uID = null;
        this.Password = null;
        this.uName = null;
        this.uEmail = null;

        this.databaseInfo = databaseInfo;
    }

    public user_info(String uID, String Password, String uName, String uEmail) {
        this.uID = uID;
        this.Password = Password;
        this.uName = uName;
        this.uEmail = uEmail;

        this.databaseInfo = null;
    }

    public user_info(String uID, String Password, String uName, String uEmail, DatabaseInfo databaseInfo) {
        this.uID = uID;
        this.Password = Password;
        this.uName = uName;
        this.uEmail = uEmail;

        this.databaseInfo = databaseInfo;
    }

    public void DBInsert() {
        Connection connection;
        connection = null;
        PreparedStatement pStatement;
        pStatement = null;

        if (uID != null && databaseInfo != null) {
            String sql;
            sql = "INSERT INTO User_Info VALUES (?, ?, ?, ?)";

            try {
                //connection = DriverManager.getConnection(databaseInfo.DB_URL, databaseInfo.USER_NAME, databaseInfo.USER_PASSWORD);
                connection = DriverManager.getConnection(databaseInfo.jdbcUrl, databaseInfo.userName, databaseInfo.password);
                //connection = DriverManager.getConnection(databaseInfo.url, databaseInfo.userName, databaseInfo.password);
                pStatement = connection.prepareStatement(sql);

                pStatement.setString(1, uID);
                pStatement.setString(2, Password);
                pStatement.setString(3, uName);
                pStatement.setString(4, uEmail);

                pStatement.executeUpdate();

                System.out.println("uID: " + uID);
                System.out.println("uName: " + uName);
                System.out.println("uEmail: " + uEmail);
            } catch (SQLException e) {
                System.out.println("DB Connection failed");
                e.printStackTrace();
            } finally {
                try { pStatement.close(); } catch (SQLException e) { e.printStackTrace(); }
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
        PreparedStatement pStatement;
        pStatement = null;
        ResultSet resultSet;
        resultSet = null;

        if (databaseInfo != null) {
            String sql;
            sql = "SELECT * FROM User_Info WHERE uID = ?";

            try {
                //connection = DriverManager.getConnection(databaseInfo.DB_URL, databaseInfo.USER_NAME, databaseInfo.USER_PASSWORD);
                connection = DriverManager.getConnection(databaseInfo.jdbcUrl, databaseInfo.userName, databaseInfo.password);
                //connection = DriverManager.getConnection(databaseInfo.url, databaseInfo.userName, databaseInfo.password);
                pStatement = connection.prepareStatement(sql);

                pStatement.setString(1, uID);

                resultSet = pStatement.executeQuery();

                resultSet.next();
                this.uID = resultSet.getString("uID");
                this.Password = resultSet.getString("Password");
                this.uName = resultSet.getString("uName");
                this.uEmail = resultSet.getString("uEmail");

                System.out.println("uID: " + uID);
                System.out.println("uName: " + uName);
                System.out.println("uEmail: " + uEmail);
            } catch (SQLException e) {
                System.out.println("DB Connection failed");
                e.printStackTrace();
            } finally {
                try { resultSet.close(); } catch (SQLException e) { e.printStackTrace(); }
                try { pStatement.close(); } catch (SQLException e) { e.printStackTrace(); }
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
        PreparedStatement pStatement;
        pStatement = null;

        if (uID != null && databaseInfo != null) {
            String sql;
            sql =
            "UPDATE User_Info SET "+
            "Password = ?, "+
            "uName = ?, "+
            "uEmail = ?, "+
            "WHERE uID = ?";

            try {
                //connection = DriverManager.getConnection(databaseInfo.DB_URL, databaseInfo.USER_NAME, databaseInfo.USER_PASSWORD);
                connection = DriverManager.getConnection(databaseInfo.jdbcUrl, databaseInfo.userName, databaseInfo.password);
                //connection = DriverManager.getConnection(databaseInfo.url, databaseInfo.userName, databaseInfo.password);
                pStatement = connection.prepareStatement(sql);

                pStatement.setString(1, Password);
                pStatement.setString(2, uName);
                pStatement.setString(3, uEmail);
                pStatement.setString(4, uID);

                pStatement.executeUpdate();

                System.out.println("uID: " + uID);
                System.out.println("uName: " + uName);
                System.out.println("uEmail: " + uEmail);
            } catch (SQLException e) {
                System.out.println("DB Connection failed");
                e.printStackTrace();
            } finally {
                try { pStatement.close(); } catch (SQLException e) { e.printStackTrace(); }
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
        PreparedStatement pStatement;
        pStatement = null;

        if(uID != null && databaseInfo != null) {
            String sql;
            sql = "DELETE User_Info WHERE uID = ?";

            try {
                //connection = DriverManager.getConnection(databaseInfo.DB_URL, databaseInfo.USER_NAME, databaseInfo.USER_PASSWORD);
                connection = DriverManager.getConnection(databaseInfo.jdbcUrl, databaseInfo.userName, databaseInfo.password);
                //connection = DriverManager.getConnection(databaseInfo.url, databaseInfo.userName, databaseInfo.password);
                pStatement = connection.prepareStatement(sql);

                pStatement.setString(1, uID);

                uID = null;
                Password = null;
                uName = null;
                uEmail = null;

                System.out.println("Deleted");
            } catch (SQLException e) {
                System.out.println("DB Connection failed");
                e.printStackTrace();
            } finally {
                try { pStatement.close(); } catch (SQLException e) { e.printStackTrace(); }
                try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        else {
            System.out.println("Null Data Error");
        }
    }
}
