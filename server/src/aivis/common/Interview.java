package aivis.common;

import java.sql.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import aivis.database.DatabaseInfo;

public class Interview {
    public String uID;
    public int DocID;
    public String Interview_Number;
    public String Video;
    public DatabaseInfo databaseInfo;

    public Interview() {
        this.uID = null;
        this.DocID = 0;
        this.Interview_Number = null;
        this.Video = null;

        this.databaseInfo = null;
    }

    public Interview(DatabaseInfo databaseInfo) {
        this.uID = null;
        this.DocID = 0;
        this.Interview_Number = null;
        this.Video = null;

        this.databaseInfo = databaseInfo;
    }

    public Interview(String uID, int DocID, String Interview_Number, String filePath) {
        this.uID = uID;
        this.DocID = DocID;
        this.Interview_Number = Interview_Number;
        this.Video = filePath;

        this.databaseInfo = null;
    }

    public Interview(String uID, int DocID, String Interview_Number, String filePath, DatabaseInfo databaseInfo) {
        this.uID = uID;
        this.DocID = DocID;
        this.Interview_Number = Interview_Number;
        this.Video = filePath;

        this.databaseInfo = databaseInfo;
    }

    public void DBInsert() {
        Connection connection;
        connection = null;
        PreparedStatement pStatement;
        pStatement = null;
        File file;
        file = null;
        FileInputStream fis;
        fis = null;

        if (Interview_Number != null && databaseInfo != null) {
            String sql;
            sql = "INSERT INTO Interview(uID, Video, DocID) VALUES (?, ?, ?)";

            try {
                //connection = DriverManager.getConnection(databaseInfo.DB_URL, databaseInfo.USER_NAME, databaseInfo.USER_PASSWORD);
                connection = DriverManager.getConnection(databaseInfo.jdbcUrl, databaseInfo.userName, databaseInfo.password);
                //connection = DriverManager.getConnection(databaseInfo.url, databaseInfo.userName, databaseInfo.password);
                pStatement = connection.prepareStatement(sql);
                file = new File(Video);
                fis = new FileInputStream(file);

                pStatement.setString(1, uID);
                pStatement.setBinaryStream(2, fis);
                pStatement.setInt(3, DocID);

                pStatement.executeUpdate();

                System.out.println("uID: " + uID);
                System.out.println("DocID: " + DocID);
                System.out.println("Interview_Number: " + Interview_Number);
            } catch (SQLException e) {
                System.out.println("DB Connection failed");
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try { fis.close(); } catch (IOException e) { e.printStackTrace(); }
                try { pStatement.close(); } catch (SQLException e) { e.printStackTrace(); }
                try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        else {
            System.out.println("Null Data Error");
        }
    }

    public void DBRead(String Interview_Number, String filePath) {
        Connection connection;
        connection = null;
        PreparedStatement pStatement;
        pStatement = null;
        File file;
        file = null;
        FileOutputStream fos;
        fos = null;
        ResultSet resultSet;
        resultSet = null;
        InputStream input;
        input = null;

        if (databaseInfo != null) {
            String sql;
            sql = "SELECT * FROM Interview WHERE Interview_Number = ?";

            try {
                //connection = DriverManager.getConnection(databaseInfo.DB_URL, databaseInfo.USER_NAME, databaseInfo.USER_PASSWORD);
                connection = DriverManager.getConnection(databaseInfo.jdbcUrl, databaseInfo.userName, databaseInfo.password);
                //connection = DriverManager.getConnection(databaseInfo.url, databaseInfo.userName, databaseInfo.password);
                pStatement = connection.prepareStatement(sql);
                this.Video = filePath;
                file = new File(Video);
                fos = new FileOutputStream(file);

                pStatement.setString(1, Interview_Number);

                resultSet = pStatement.executeQuery();

                resultSet.next();
                this.uID = resultSet.getString("uID");
                this.DocID = resultSet.getInt("DocID");
                this.Interview_Number = resultSet.getString("Interview_Number");
                input = resultSet.getBinaryStream("Video");
                byte[] buffer = new byte[1024];
                while (input.read(buffer) > 0) {
                    fos.write(buffer);
                }
                System.out.println("uID: " + uID);
                System.out.println("DocID: " + DocID);
                System.out.println("Interview_Number: " + Interview_Number);
            } catch (SQLException e) {
                System.out.println("DB Connection failed");
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try { input.close(); } catch (IOException e) { e.printStackTrace(); }
                try { resultSet.close(); } catch (SQLException e) { e.printStackTrace(); }
                try { fos.close(); } catch (IOException e) { e.printStackTrace(); }
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
        File file;
        file = null;
        FileInputStream fis;
        fis = null;

        if (Interview_Number != null && databaseInfo != null) {
            String sql;
            sql =
            "UPDATE Interview SET "+
            "uID = ?, "+
            "DocID = ?, "+
            "Video = ?, "+
            "WHERE Interview_Number = ?";

            try {
                //connection = DriverManager.getConnection(databaseInfo.DB_URL, databaseInfo.USER_NAME, databaseInfo.USER_PASSWORD);
                connection = DriverManager.getConnection(databaseInfo.jdbcUrl, databaseInfo.userName, databaseInfo.password);
                //connection = DriverManager.getConnection(databaseInfo.url, databaseInfo.userName, databaseInfo.password);
                pStatement = connection.prepareStatement(sql);
                file = new File(Video);
                fis = new FileInputStream(file);

                pStatement.setString(1, uID);
                pStatement.setInt(2, DocID);
                pStatement.setBinaryStream(3, fis);
                pStatement.setString(4, Interview_Number);

                pStatement.executeUpdate();

                System.out.println("uID: " + uID);
                System.out.println("DocID: " + DocID);
                System.out.println("Interview_Number: " + Interview_Number);
            } catch (SQLException e) {
                System.out.println("DB Connection failed");
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try { fis.close(); } catch (IOException e) { e.printStackTrace(); }
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

        if(Interview_Number != null && databaseInfo != null) {
            String sql;
            sql = "DELETE Interview WHERE Interview_Number = ?";

            try {
                //connection = DriverManager.getConnection(databaseInfo.DB_URL, databaseInfo.USER_NAME, databaseInfo.USER_PASSWORD);
                connection = DriverManager.getConnection(databaseInfo.jdbcUrl, databaseInfo.userName, databaseInfo.password);
                //connection = DriverManager.getConnection(databaseInfo.url, databaseInfo.userName, databaseInfo.password);
                pStatement = connection.prepareStatement(sql);

                pStatement.setString(1, Interview_Number);

                uID = null;
                DocID = 0;
                Interview_Number = null;
                Video = null;

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
