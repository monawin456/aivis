package aivis_client_tmp;

import java.net.Socket;
import java.util.Scanner;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import aivis_common.FileManager;

public class ClientMain {
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
            // print data.txt
            System.out.println("Self Introduction Content:");
            fileManager.printText("./data", "data.txt");

            // wait time
            System.out.println("If you want continue press any key");
            scanner.nextLine();

            // upload data.txt
            System.out.println("Try to Connect Server...");
			socket = new Socket(serverAddress, port);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            System.out.println("Connect success");

            // wait time
            System.out.println("If you want continue press any key");
            scanner.nextLine();

			System.out.println("Send Request: upload");
			dos.writeUTF("upload");
            dos.flush();
            System.out.println("Send Self Introduction Document Data");
            fileManager.send(dos, "./data", "data.txt");

            // wait time
            System.out.println("If you want continue press any key");
            scanner.nextLine();

            // recieve question
            System.out.println("Send Request: question");
            dos.writeUTF("question");
            dos.flush();
            isAvailable = dis.readUTF();
            System.out.println("Receive Server State: " + isAvailable);
            if(isAvailable.compareTo("f") == 0) {
				System.out.println("sorry, server is working for learning");
			}
			else if(isAvailable.compareTo("n") == 0) {
				System.out.println("sorry, file is not found");
            }
            else if(isAvailable.compareTo("t") == 0) {
                System.out.println("Receive Question Data");
                fileManager.recieve(dis, "./data", "questions.txt");

                // print questions.txt
                System.out.println("Questions Content:");
                fileManager.printText("./data", "questions.txt");
            }
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
