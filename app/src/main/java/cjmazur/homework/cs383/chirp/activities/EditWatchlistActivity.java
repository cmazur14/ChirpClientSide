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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cjmazur.homework.cs383.chirp.R;
import cjmazur.homework.cs383.chirp.models.User;
import cjmazur.homework.cs383.chirp.volley.ImportantURLs;
import cjmazur.homework.cs383.chirp.volley.RequestQueueSingleton;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class EditWatchlistActivity extends AppCompatActivity {

    private Button confirmWatchlistButton;
    private RecyclerView userRecyclerView;
    private ArrayList<User> allUserList;
    private ArrayList<String> selectedUserList;
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

        updateUserList();

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
                            List<String> usersAsList = new ArrayList<>();

                            JSONArray objs = new JSONArray(response);
                            JSONObject object;

                            int numObjects = objs.length();
                            for (int i = 0; i < numObjects; i++) {
                                //usersAsList.add(mapper.readValue(objs.getString(i), User.class));
                                object = objs.getJSONObject(i);
                                usersAsList.add(object.getString("handle"));
                            }

                            for (String s : usersAsList) {
                                Log.d("JSONStuff", s);
                            }

                            userAdapter = new UserSelectorAdapter(usersAsList);
                            userRecyclerView.setAdapter(userAdapter);

                            //Log.d("ServerCommunication", userNames.toString());
                        } /*catch (JsonGenerationException e) {
                            e.printStackTrace();
                        } catch (JsonMappingException e) {
                            e.printStackTrace();
                        } */catch (JSONException e) {
                            e.printStackTrace();
                        } /*catch (IOException e) {
                            e.printStackTrace();
                        } */
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

    private void updateUserList() {
        //TODO
    }

    private void confirmWatchlistButtonClicked() {
        //TODO
    }

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, EditWatchlistActivity.class);
        return intent;
    }

    private class userSelectorHolder extends RecyclerView.ViewHolder {
        private CheckBox userBox;

        private String userName;

        public userSelectorHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.watchlist_item, parent, false));
            userBox = itemView.findViewById(R.id.user_checkbox);
        }

        public void bind(final String inputName) {
            userName = inputName;
            userBox.setText(userName);
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
        private List<String> userNames;

        public UserSelectorAdapter(List<String> listOfUsers) { userNames = listOfUsers; }

        @NonNull
        @Override
        public userSelectorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new userSelectorHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull userSelectorHolder holder, int position) {
            holder.bind(userNames.get(position));
        }

        @Override
        public int getItemCount() {
            return userNames.size();
        }
    }
}
