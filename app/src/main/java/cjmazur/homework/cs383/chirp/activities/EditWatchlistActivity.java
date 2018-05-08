package cjmazur.homework.cs383.chirp.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cjmazur.homework.cs383.chirp.R;
import cjmazur.homework.cs383.chirp.models.User;
import cjmazur.homework.cs383.chirp.volley.ImportantURLs;
import cjmazur.homework.cs383.chirp.volley.RequestQueueSingleton;
import cjmazur.homework.cs383.chirp.volley.SharedPrefManager;
import cjmazur.homework.cs383.chirp.volley.VolleySingleton;

import com.fasterxml.jackson.databind.ObjectMapper;


public class EditWatchlistActivity extends AppCompatActivity {

    private Button confirmWatchlistButton;
    private RecyclerView userRecyclerView;
    private ArrayList<User> allUserList;
    private ArrayList<User> selectedUserList;
    private UserSelectorAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_watchlist);

        allUserList = new ArrayList<>();
        selectedUserList = new ArrayList<>();

        userRecyclerView = findViewById(R.id.user_list);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        populateAllUserList();

        confirmWatchlistButton = findViewById(R.id.confirm_watchlist_button);
        confirmWatchlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmWatchlistButtonClicked();
            }
        });

    }

    private void populateAllUserList() {
        StringRequest sr = new StringRequest(StringRequest.Method.GET,
                ImportantURLs.BASE_URL + "users",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ServerCommunication", response);
                        ObjectMapper mapper = new ObjectMapper();

                        try {
                            allUserList.clear();

                            JSONArray objects = new JSONArray(response);

                            int numObjects = objects.length();
                            for (int i = 0; i < numObjects; i++) {
                                //usersAsList.add(mapper.readValue(objs.getString(i), User.class));
                                allUserList.add(mapper.readValue(objects.getString(i), User.class));
                            }

                            userAdapter = new UserSelectorAdapter(allUserList);
                            userRecyclerView.setAdapter(userAdapter);

                            //Log.d("ServerCommunication", userNames.toString());
                        } /*catch (JsonGenerationException e) {
                            e.printStackTrace();
                        } catch (JsonMappingException e) {
                            e.printStackTrace();
                        } */catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "It seems there's an error connecting to the server!", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        RequestQueueSingleton.getInstance(getApplicationContext()).addToRequestQueue(sr);
    }

    private void confirmWatchlistButtonClicked() {
        StringRequest sr;
        for (User user : selectedUserList) {
            final User finalUser = user;
            sr = new StringRequest(StringRequest.Method.PUT,
                    ImportantURLs.BASE_URL + "users/aw/" + user.getId(),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("ServerCommunication", response);
                            try {
                                JSONObject obj = new JSONObject(response);
                                if (obj.getBoolean("user_added"))
                                    Log.d("ServerCommunication", "User successfully added");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(EditWatchlistActivity.this, "It seems there was an error communicating with the server!", Toast.LENGTH_SHORT).show();
                    Log.d("ServerCommunication", error.getMessage());
                }
            }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("watcherId", Long.toString(SharedPrefManager.getInstance(getApplicationContext()).getUser().getId()));
                    params.put("watchedId", Long.toString(finalUser.getId()));
                    return params;
                }
            };

            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(sr);
        }
        startActivity(new Intent(getApplicationContext(), RecentChirpsActivity.class));

    }

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, EditWatchlistActivity.class);
        return intent;
    }

    private class userSelectorHolder extends RecyclerView.ViewHolder {
        private CheckBox userBox;

        private User user;

        public userSelectorHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.watchlist_item, parent, false));
            userBox = itemView.findViewById(R.id.user_checkbox);
        }

        public void bind(final User inputName) {
            user = inputName;
            userBox.setText(user.getHandle());
            userBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (userBox.isChecked()) {
                        selectedUserList.add(inputName);
                    }
                    else {
                        selectedUserList.remove(inputName);
                    }
                }
            });
        }
    }

    private class UserSelectorAdapter extends RecyclerView.Adapter<userSelectorHolder> {
        private List<User> users;

        public UserSelectorAdapter(List<User> listOfUsers) { users = listOfUsers; }

        @NonNull
        @Override
        public userSelectorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new userSelectorHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull userSelectorHolder holder, int position) {
            holder.bind(users.get(position));
        }

        @Override
        public int getItemCount() {
            return users.size();
        }
    }
}
