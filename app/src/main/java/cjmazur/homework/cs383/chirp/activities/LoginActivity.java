package cjmazur.homework.cs383.chirp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import cjmazur.homework.cs383.chirp.R;
import cjmazur.homework.cs383.chirp.models.UserLoginInfo;

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

    private UserLoginInfo login;

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
        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO add an if-statement that verifies things with the server
            }
        });

        //if there is a user login already saved, this will load it into the text fields
        loadUserLogin();
        if (login != null) {
            emailField.setText(login.getEmail());
            passwordField.setText(login.getUserPassword());
        }

        /* uncomment this and then run it to delete any saved login information
        login = null;
        saveUserLogin();
        */
    }

//    private void sendListOfUsersRequest() {
//        RequestQueue queue = Volley.newRequestQueue(this);
//        String url = "http://10.0.2.2:5010/users";
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response, new Response.ErrorListener());
//        queue.add(stringRequest);
//    }

    private void saveUserLogin() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(getFilesDir(), FILE_STORAGE_NAME)));
            oos.writeObject(login);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Login information could not be saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Login information could not be saved", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadUserLogin() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(getFilesDir(), FILE_STORAGE_NAME)));
            login = (UserLoginInfo) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Login information could not be loaded", Toast.LENGTH_SHORT).show();
            login = null;
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Login information could not be loaded", Toast.LENGTH_SHORT).show();
            login = null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Login information could not be loaded", Toast.LENGTH_SHORT).show();
            login = null;
        }
    }



}
