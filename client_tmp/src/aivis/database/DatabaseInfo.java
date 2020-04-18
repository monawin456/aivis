package aivis.database;

public class DatabaseInfo {
    public String JDBC_DRIVER;
    public String DB_URL;
    public String USER_NAME;
    public String USER_PASSWORD;

    public DatabaseInfo(String JDBC_DRIVER, String DB_URL, String USER_NAME, String USER_PASSWORD) {
        try {
            this.JDBC_DRIVER = JDBC_DRIVER;
            Class.forName(this.JDBC_DRIVER);
            this.DB_URL = DB_URL;
            this.USER_NAME = USER_NAME;
            this.USER_PASSWORD = USER_PASSWORD;
        } catch (ClassNotFoundException e) {
            System.out.println("DB Driver not found");
            this.JDBC_DRIVER = null;
            this.DB_URL = null;
            this.USER_NAME = null;
            this.USER_PASSWORD = null;
            e.printStackTrace();
        }
    }
}
