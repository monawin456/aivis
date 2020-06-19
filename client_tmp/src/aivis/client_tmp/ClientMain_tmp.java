package aivis.client_tmp;

import java.net.Socket;
import java.util.Scanner;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import aivis.common.FileManager;

public class ClientMain_tmp {
	public static void main(String[] args) {
		String serverAddress;
        serverAddress = "127.0.0.1";
		int port;
        port = 8888;
        Socket socket = null;
		DataInputStream dis = null;
        DataOutputStream dos = null;
        String isAvailable = null;
        FileManager fileManager = new FileManager();
        Scanner scanner = new Scanner(System.in);

        try
        {
            // wait time
            System.out.println("If you want continue press any key");
            scanner.nextLine();

            // connect
            System.out.println("Try to Connect Server");
			socket = new Socket(serverAddress, port);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            System.out.println("Connect success");

            // wait time
            System.out.println("If you want continue press any key");
            scanner.nextLine();

            // login
            System.out.println("Try to login");
            dos.writeUTF("login");
            dos.writeUTF("admin");
            dos.writeUTF("1234");
            dos.flush();
            String pass;
            pass = dis.readUTF();
            if(pass.compareTo("valid") == 0){
                System.out.println("Valid");
            }
            else if(pass.compareTo("fail") == 0){
                System.out.println("Fail");
            }

            // wait time
            System.out.println("If you want continue press any key");
            scanner.nextLine();

            // upload
			System.out.println("Send Request: upload");
			dos.writeUTF("upload");
            dos.flush();
            System.out.println("Send Self Introduction Document Data");
            fileManager.send(dos, "../data", "data.txt");

            // wait time
            System.out.println("If you want continue press any key");
            scanner.nextLine();

            // question
			System.out.println("Send Request: question");
			dos.writeUTF("question");
            dos.flush();
            System.out.println("Recieve Question");
            fileManager.recieve(dis, "../data", "questions.txt");

            // wait time
            System.out.println("If you want continue press any key");
            scanner.nextLine();

            // video
			System.out.println("Send Request: video");
			dos.writeUTF("video");
            dos.flush();
            System.out.println("Recieve Evaluation");
            fileManager.recieve(dis, "../data", "eye.txt");
            fileManager.recieve(dis, "../data", "emotion.txt");
        }catch(Exception e) {
            System.out.println("socket error occure");
            e.printStackTrace();
        } finally {
            try { dos.close(); } catch (IOException e) { e.printStackTrace(); }
            try { dis.close(); } catch (IOException e) { e.printStackTrace(); }
            try { socket.close(); } catch (IOException e) { e.printStackTrace(); }
        }
        scanner.close();
    }
}
