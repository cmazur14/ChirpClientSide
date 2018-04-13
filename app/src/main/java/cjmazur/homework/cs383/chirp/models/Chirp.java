package cjmazur.homework.cs383.chirp.models;

import android.media.Image;
import android.support.annotation.Nullable;

import java.time.ZonedDateTime;

/**
 * Created by CJ on 3/28/2018.
 */

public class Chirp {
    private String message;
    private String date;
    private String author;
    private Image image;

    public Chirp(@Nullable String message, String date, String auth,@Nullable Image img) {
        this.message = message;
        this.date = date;
        author = auth;
        image = img;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public String getAuthor() {
        return author;
    }

    public Image getImage() {
        return image;
    }
}
