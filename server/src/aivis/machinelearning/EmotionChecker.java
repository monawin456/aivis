package aivis.machinelearning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EmotionChecker {
    private Runtime runtime;
    private String[] trainCommand;
    private String[] excuteCommand;

    public EmotionChecker(){
        runtime = Runtime.getRuntime();
        excuteCommand = new String[]{"python", ".\\emotion_check.py"}; // for windows
        //excuteCommand = new String[]{"python3", "./emotion_check.py"}; // for linux
    }

    public void excute(){
        try {
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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
