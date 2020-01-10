package aivis_server.machinelearning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Chatbot {
    private Runtime runtime;
    private String[] trainCommand;
    private String[] excuteCommand;

    public Chatbot(){
        runtime = Runtime.getRuntime();
        //trainCommand = new String[]{"python", ".\\chatbotTrain.py"}; // for windows
        trainCommand = new String[]{"python3", "./chatbotTrain.py"}; // for linux
        //excuteCommand = new String[]{"python", ".\\chatbotExcute.py"}; // for windows
        excuteCommand = new String[]{"python3", "./chatbotExcute.py"}; // for linux
    }

    public void trainChatbot(){
        try {
            System.out.println("Start Train");
            Process process = null;
            process = runtime.exec(trainCommand);

            // print python result
            String s = null;
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
            stdInput.close();
            stdError.close();

            process.waitFor();
            process.destroy();
            System.out.println("End Train");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void excuteChatbot(){
        try {
            System.out.println("Start Keyword Matching");
            Process process = null;
            process = runtime.exec(excuteCommand);

            // print python result
            String s = null;
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
            stdInput.close();
            stdError.close();
            
            process.waitFor();
            process.destroy();
            System.out.println("End Keyword Matching");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
