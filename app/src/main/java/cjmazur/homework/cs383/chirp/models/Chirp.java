package cjmazur.homework.cs383.chirp.models;

import android.support.annotation.Nullable;

import java.util.UUID;

/**
 * Created by CJ on 3/28/2018.
 */

public class Chirp {
    private String message;
    private String date;
    private String userId;
    private String id;


    public Chirp(@Nullable String message, String date, String userId) {
        this.message = message;
        this.date = date;
        this.userId = userId;
        id = UUID.randomUUID().toString();
    }

    public Chirp() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




}
