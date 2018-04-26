package cjmazur.homework.cs383.chirp.models;

/**
 * Created by CJ on 4/25/2018.
 */

public class ChirpTransport {

    private String message;
    private String date;
    private String userId;

    public ChirpTransport(String message, String date, String userId) {
        this.message = message;
        this.date = date;
        this.userId = userId;
    }

    public String getMessage() {return message;}
    public String getDate() {return date;}
    public String getUserId() {return userId;}

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
