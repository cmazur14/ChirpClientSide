package cjmazur.homework.cs383.chirp.activities;

import android.content.Intent;
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
                if (verifyUser(emailField.getText().toString(), passwordField.getText().toString())) {
                    Intent intent = RecentChirpsActivity.newIntent(view.getContext());
                    startActivity(intent);
                    //TODO set it up so it gets and starts the intent for the logged-in user to go to the correct page
                } else {
                    Toast.makeText(LoginActivity.this, "We're sorry, but that login information doesn't seem to be in our system. Please try a different email and password, or register as a new user!", Toast.LENGTH_LONG).show();
                    emailField.setText("");
                    passwordField.setText("");
                }
            }
        });

        //if there is a user login already saved, this will load it into the text fields
        loadUserLogin();
        if (login != null) {
            emailField.setText(login.getEmail());
            passwordField.setText(login.getUserPassword());
        }

    }

    private boolean verifyUser(String userEmail, String userPassword) {
        //TODO verify the user with the server
        return true;
    }

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

    public void deleteSavedUserLoginInformation() {
        login = null;
        saveUserLogin();
    }



}
