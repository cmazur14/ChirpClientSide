package cjmazur.homework.cs383.chirp.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

        final String regex = "^[a-zA-Z0-9]+$";

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

        StringRequest sr = new StringRequest(ImportantURLs.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();

                                //interpret the user from the response
                                JSONObject userJson = obj.getJSONObject("user");

                                User user = new User(
                                        //email, pw, handle, id
                                        userJson.getString("email"),
                                        userJson.getString("password"),
                                        userJson.getString("handle"),
                                        userJson.getLong("id")
                                );

                                //store the logged-in user in sharedPreferences
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                //start the recentChirpsActivity
                                finish();
                                startActivity(new Intent(getApplicationContext(), RecentChirpsActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
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
                params.put("email", email);
                params.put("password", pw);
                params.put("handle", handle);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(sr);
    }
    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, RegistrationActivity.class);
        return intent;
    }
}
