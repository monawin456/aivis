package aivis.common;

public class User {
    public String uID;
    public String Password;
    public String uName;
    public String uEmail;

    public User() {
        uID = null;
        Password = null;
        uName = null;
        uEmail = null;
    }

    public User(String uID, String Password, String uName, String uEmail) {
        this.uID = uID;
        this.Password = Password;
        this.uName = uName;
        this.uEmail = uEmail;
    }
}
