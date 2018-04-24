package cjmazur.homework.cs383.chirp.models;

/**
 * Created by CJ on 4/14/2018.
 */

//TODO deprecate this

public class ActiveUserHandler {

    private static ActiveUserHandler mInstance;
    private User mUser;

    private ActiveUserHandler() {
        mUser = getUser();
    }

    public static ActiveUserHandler getInstance() {
        if (mInstance == null) mInstance = new ActiveUserHandler();
        return mInstance;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public User getUser() {
        if (mUser == null) mUser = new User();
        return mUser;
    }

}
