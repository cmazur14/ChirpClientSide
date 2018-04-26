package cjmazur.homework.cs383.chirp.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cjmazur.homework.cs383.chirp.R;
import cjmazur.homework.cs383.chirp.models.ChirpTransport;
import cjmazur.homework.cs383.chirp.models.User;
import cjmazur.homework.cs383.chirp.volley.ImportantURLs;
import cjmazur.homework.cs383.chirp.volley.SharedPrefManager;
import cjmazur.homework.cs383.chirp.volley.VolleySingleton;

public class CreateChirpActivity extends AppCompatActivity {

    EditText inputBox;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_chirp);

        inputBox = findViewById(R.id.new_chirp_input_box);
        submitButton = findViewById(R.id.new_chirp_post_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verifyValidChirp())
                    postChirp();
            }
        });
    }

    public boolean verifyValidChirp() {
        return (inputBox.getText().toString().length() < 281);
    }

    public void postChirp() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date now = new Date();
        final String body = new Gson().toJson(new ChirpTransport(
                inputBox.getText().toString(),
                dateFormat.format(now),
                Long.toString(SharedPrefManager.getInstance(getApplicationContext()).getUser().getId())
        ));


        StringRequest sr = new StringRequest(StringRequest.Method.POST,
                ImportantURLs.BASE_URL + "chirps/a",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ServerCommunication", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.getBoolean("chirp_created")) {
                                //tell the user it worked
                                Toast.makeText(getApplicationContext(), "Chirp successfully posted!", Toast.LENGTH_LONG).show();

                                //start the recentChirpsActivity
                                finish();
                                startActivity(new Intent(getApplicationContext(), RecentChirpsActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Hmm, there seems to be an error processing your post", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Hmm, there seems to be an error processing your post", Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            public byte[] getBody() throws AuthFailureError {
                return body.getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(sr);
    }


    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, CreateChirpActivity.class);
        return intent;
    }
}
