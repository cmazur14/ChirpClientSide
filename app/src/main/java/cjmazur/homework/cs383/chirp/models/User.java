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
    private ArrayList<Long> watchlist;

    public User(String email, String password, String handle, long id) {
        this.email = email;
        this.password = password;
        this.handle = handle;
        this.id = id;
        watchlist = new ArrayList<>();
    }

    public User() {
        this("fred@bedrock.com", "password", "FreddyBoi", UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
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

    public void addToWatchList(long newUserId) {
        watchlist.add(newUserId);
    }

    public ArrayList<Long> getWatchlist() {
        ArrayList<Long> listCopy = new ArrayList<>();
        for (long id : watchlist)
            listCopy.add(id);
        return listCopy;
    }


}
