package cjmazur.homework.cs383.chirp.models;

import java.time.ZonedDateTime;

/**
 * Created by CJ on 3/28/2018.
 */

class Chirp {
    private String message;
    private ZonedDateTime date;

    public Chirp(String message, ZonedDateTime date) {
        this.message = message;
        this.date = date;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public ZonedDateTime getDate() {
        return date;
    }
}
