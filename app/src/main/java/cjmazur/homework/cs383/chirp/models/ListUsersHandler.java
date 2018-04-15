package cjmazur.homework.cs383.chirp.models;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by CJ on 3/28/2018.
 *
 * This is an example of a handler for dealing with requests
 * In the activity or app itself, create an instance of the handler, and pass it to
 * the request manager. Once the request manager returns, the handler will have the
 * data provided by the HTTPResponse;
 */

class ListUsersHandler {
    private ArrayList<User> mUserList;
    private ListUsersHandler mInstance;

    private ListUsersHandler() {
        mUserList = new ArrayList<>();
    }

    public synchronized ListUsersHandler getInstance() {
        if (mInstance == null)
            mInstance = new ListUsersHandler();
        return mInstance;
    }

    public ArrayList<User> getUserList() {
        if (mUserList == null) {
            mUserList = new ArrayList<>();
        }
        return mUserList;
    }

    public void setUsersList(User[] users) {
        mUserList = new ArrayList<User>(Arrays.asList(users));
    }
}
