package aivis.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import aivis.common.FileManager;
import aivis.machinelearning.*;

public class UserProcess implements Runnable {
    private Socket socket;
    private ServerMain serverMain;
    private FileManager fileManager;
    private int id;

    public UserProcess(ServerMain serverMain, Socket socket) throws Exception
    {
        this.serverMain = serverMain;
        this.socket = socket;
        fileManager = new FileManager();
    }

    public void run()
    {
        DataInputStream dis = null;
        DataOutputStream dos = null;
        try
        {
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            id = serverMain.userProcesses.indexOf(this);
            while(true)
            {
                System.out.println("Wait Client's Request");
                String msg = dis.readUTF();

                System.out.println("Recieve Request: " + msg);

                if(msg.compareTo("question") == 0){
                    System.out.println("Start Create Question");

                    Textrank textrank = new Textrank();
                    Chatbot chatbot = new Chatbot();

                    System.out.println("Start Textrank");
                    textrank.createTextrank();
                    System.out.println("End Textrank");

                    System.out.println("Start Keyword Matching");
                    chatbot.excuteChatbot();
                    System.out.println("End Keyword Matching");

                    System.out.println("End Create Question");
                    
                    System.out.println("Send Question Data to Client");
                    fileManager.send(dos, "../data", "questions.txt");
                }
                else if(msg.compareTo("video") == 0){
                    System.out.println("Start Video Analysis");

                    EyeChecker eyeChecker = new EyeChecker();
                    EmotionChecker emotionChecker = new EmotionChecker();

                    eyeChecker.excute();
                    emotionChecker.excute();

                    System.out.println("End Video Analysis");
                }
                else if(msg.compareTo("upload") == 0){
                    System.out.println("Recieve Self Introduction Document Data");
                    fileManager.recieve(dis, "../data", "data.txt");
                }
            }
        } catch(Exception e) {
            System.out.println("Client Leave Server");
            e.printStackTrace();
        } finally {
            try { dos.close(); } catch (IOException e) { e.printStackTrace(); }
            try { dis.close(); } catch (IOException e) { e.printStackTrace(); }
            try { socket.close(); } catch (IOException e) { e.printStackTrace(); }

            serverMain.psUsed[id] = 0;
            serverMain.userProcesses.remove(this);
        }
    }
}
