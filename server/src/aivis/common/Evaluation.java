package aivis.common;

import java.sql.*;

import aivis.database.DatabaseInfo;

public class Evaluation {
    public int Interview_Number;
    public int Eye;
    public int Face;
    public int Voice_Speed;
    public int Voice_Tone;
    public DatabaseInfo databaseInfo;

    public Evaluation() {
        this.Interview_Number = 0;
        this.Eye = 0;
        this.Face = 0;
        this.Voice_Speed = 0;
        this.Voice_Tone = 0;

        this.databaseInfo = null;
    }

    public Evaluation(DatabaseInfo databaseInfo) {
        this.Interview_Number = 0;
        this.Eye = 0;
        this.Face = 0;
        this.Voice_Speed = 0;
        this.Voice_Tone = 0;

        this.databaseInfo = databaseInfo;
    }

    public Evaluation(int Interview_Number, int Eye, int Face, int Voice_Speed, int Voice_Tone) {
        this.Interview_Number = Interview_Number;
        this.Eye = Eye;
        this.Face = Face;
        this.Voice_Speed = Voice_Speed;
        this.Voice_Tone = Voice_Tone;

        this.databaseInfo = null;
    }

    public Evaluation(int Interview_Number, int Eye, int Face, int Voice_Speed, int Voice_Tone, DatabaseInfo databaseInfo) {
        this.Interview_Number = Interview_Number;
        this.Eye = Eye;
        this.Face = Face;
        this.Voice_Speed = Voice_Speed;
        this.Voice_Tone = Voice_Tone;

        this.databaseInfo = databaseInfo;
    }

    public void DBInsert() {
        Connection connection;
        connection = null;
        PreparedStatement pStatement;
        pStatement = null;

        if (Interview_Number != 0 && databaseInfo != null) {
            String sql;
            sql = "INSERT INTO Evaluation VALUES (?, ?, ?, ?, ?)";

            try {
                //connection = DriverManager.getConnection(databaseInfo.DB_URL, databaseInfo.USER_NAME, databaseInfo.USER_PASSWORD);
                connection = DriverManager.getConnection(databaseInfo.jdbcUrl, databaseInfo.userName, databaseInfo.password);
                //connection = DriverManager.getConnection(databaseInfo.url, databaseInfo.userName, databaseInfo.password);
                pStatement = connection.prepareStatement(sql);

                pStatement.setInt(1, Interview_Number);
                pStatement.setInt(2, Eye);
                pStatement.setInt(3, Face);
                pStatement.setInt(4, Voice_Speed);
                pStatement.setInt(4, Voice_Tone);

                pStatement.executeUpdate();

                System.out.println("Interview_Number: " + Interview_Number);
                System.out.println("Eye: " + Eye);
                System.out.println("Face: " + Face);
                System.out.println("Voice_Speed: " + Voice_Speed);
                System.out.println("Voice_Tone: " + Voice_Tone);
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

    public void DBRead(int Interview_Number) {
        Connection connection;
        connection = null;
        PreparedStatement pStatement;
        pStatement = null;
        ResultSet resultSet;
        resultSet = null;

        if (databaseInfo != null) {
            String sql;
            sql = "SELECT * FROM Evaluation WHERE Interview_Number = ?";

            try {
                //connection = DriverManager.getConnection(databaseInfo.DB_URL, databaseInfo.USER_NAME, databaseInfo.USER_PASSWORD);
                connection = DriverManager.getConnection(databaseInfo.jdbcUrl, databaseInfo.userName, databaseInfo.password);
                //connection = DriverManager.getConnection(databaseInfo.url, databaseInfo.userName, databaseInfo.password);
                pStatement = connection.prepareStatement(sql);

                pStatement.setInt(1, Interview_Number);

                resultSet = pStatement.executeQuery();

                resultSet.next();
                this.Interview_Number = resultSet.getInt("Interview_Number");
                this.Eye = resultSet.getInt("Eye");
                this.Face = resultSet.getInt("Face");
                this.Voice_Speed = resultSet.getInt("Voice_Speed");
                this.Voice_Tone = resultSet.getInt("Voice_Tone");

                System.out.println("Interview_Number: " + Interview_Number);
                System.out.println("Eye: " + Eye);
                System.out.println("Face: " + Face);
                System.out.println("Voice_Speed: " + Voice_Speed);
                System.out.println("Voice_Tone: " + Voice_Tone);
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

        if (Interview_Number != 0 && databaseInfo != null) {
            String sql;
            sql =
            "UPDATE Evaluation SET "+
            "Eye = ?, "+
            "Face = ?, "+
            "Voice_Speed = ?, "+
            "Voice_Tone = ?, "+
            "WHERE Interview_Number = ?";

            try {
                //connection = DriverManager.getConnection(databaseInfo.DB_URL, databaseInfo.USER_NAME, databaseInfo.USER_PASSWORD);
                connection = DriverManager.getConnection(databaseInfo.jdbcUrl, databaseInfo.userName, databaseInfo.password);
                //connection = DriverManager.getConnection(databaseInfo.url, databaseInfo.userName, databaseInfo.password);
                pStatement = connection.prepareStatement(sql);

                pStatement.setInt(1, Eye);
                pStatement.setInt(2, Face);
                pStatement.setInt(3, Voice_Speed);
                pStatement.setInt(4, Voice_Tone);
                pStatement.setInt(5, Interview_Number);

                pStatement.executeUpdate();

                System.out.println("Interview_Number: " + Interview_Number);
                System.out.println("Eye: " + Eye);
                System.out.println("Face: " + Face);
                System.out.println("Voice_Speed: " + Voice_Speed);
                System.out.println("Voice_Tone: " + Voice_Tone);
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

        if(Interview_Number != 0 && databaseInfo != null) {
            String sql;
            sql = "DELETE Evaluation WHERE Interview_Number = ?";

            try {
                //connection = DriverManager.getConnection(databaseInfo.DB_URL, databaseInfo.USER_NAME, databaseInfo.USER_PASSWORD);
                connection = DriverManager.getConnection(databaseInfo.jdbcUrl, databaseInfo.userName, databaseInfo.password);
                //connection = DriverManager.getConnection(databaseInfo.url, databaseInfo.userName, databaseInfo.password);
                pStatement = connection.prepareStatement(sql);

                pStatement.setInt(1, Interview_Number);

                Interview_Number = 0;
                Eye = 0;
                Face = 0;
                Voice_Speed = 0;
                Voice_Tone = 0;

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
