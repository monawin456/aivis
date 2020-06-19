package aivis.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Stack;

import aivis.database.DatabaseInfo;

public class ServerMain {
    public ServerMain serverMain;
    public int numUserProcess;
    public ServerSocket serverSocket;
    //public Thread userProcesses[];
    public Stack<UserProcess> userProcesses;
    public int psUsed[];
    public int port;
    public int maxNumUserProcess;
    public String state;

    public ServerMain() {
        port = 8888;
        maxNumUserProcess = 2;
        psUsed = new int[maxNumUserProcess];
        for(int i = 0; i < maxNumUserProcess; i++){
            psUsed[i] = 0;
        }
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //userProcesses = new Thread[maxNumUserProcess];
        userProcesses = new Stack<UserProcess>();
        serverMain = this;
        numUserProcess = 0;
        // server state
        // "t" = idle, "f" = not working, "n" = none file, "x" = shut down
        state = "t";
    }

    public static void main(String arg[]) {
        ServerMain serverMain = new ServerMain();
        Shell shell = new Shell(serverMain);
        System.out.println("Server Running");
        new Thread(shell).start();
        while (true) {
            if (serverMain.userProcesses.size() < serverMain.maxNumUserProcess) {
                try {
                    String hostname = "localhost";
                    String dbPort = "3306";
                    String dbName = "aivis";
                    String userName = "admin";
                    String password = "1+1=mysql";
                    DatabaseInfo databaseInfo = new DatabaseInfo(hostname, dbPort, dbName, userName, password);

                    serverMain.userProcesses.push(new UserProcess(serverMain, serverMain.serverSocket.accept(), databaseInfo));
                    new Thread(serverMain.userProcesses.peek()).start();
                    System.out.println("Someone Connect to Server");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    serverMain.serverSocket.accept().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
