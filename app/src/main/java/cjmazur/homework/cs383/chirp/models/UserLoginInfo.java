package cjmazur.homework.cs383.chirp.models;

import java.io.Serializable;

/**
 * Created by CJ on 3/27/2018.
 */

public class UserLoginInfo implements Serializable{

    private String userEmail;
    private String userPassword;

    public UserLoginInfo(String ue, String pw) {
        userEmail = ue;
        userPassword = pw;
    }

    public UserLoginInfo() {
        userEmail = "john@westminster.edu";
        userPassword = "password";
    }

    public String getEmail() {
        return userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserEmail(String email) {
        userEmail = email;
    }

    public void setUserPassword(String pw) {
        userPassword = pw;
    }



}
