package cjmazur.homework.cs383.chirp.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cjmazur.homework.cs383.chirp.R;

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
                //TODO verify everything is valid, then create the user on the server
            }
        });

    }

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, RegistrationActivity.class);
        return intent;
    }
}
