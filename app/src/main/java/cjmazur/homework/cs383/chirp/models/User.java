package cjmazur.homework.cs383.chirp.models;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by CJ on 3/28/2018.
 */

class User {
    private String name;
    private String email;
    private String password;
    private String handle;
    private UUID id;
    private ArrayList<Chirp> chirpList;

    public User(String name, String email, String password, String handle, UUID id, ArrayList<Chirp> chirpList) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.handle = handle;
        this.id = id;
        this.chirpList = chirpList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setChirpList(ArrayList<Chirp> chirpList) {
        this.chirpList = chirpList;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getHandle() {
        return handle;
    }

    public UUID getId() {
        return id;
    }

    public ArrayList<Chirp> getChirpList() {
        return chirpList;
    }

    public void addChirp(Chirp chirp) {
        chirpList.add(chirp);
    }
}
