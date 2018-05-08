package cjmazur.homework.cs383.chirp.activities;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cjmazur.homework.cs383.chirp.models.Chirp;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import cjmazur.homework.cs383.chirp.R;
import cjmazur.homework.cs383.chirp.models.User;
import cjmazur.homework.cs383.chirp.volley.ImportantURLs;
import cjmazur.homework.cs383.chirp.volley.RequestQueueSingleton;
import cjmazur.homework.cs383.chirp.volley.SharedPrefManager;
import cjmazur.homework.cs383.chirp.volley.VolleySingleton;

public class RecentChirpsActivity extends AppCompatActivity {

    private Button createChirpsButton;
    private Button editWatchlistButton;
    private RecyclerView chirpsView;
    private ArrayList<Chirp> chirps;
    private ChirpAdapter chirpsAdapter;
    private Button logoutButton;

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, RecentChirpsActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_chirps);
        createChirpsButton = findViewById(R.id.create_chirp_button);
        createChirpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = CreateChirpActivity.newIntent(view.getContext());
                startActivity(intent);
            }
        });
        editWatchlistButton = findViewById(R.id.edit_watchlist_button);
        editWatchlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = EditWatchlistActivity.newIntent(view.getContext());
                startActivity(intent);*/
                Toast.makeText(getApplicationContext(), "We're sorry, but that activity has currently been disabled.", Toast.LENGTH_LONG).show();
            }
        });
        logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser();
            }
        });
        chirpsView = findViewById(R.id.chirps_recycler_view);
        chirps = new ArrayList<>();

        chirpsView.setLayoutManager(new LinearLayoutManager(this));
        updateChirpsList();
    }

    private void logoutUser() {
        SharedPrefManager.getInstance(getApplicationContext()).logout();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    private void updateChirpsList() {
        StringRequest sr = new StringRequest(StringRequest.Method.GET,
                ImportantURLs.BASE_URL + "chirps",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ServerCommunication", response);
                        ObjectMapper mapper = new ObjectMapper();

                        try {
                            chirps.clear();

                            JSONArray objects = new JSONArray(response);

                            int numObjects = objects.length();
                            for (int i = 0; i < numObjects; i++) {
                                chirps.add(mapper.readValue(objects.getString(i), Chirp.class));
                            }
                            updateUserNames();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "We're sorry, but it seems like there's an error getting to the Chirp server", Toast.LENGTH_LONG).show();
                    }
        });
        RequestQueueSingleton.getInstance(getApplicationContext()).addToRequestQueue(sr);
    }

    public void updateUserNames() {
        StringRequest sr;
        for (final Chirp c : chirps) {
            sr = new StringRequest(Request.Method.GET,
                    ImportantURLs.BASE_URL + "users/fi/" + c.getUserId(),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("ServerCommunication", response);
                            ObjectMapper mapper = new ObjectMapper();

                            try {
                                User user = mapper.readValue(response, User.class);
                                c.setUserId(user.getHandle());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            chirpsAdapter = new ChirpAdapter(chirps);
                            chirpsView.setAdapter(chirpsAdapter);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "There seems to be an error communicating with the server", Toast.LENGTH_LONG).show();
                        }
                    }
            );
            RequestQueueSingleton.getInstance(getApplicationContext()).addToRequestQueue(sr);
        }
    }

    private class chirpHolder extends RecyclerView.ViewHolder {
        private TextView author;
        private TextView date;
        private TextView content;
        private ImageView picture;

        private Chirp chirp;

        public chirpHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.chirp_list_item, parent, false));
            author = itemView.findViewById(R.id.chirp_author);
            date = itemView.findViewById(R.id.chirp_date_created);
            content = itemView.findViewById(R.id.chirp_content);
            picture = itemView.findViewById(R.id.chirp_image);
        }

        public void bind(Chirp inputChirp) {
            chirp = inputChirp;
            author.setText(chirp.getUserId());
            date.setText(chirp.getDate().toString());
            if (chirp.getMessage() != null) {
                content.setText(chirp.getMessage());
            }
            else {
                content.setVisibility(View.GONE);
            }
            picture.setVisibility(View.GONE);
        }
    }

    private class ChirpAdapter extends RecyclerView.Adapter<chirpHolder> {
        private List<Chirp> chirps;

        public ChirpAdapter(List<Chirp> listOfChirps) {
            chirps = listOfChirps;
        }

        @NonNull
        @Override
        public chirpHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new chirpHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull chirpHolder holder, int position) {
            holder.bind(chirps.get(position));
        }

        @Override
        public int getItemCount() {
            return chirps.size();
        }
    }
}
