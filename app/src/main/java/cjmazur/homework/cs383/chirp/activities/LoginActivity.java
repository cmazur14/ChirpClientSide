package cjmazur.homework.cs383.chirp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cjmazur.homework.cs383.chirp.R;

/**
 * @author David Windsor
 * @author Chris Mazur
 * @author Devin Cheplic
 *
 * @since 3/26/18
 *
 * Activity for handling the login and registration of users
 * When a login file is present the activity will auto login the user, otherwise it will prompt a login
 * and if needed guide them through registration
 */

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

//    private void sendListOfUsersRequest() {
//        RequestQueue queue = Volley.newRequestQueue(this);
//        String url = "http://10.0.2.2:5010/users";
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response, new Response.ErrorListener());
//        queue.add(stringRequest);
//    }

}
