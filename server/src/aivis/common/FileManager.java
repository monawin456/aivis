package aivis.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileManager{
    public String recieve(DataInputStream dis, String filePath, String fileName){
        String result;

        FileOutputStream fos = null;
        BufferedOutputStream bos = null;

        try {
            System.out.println("파일 수신 작업을 시작합니다.");

            // 파일을 생성하고 출력 스트림 생성
            File file = new File(filePath + "/" + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);

            // 파일 수신 시작
            int len = 0;
            int size = 4096;
            byte[] data = new byte[size];
            while (true) {
                len = dis.readInt();
                System.out.println("number of bytes: " + len);
                if(len == -1){
                    break;
                }
                else{
                    dis.read(data, 0, len);
                    bos.write(data, 0, len);
                }
            }
            bos.flush();

            result = "SUCCESS";
        
            System.out.println("파일 수신 작업을 완료하였습니다.");
        } catch (IOException e) {
            e.printStackTrace();
            result = "ERROR";
        } finally{
            try { bos.close(); } catch (IOException e) { e.printStackTrace(); }
            try { fos.close(); } catch (IOException e) { e.printStackTrace(); }
        }
    
        return result;
    }

    public String send(DataOutputStream dos, String filePath, String fileName){
        String result;

        FileInputStream fis = null;
        BufferedInputStream bis = null;
     
        try {
            System.out.println("파일 전송 작업을 시작합니다.");
 
            // 파일을 읽고 입력 스트림 생성
            File file = new File(filePath + "/" + fileName);
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);

            // 파일 전송 시작
            int len = 0;
            int size = 4096;
            byte[] data = new byte[size];
            while (true) {
                len = bis.read(data);
                System.out.println("number of bytes: " + len);
                if(len == -1){
                    dos.writeInt(len);
                    break;
                }else{
                    dos.writeInt(len);
                    dos.write(data, 0, len);
                }
            }
            dos.flush();
           
            result = "SUCCESS";

            System.out.println("파일 전송 작업을 완료하였습니다.");
        } catch (IOException e) {
            e.printStackTrace();
            result = "ERROR";
        } finally{
            try { bis.close(); } catch (IOException e) { e.printStackTrace(); }
            try { fis.close(); } catch (IOException e) { e.printStackTrace(); }
        }
       
        return result;
    }

    public void printText(String filePath, String fileName){
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader bufReader = null;
        try {
            File file = new File(filePath + "/" + fileName);
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis, "UTF-8");
            bufReader = new BufferedReader(isr);

            String line = null;
            while((line = bufReader.readLine()) != null){
                System.out.println(line);
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        } finally{
            try { bufReader.close(); } catch (IOException e) { e.printStackTrace(); }
            try { isr.close(); } catch (IOException e) { e.printStackTrace(); }
            try { fis.close(); } catch (IOException e) { e.printStackTrace(); }
        }
    }
}
