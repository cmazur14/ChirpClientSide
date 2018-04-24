package cjmazur.homework.cs383.chirp.models;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by CJ on 3/28/2018.
 */

public class User {
    private String email;
    private String password;
    private String handle;
    private long id;

    public User(String email, String password, String handle, long id) {
        this.email = email;
        this.password = password;
        this.handle = handle;
        this.id = id;
    }

    public User() {
        email = "john.doe@gmail.com";
        password = "pw";
        handle = "FreddyBoi";
        id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
