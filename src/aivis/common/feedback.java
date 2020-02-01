package aivis.common;

import aivis.database.DatabaseInfo;

public class feedback {
    public String Interview_Number;
    public int rank;
    public int face;
    public int eye;
    public int voice_speed;
    public int voice_tone;
    public DatabaseInfo databaseInfo;

    public feedback() {
        Interview_Number = null;
        rank = 0;
        face = 0;
        eye = 0;
        voice_speed = 0;
        voice_tone = 0;

        databaseInfo = null;
    }

    public feedback(String Interview_Number, int rank, int face, int eye, int voice_speed, int voice_tone) {
        this.Interview_Number = Interview_Number;
        this.rank = rank;
        this.face = face;
        this.eye = eye;
        this.voice_speed = voice_speed;
        this.voice_tone = voice_tone;

        databaseInfo = null;
    }

    public feedback(String Interview_Number, int rank, int face, int eye, int voice_speed, int voice_tone, DatabaseInfo databaseInfo) {
        this.Interview_Number = Interview_Number;
        this.rank = rank;
        this.face = face;
        this.eye = eye;
        this.voice_speed = voice_speed;
        this.voice_tone = voice_tone;

        this.databaseInfo = databaseInfo;
    }
}
