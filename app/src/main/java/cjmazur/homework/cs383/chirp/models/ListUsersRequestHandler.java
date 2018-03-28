package cjmazur.homework.cs383.chirp.models;

import java.util.ArrayList;

/**
 * Created by CJ on 3/28/2018.
 *
 * This is an example of a handler for dealing with requests
 * In the activity or app itself, create an instance of the handler, and pass it to
 * the request manager. Once the request manager returns, the handler will have the
 * data provided by the HTTPResponse;
 */

class ListUsersRequestHandler {
    private ArrayList<User> userList;

    public ArrayList<User> getUserList() {
        if (userList == null) {
            userList = new ArrayList<>();
        }
        return userList;
    }
}
