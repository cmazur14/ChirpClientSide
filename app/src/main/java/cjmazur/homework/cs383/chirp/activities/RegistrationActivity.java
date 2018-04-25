package cjmazur.homework.cs383.chirp.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.UUID;

import cjmazur.homework.cs383.chirp.R;
import cjmazur.homework.cs383.chirp.models.User;
import cjmazur.homework.cs383.chirp.volley.ImportantURLs;
import cjmazur.homework.cs383.chirp.volley.SharedPrefManager;
import cjmazur.homework.cs383.chirp.volley.VolleySingleton;

public class RegistrationActivity extends AppCompatActivity {

    EditText emailBox;
    EditText passwordBox;
    EditText passwordVerificationBox;
    EditText handleBox;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        emailBox = findViewById(R.id.registration_email);
        passwordBox = findViewById(R.id.registration_password);
        passwordVerificationBox = findViewById(R.id.registration_verify_password);
        handleBox = findViewById(R.id.registration_handle);
        registerButton = findViewById(R.id.registration_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        final String email = emailBox.getText().toString().trim();
        final String pw = passwordBox.getText().toString().trim();
        final String pwVerify = passwordVerificationBox.getText().toString().trim();
        final String handle = handleBox.getText().toString().trim();
        final long id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        final String regex = "^[a-zA-Z0-9]+$";

        final JSONObject body = new JSONObject();
        try {
            body.put("email", email);
            body.put("handle", handle);
            body.put("password", pw);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //first, we double check that the user didn't mess up data entry

        if (TextUtils.isEmpty(email)) {
            emailBox.setError("Please enter an email");
            emailBox.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(pw)) {
            passwordBox.setError("Please enter a password");
            passwordBox.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(pwVerify)) {
            passwordVerificationBox.setError("Please re-enter your password");
            passwordVerificationBox.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(handle)) {
            handleBox.setError("Please enter a handle");
            handleBox.requestFocus();
            return;
        }

        if (!pw.equals(pwVerify)) {
            passwordVerificationBox.setError("Passwords do not match!");
            passwordVerificationBox.requestFocus();
            return;
        }

        if (pw.length() > 24) {
            passwordBox.setError("Your password is too long, please choose another");
            passwordBox.requestFocus();
            return;
        }

        if (handle.length() > 12 || !handle.matches(regex)) {
            handleBox.setError("Your handle is invalid (must be <12 characters and alphanumeric)");
            handleBox.requestFocus();
            return;
        }

        StringRequest sr = new StringRequest(StringRequest.Method.POST,
                ImportantURLs.BASE_URL + "users/c",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ServerCommunication", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.getBoolean("user_created")) {
                                Toast.makeText(getApplicationContext(), "User successfully created!", Toast.LENGTH_LONG).show();

                                //"User created"

                                User user = new User(
                                        //email, pw, handle, id
                                        obj.getString("email"),
                                        obj.getString("password"),
                                        obj.getString("handle"),
                                        obj.getLong("id")
                                );

                                //store the logged-in user in sharedPreferences
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                //start the recentChirpsActivity
                                finish();
                                startActivity(new Intent(getApplicationContext(), RecentChirpsActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
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
            public byte[] getBody() throws AuthFailureError {
                return body.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(sr);
    }
    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, RegistrationActivity.class);
        return intent;
    }
}
