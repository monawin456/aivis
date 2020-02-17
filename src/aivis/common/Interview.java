package aivis.common;

import java.sql.*;
import java.io.File;

import aivis.database.DatabaseInfo;

public class Interview {
    public String uID;
    public String DocID;
    public String Interview_Number;
    public File Video;
    public DatabaseInfo databaseInfo;

    public Interview() {
        uID = null;
        DocID = null;
        Interview_Number = null;
        Video = null;

        databaseInfo = null;
    }

    public Interview(String uID, String DocID, String Interview_Number, String filePath) {
        try{
            this.uID = uID;
            this.DocID = DocID;
            this.Interview_Number = Interview_Number;
            this.Video = new File(filePath);

            databaseInfo = null;
        } catch(NullPointerException e) {
            e.printStackTrace();
            this.uID = null;
            this.DocID = null;
            this.Interview_Number = null;
            this.Video = null;
    
            databaseInfo = null;
        }
    }

    public Interview(String uID, String DocID, String Interview_Number, String filePath, DatabaseInfo databaseInfo) {
        try{
            this.uID = uID;
            this.DocID = DocID;
            this.Interview_Number = Interview_Number;
            this.Video = new File(filePath);

            this.databaseInfo = databaseInfo;
        } catch(NullPointerException e) {
            e.printStackTrace();
            this.uID = null;
            this.DocID = null;
            this.Interview_Number = null;
            this.Video = null;
    
            this.databaseInfo = null;
        }
    }
}
