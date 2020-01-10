package aivis_server;

import java.util.Scanner;

import aivis_server.machinelearning.*;

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

        System.out.println("Start Train...");

        // remove DB
        chatbot.trainChatbot();

        System.out.println("End Train");
    }
}
