package aivis.common;

import java.sql.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import aivis.database.DatabaseInfo;

public class Self_Intro_Doc {
    public String uID;
    public int DocID;
    public String Document;
    public DatabaseInfo databaseInfo;

    public Self_Intro_Doc() {
        this.uID = null;
        this.DocID = 0;
        this.Document = null;

        this.databaseInfo = null;
    }

    public Self_Intro_Doc(DatabaseInfo databaseInfo) {
        this.uID = null;
        this.DocID = 0;
        this.Document = null;

        this.databaseInfo = databaseInfo;
    }

    public Self_Intro_Doc(String uID, int DocID, String filePath) {
        this.uID = uID;
        this.DocID = DocID;
        this.Document = filePath;

        this.databaseInfo = null;
    }

    public Self_Intro_Doc(String uID, int DocID, String filePath, DatabaseInfo databaseInfo) {
        this.uID = uID;
        this.DocID = DocID;
        this.Document = filePath;

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

        if (databaseInfo != null) {
            String sql;
            sql = "INSERT INTO Self_Intro_Doc(uID, Document) VALUES (?, ?)";

            try {
                //connection = DriverManager.getConnection(databaseInfo.DB_URL, databaseInfo.USER_NAME, databaseInfo.USER_PASSWORD);
                connection = DriverManager.getConnection(databaseInfo.jdbcUrl, databaseInfo.userName, databaseInfo.password);
                //connection = DriverManager.getConnection(databaseInfo.url, databaseInfo.userName, databaseInfo.password);
                pStatement = connection.prepareStatement(sql);
                file = new File(Document);
                fis = new FileInputStream(file);

                pStatement.setString(1, uID);
                pStatement.setBinaryStream(2, fis);

                pStatement.executeUpdate();

                System.out.println("uID: " + uID);
                System.out.println("DocID: " + DocID);
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

    public void DBRead(int DocID, String filePath) {
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
            sql = "SELECT * FROM Self_Intro_Doc WHERE DocID = ?";

            try {
                //connection = DriverManager.getConnection(databaseInfo.DB_URL, databaseInfo.USER_NAME, databaseInfo.USER_PASSWORD);
                connection = DriverManager.getConnection(databaseInfo.jdbcUrl, databaseInfo.userName, databaseInfo.password);
                //connection = DriverManager.getConnection(databaseInfo.url, databaseInfo.userName, databaseInfo.password);
                pStatement = connection.prepareStatement(sql);
                this.Document = filePath;
                file = new File(Document);
                fos = new FileOutputStream(file);

                pStatement.setInt(1, DocID);

                resultSet = pStatement.executeQuery();

                resultSet.next();
                this.uID = resultSet.getString("uID");
                this.DocID = resultSet.getInt("DocID");
                input = resultSet.getBinaryStream("Document");
                byte[] buffer = new byte[1024];
                while (input.read(buffer) > 0) {
                    fos.write(buffer);
                }
                System.out.println("uID: " + uID);
                System.out.println("DocID: " + DocID);
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

        if (DocID != 0 && databaseInfo != null) {
            String sql;
            sql =
            "UPDATE Self_Intro_Doc SET "+
            "uID = ?, "+
            "Document = ?, "+
            "WHERE DocID = ?";

            try {
                //connection = DriverManager.getConnection(databaseInfo.DB_URL, databaseInfo.USER_NAME, databaseInfo.USER_PASSWORD);
                connection = DriverManager.getConnection(databaseInfo.jdbcUrl, databaseInfo.userName, databaseInfo.password);
                //connection = DriverManager.getConnection(databaseInfo.url, databaseInfo.userName, databaseInfo.password);
                pStatement = connection.prepareStatement(sql);
                file = new File(Document);
                fis = new FileInputStream(file);

                pStatement.setString(1, uID);
                pStatement.setBinaryStream(2, fis);
                pStatement.setInt(3, DocID);

                pStatement.executeUpdate();

                System.out.println("uID: " + uID);
                System.out.println("DocID: " + DocID);
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

        if(DocID != 0 && databaseInfo != null) {
            String sql;
            sql = "DELETE Self_Intro_Doc WHERE DocID = ?";

            try {
                //connection = DriverManager.getConnection(databaseInfo.DB_URL, databaseInfo.USER_NAME, databaseInfo.USER_PASSWORD);
                connection = DriverManager.getConnection(databaseInfo.jdbcUrl, databaseInfo.userName, databaseInfo.password);
                //connection = DriverManager.getConnection(databaseInfo.url, databaseInfo.userName, databaseInfo.password);
                pStatement = connection.prepareStatement(sql);

                pStatement.setInt(1, DocID);

                uID = null;
                DocID = 0;
                Document = null;

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
