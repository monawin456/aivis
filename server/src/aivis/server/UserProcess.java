package aivis.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import aivis.common.FileManager;
import aivis.machinelearning.*;
import aivis.database.DatabaseInfo;
import aivis.common.*;

public class UserProcess implements Runnable {
    private Socket socket;
    private ServerMain serverMain;
    private FileManager fileManager;
    private int id;

    private DatabaseInfo databaseInfo;
    
    private user_info user;
    private Self_Intro_Doc introDoc;
    private Interview interview;
    private Evaluation evaluation;

    public UserProcess(ServerMain serverMain, Socket socket, DatabaseInfo databaseInfo) throws Exception
    {
        this.serverMain = serverMain;
        this.socket = socket;
        fileManager = new FileManager();

        this.databaseInfo = new DatabaseInfo(databaseInfo.hostname, databaseInfo.port, databaseInfo.dbName, databaseInfo.userName, databaseInfo.password);

        user = new user_info(this.databaseInfo);
        introDoc = new Self_Intro_Doc(this.databaseInfo);
        interview = new Interview(this.databaseInfo);
        evaluation = new Evaluation(this.databaseInfo);
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

            System.out.println("Wait Login");
            String firstMsg = dis.readUTF();
            if(firstMsg.compareTo("login") == 0){
                String uID = dis.readUTF();
                String Password = dis.readUTF();

                user.DBRead(uID);

                if(user.uID == null){
                    dos.writeUTF("fail");
                    dos.flush();
                }
                else{
                    dos.writeUTF("valid");
                    dos.flush();
                }
            }

            while(user.uID != null)
            {
                System.out.println("Wait Client's Request");
                String msg = dis.readUTF();

                System.out.println("Recieve Request: " + msg);

                if(msg.compareTo("login") == 0){
                    String uID = dis.readUTF();
                    String Password = dis.readUTF();

                    user.DBRead(uID);

                    if(user.uID == null){
                        dos.writeUTF("fail");
                        dos.flush();
                        break;
                    }
                    else{
                        dos.writeUTF("valid");
                        dos.flush();
                    }
                }

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
                    fileManager.send(dos, "../data", "eye.txt");
                    fileManager.send(dos, "../data", "emotion.txt");
                    System.out.println("End Video Analysis");
                }
                else if(msg.compareTo("upload") == 0){
                    System.out.println("Recieve Self Introduction Document Data");
                    fileManager.recieve(dis, "../data", "data.txt");
                    introDoc.uID = user.uID;
                    introDoc.Document = "../data/data.txt".
                    introDoc.DBInsert();
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
