package aivis_server.machinelearning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Textrank {
    private Runtime runtime;
    private String[] command;

    public Textrank(){
        runtime = Runtime.getRuntime();
        //command = new String[]{"python", ".\\textrank.py"}; // for windows
        command = new String[]{"python3", "./textrank.py"}; // for linux
    }

    public void createTextrank(){
        try {
            System.out.println("Start Textrank");
            Process process = null;
            process = runtime.exec(command);

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
            System.out.println("End Textrank");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
