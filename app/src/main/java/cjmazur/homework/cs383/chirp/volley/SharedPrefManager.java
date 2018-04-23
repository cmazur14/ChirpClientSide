package cjmazur.homework.cs383.chirp.volley;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import cjmazur.homework.cs383.chirp.activities.LoginActivity;
import cjmazur.homework.cs383.chirp.models.User;

/**
 * Created by CJ on 4/19/2018.
 */

public class SharedPrefManager {

    private static final String SHARED_PREFERENCES_NAME = "chirp_shared_preferences";
    private static final String EMAIL_KEY = "emailkey";
    private static final String PASSWORD_KEY = "passwordkey";
    private static final String ID_KEY = "idkey";
    private static final String HANDLE_KEY = "handlekey";

    private static SharedPrefManager mInstance;
    private static Context mContext;

    //the following two methods are the singleton pattern for hte class
    private SharedPrefManager(Context context) {
        mContext = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null)
            mInstance = new SharedPrefManager(context);
        return mInstance;
    }

    //method that stores the logged-in user's data in the device's SharedPreferences
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAIL_KEY, user.getEmail());
        editor.putString(PASSWORD_KEY, user.getPassword());
        editor.putString(HANDLE_KEY, user.getHandle());
        editor.putLong(ID_KEY, user.getId());
        editor.apply();
    }

    //method that checks whether a user is logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sp = mContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sp.getString(EMAIL_KEY, null) != null;
    }

    //method that returns the logged-in user from the device's SharedPreferences
    public User getUser() {
        SharedPreferences sp = mContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return new User( //email, pw, handle, id
                sp.getString(EMAIL_KEY, null),
                sp.getString(PASSWORD_KEY, null),
                sp.getString(HANDLE_KEY, null),
                sp.getLong(ID_KEY, -1)
        );
    }

    //method that logs out the user and removes it from the device's SharedPreferences
    public void logout() {
        SharedPreferences sp = mContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
        mContext.startActivity(new Intent(mContext, LoginActivity.class));
    }


}
