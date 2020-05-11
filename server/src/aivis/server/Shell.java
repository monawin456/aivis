package aivis.server;

import java.util.Scanner;

import aivis.machinelearning.*;
import aivis.database.DatabaseInfo;
import aivis.common.User;

public class Shell implements Runnable{
    private ServerMain serverMain;

    public Shell(ServerMain serverMain){
        this.serverMain = serverMain;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String command = null;
        while(serverMain.state.compareTo("x") != 0){
            System.out.print("Input your command: ");
            command = scanner.nextLine();

            if(command.compareTo("shutdown") == 0){
                shutDown();
            }
            else if (command.compareTo("usercount") == 0){
                userCount();
            }
            else if (command.compareTo("train") == 0){
                train();
            }
            else if (command.compareTo("dbtest") == 0){
                dbtest();
            }
            else {
                System.out.println("Wrong Command");
            }

            command = null;
        }
        scanner.close();
    }

    private void shutDown(){
        System.out.println("Exit Server");
        System.exit(0);
    }

    private void userCount(){
        System.out.print("Number of User: ");
        System.out.println(serverMain.numUserProcess);
    }

    private void train(){
        Chatbot chatbot = new Chatbot();

        System.out.println("Start Train");

        // remove DB
        chatbot.trainChatbot();

        System.out.println("End Train");
    }

    private void dbtest(){
        System.out.println("Start");

        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        String DB_URL = "";
        String USER_NAME = "";
        String USER_PASSWORD = "";

        String hostname;
        String port;
        String dbName;
        String userName;
        String password;

        DatabaseInfo databaseInfo = new DatabaseInfo(hostname, port, dbName, userName, password);

        String uID = "0001";
        String Password = "1111";
        String uName = "tester01";
        String uEmail = "tester01@abc.d";

        User testUser = new User(uID, Password, uName, uEmail, databaseInfo);

        testUser.DBInsert();

        System.out.println("End");
    }
}
