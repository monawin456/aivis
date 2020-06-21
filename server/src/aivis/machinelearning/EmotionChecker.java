package aivis.machinelearning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EmotionChecker {
    private Runtime runtime;
    private String[] trainCommand;
    //private String[] excuteCommand;
    private String[] excuteCommand1;
    private String[] excuteCommand2;
    private String[] excuteCommand3;
    private String[] excuteCommand4;
    private String[] excuteCommand5;

    public EmotionChecker(){
        runtime = Runtime.getRuntime();
        //excuteCommand = new String[]{"python", ".\\emotion_check.py"}; // for windows
        excuteCommand1 = new String[]{"python", ".\\emotion_check.py", "1"}; // for windows
        excuteCommand2 = new String[]{"python", ".\\emotion_check.py", "2"}; // for windows
        excuteCommand3 = new String[]{"python", ".\\emotion_check.py", "3"}; // for windows
        excuteCommand4 = new String[]{"python", ".\\emotion_check.py", "4"}; // for windows
        excuteCommand5 = new String[]{"python", ".\\emotion_check.py", "5"}; // for windows
        //excuteCommand = new String[]{"python3", "./emotion_check.py"}; // for linux
    }

    // public void excute(){
    //     try {
    //         Process process = null;
    //         process = runtime.exec(excuteCommand);

    //         // print python result
    //         String s = null;
    //         BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
    //         BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
    //         while ((s = stdInput.readLine()) != null) {
    //             System.out.println(s);
    //         }
    //         while ((s = stdError.readLine()) != null) {
    //             System.out.println(s);
    //         }
    //         stdInput.close();
    //         stdError.close();
            
    //         process.waitFor();
    //         process.destroy();
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     } catch (InterruptedException e) {
    //         e.printStackTrace();
    //     }
    // }

    public void excute(){
        try {
            Process process = null;
            process = runtime.exec(excuteCommand1);

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

        try {
            Process process = null;
            process = runtime.exec(excuteCommand2);

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

        try {
            Process process = null;
            process = runtime.exec(excuteCommand3);

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

        try {
            Process process = null;
            process = runtime.exec(excuteCommand4);

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

        try {
            Process process = null;
            process = runtime.exec(excuteCommand5);

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
