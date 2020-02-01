package aivis.common;

import java.io.File;

import aivis.database.DatabaseInfo;

public class SelfIntroDoc {
    public String uID;
    public String DocID;
    public File Document;
    public DatabaseInfo databaseInfo;

    public SelfIntroDoc() {
        uID = null;
        DocID = null;
        Document = null;

        databaseInfo = null;
    }

    public SelfIntroDoc(String uID, String DocID, String filePath) {
        try{
            this.uID = uID;
            this.DocID = DocID;
            this.Document = new File(filePath);

            databaseInfo = null;
        } catch(NullPointerException e) {
            e.printStackTrace();
            this.uID = null;
            this.DocID = null;
            this.Document = null;

            databaseInfo = null;
        }
    }

    public SelfIntroDoc(String uID, String DocID, String filePath, DatabaseInfo databaseInfo) {
        try{
            this.uID = uID;
            this.DocID = DocID;
            this.Document = new File(filePath);

            this.databaseInfo = databaseInfo;
        } catch(NullPointerException e) {
            e.printStackTrace();
            this.uID = null;
            this.DocID = null;
            this.Document = null;

            this.databaseInfo = null;
        }
    }
}
