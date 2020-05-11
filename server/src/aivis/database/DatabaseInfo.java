package aivis.database;

public class DatabaseInfo {
    // public String JDBC_DRIVER;
    // public String DB_URL;
    // public String USER_NAME;
    // public String USER_PASSWORD;
    public String hostname;
    public String port;
    public String dbName;
    public String userName;
    public String password;
    public String connectionString;

    public DatabaseInfo(String hostname, String port, String dbName, String userName, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.hostname = hostname;
            this.port = port;
            this.dbName = dbName;
            this.userName = userName;
            this.password = password;
            this.connectionString = "jdbc:mysql://"+this.hostname+":"+this.port+"/"+this.dbName+"?user="+this.userName+"&password="+this.password;
        } catch (ClassNotFoundException e) {
            System.out.println("DB Driver not found");
            this.hostname = null;
            this.port = null;
            this.dbName = null;
            this.userName = null;
            this.password = null;
            this.connectionString = null;
            e.printStackTrace();
        }
    }
}
