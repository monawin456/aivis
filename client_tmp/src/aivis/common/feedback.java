package aivis.common;

import java.sql.*;

import aivis.database.DatabaseInfo;

public class feedback {
    public String Interview_Number;
    public int rank;
    public int face;
    public int eye;
    public int voice_speed;
    public int voice_tone;
    public DatabaseInfo databaseInfo;

    public feedback() {
        Interview_Number = null;
        rank = 0;
        face = 0;
        eye = 0;
        voice_speed = 0;
        voice_tone = 0;

        databaseInfo = null;
    }

    public feedback(String Interview_Number, int rank, int face, int eye, int voice_speed, int voice_tone) {
        this.Interview_Number = Interview_Number;
        this.rank = rank;
        this.face = face;
        this.eye = eye;
        this.voice_speed = voice_speed;
        this.voice_tone = voice_tone;

        databaseInfo = null;
    }

    public feedback(String Interview_Number, int rank, int face, int eye, int voice_speed, int voice_tone, DatabaseInfo databaseInfo) {
        this.Interview_Number = Interview_Number;
        this.rank = rank;
        this.face = face;
        this.eye = eye;
        this.voice_speed = voice_speed;
        this.voice_tone = voice_tone;

        this.databaseInfo = databaseInfo;
    }

    public void DBInsert() {
        Connection connection;
        connection = null;
        Statement statement;
        statement = null;
        ResultSet resultSet;
        resultSet = null;

        if(Interview_Number != null && databaseInfo != null) {
            String sql;
            sql = "INSERT INTO feedback VALUES ('"+Interview_Number+"',"+rank+","+face+","+eye+","+voice_speed+","+voice_tone+");";
            
            try {
                connection = DriverManager.getConnection(databaseInfo.DB_URL, databaseInfo.USER_NAME, databaseInfo.USER_PASSWORD);
                statement = connection.createStatement();
                resultSet = statement.executeQuery(sql);

                System.out.println("Interview_Number: " + Interview_Number);
                System.out.println("rank: " + rank);
                System.out.println("face: " + face);
                System.out.println("eye: " + eye);
                System.out.println("voice_speed: " + voice_speed);
                System.out.println("voice_tone: " + voice_tone);
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

    public void DBRead(String Interview_Number) {
        Connection connection;
        connection = null;
        Statement statement;
        statement = null;
        ResultSet resultSet;
        resultSet = null;

        if(databaseInfo != null) {
            String sql;
            sql = "SELECT * FROM feedback WHERE Interview_Number = '"+Interview_Number+"';";

            try {
                connection = DriverManager.getConnection(databaseInfo.DB_URL, databaseInfo.USER_NAME, databaseInfo.USER_PASSWORD);
                statement = connection.createStatement();
                resultSet = statement.executeQuery(sql);

                resultSet.next();
                this.Interview_Number = resultSet.getString("Interview_Number");
                this.rank = resultSet.getInt("rank");
                this.face = resultSet.getInt("face");
                this.eye = resultSet.getInt("eye");
                this.voice_speed = resultSet.getInt("voice_speed");
                this.voice_tone = resultSet.getInt("voice_tone");
                System.out.println("Interview_Number: " + this.Interview_Number);
                System.out.println("rank: " + this.rank);
                System.out.println("face: " + this.face);
                System.out.println("eye: " + this.eye);
                System.out.println("voice_speed: " + this.voice_speed);
                System.out.println("voice_tone: " + this.voice_tone);
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

        if(Interview_Number != null && databaseInfo != null) {
            String sql;
            sql = "UPDATE feedback SET rank = "+rank+", face = "+face+", eye = "+eye+", voice_speed = "+voice_speed+", voice_tone = "+voice_tone+" WHERE Interview_Number = '"+Interview_Number+"';";

            try {
                connection = DriverManager.getConnection(databaseInfo.DB_URL, databaseInfo.USER_NAME, databaseInfo.USER_PASSWORD);
                statement = connection.createStatement();
                resultSet = statement.executeQuery(sql);

                System.out.println("Interview_Number: " + Interview_Number);
                System.out.println("rank: " + rank);
                System.out.println("face: " + face);
                System.out.println("eye: " + eye);
                System.out.println("voice_speed: " + voice_speed);
                System.out.println("voice_tone: " + voice_tone);
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

        if(Interview_Number != null && databaseInfo != null) {
            String sql;
            sql = "DELETE feedback WHERE Interview_Number = '"+Interview_Number+"';";

            try {
                connection = DriverManager.getConnection(databaseInfo.DB_URL, databaseInfo.USER_NAME, databaseInfo.USER_PASSWORD);
                statement = connection.createStatement();
                resultSet = statement.executeQuery(sql);

                Interview_Number = null;
                rank = 0;
                face = 0;
                eye = 0;
                voice_speed = 0;
                voice_tone = 0;

                System.out.println(Interview_Number+"(Interview_Number) is Deleted");
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
