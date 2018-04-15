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
    private ArrayList<UUID> chirpList;
    private ArrayList<UUID> userPreferences; //TODO set this up

    public User(String name, String email, String password, String handle, UUID id, ArrayList<UUID> chirpList, ArrayList<UUID> userPreferences) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.handle = handle;
        this.id = id;
        this.chirpList = chirpList;
        this.userPreferences = userPreferences;
    }

    public User() {
        name = "Fred Flintstone";
        email = "john.doe@gmail.com";
        password = "pw";
        handle = "FreddyBoi";
        id = new UUID(0, 0);
        chirpList = new ArrayList<>();
        userPreferences = new ArrayList<>();
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

    public ArrayList<UUID> getUserPreferences() {
        return userPreferences;
    }

    public void setUserPreferences(ArrayList<UUID> userPreferences) {
        this.userPreferences = userPreferences;
    }

    public void setChirpList(ArrayList<UUID> chirpList) {
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

    public ArrayList<UUID> getChirpList() {
        return chirpList;
    }

    public void addChirp(UUID chirpID) {
        chirpList.add(chirpID);
    }
}
