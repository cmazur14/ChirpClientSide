package cjmazur.homework.cs383.chirp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cjmazur.homework.cs383.chirp.R;
import cjmazur.homework.cs383.chirp.models.ActiveUserHandler;
import cjmazur.homework.cs383.chirp.models.RequestManager;
import cjmazur.homework.cs383.chirp.models.User;
import cjmazur.homework.cs383.chirp.volley.ImportantURLs;
import cjmazur.homework.cs383.chirp.volley.SharedPrefManager;
import cjmazur.homework.cs383.chirp.volley.VolleySingleton;

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

    private static final String FILE_STORAGE_NAME = "login_info.serializedObject";

    private EditText emailField;
    private EditText passwordField;
    private TextView registerTextView;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //Assigns the login page's widgets to instance variables
        emailField = findViewById(R.id.login_email);
        passwordField = findViewById(R.id.login_password);
        registerTextView = findViewById(R.id.login_register);
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = RegistrationActivity.newIntent(view.getContext());
                startActivity(intent);
            }
        });
        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyUser();
            }
        });

        //if there is a user login already saved, this will load it into the text fields
        //and then verify that the saved login information is correct. If it is, then it will log
        //in the user
        loadUserLogin();
    }

    private void verifyUser() {
        StringRequest sr = new StringRequest(StringRequest.Method.GET,
                ImportantURLs.BASE_URL + "users/fe/" + emailField.getText().toString().trim(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ServerCommunication", response);
                        try {
                            if (response != null) {
                                JSONObject obj = new JSONObject(response);

                                User user = new User(
                                        //email, pw, handle, id
                                        obj.getString("email"),
                                        obj.getString("password"),
                                        obj.getString("handle"),
                                        obj.getLong("id")
                                );

                                if (user.getPassword().equals(passwordField.getText().toString().trim())) {
                                    //store the logged-in user in sharedPreferences
                                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                    //start the recentChirpsActivity
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), RecentChirpsActivity.class));
                                } else {
                                    passwordField.setError("Incorrect Password!");
                                    passwordField.requestFocus();
                                }
                            } else {
                                emailField.setError("Invalid Email!");
                                emailField.requestFocus();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", emailField.getText().toString().trim());
                return params;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(sr);
    }

    private void loadUserLogin() {
        User storedUser = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        if (storedUser.getEmail() == null)
            return;
        if (storedUser.getPassword() == null)
            return;
        emailField.setText(storedUser.getEmail());
        passwordField.setText(storedUser.getPassword());
        verifyUser();
    }

    public void logoutUser() {
        SharedPrefManager.getInstance(getApplicationContext()).logout();
        emailField.setText("");
        passwordField.setText("");
    }



}
