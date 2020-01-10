package aivis_server;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerMain {
    public ServerMain serverMain;
    public int numUserProcess;
    public ServerSocket serverSocket;
    public Thread userProcesses[];
    public int port;
    public int maxNumUserProcess;
    public String state;

    public ServerMain() {
        port = 8888;
        maxNumUserProcess = 10;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        userProcesses = new Thread[maxNumUserProcess];
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
            if (serverMain.numUserProcess < serverMain.maxNumUserProcess) {
                try {
                    serverMain.userProcesses[serverMain.numUserProcess] = new Thread(new UserProcess(serverMain, serverMain.serverSocket.accept()));
                    serverMain.userProcesses[serverMain.numUserProcess].start();
                    serverMain.numUserProcess++;
                    System.out.println("Someone Connect to Server");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
