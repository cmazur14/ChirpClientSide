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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cjmazur.homework.cs383.chirp.models.Chirp;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import cjmazur.homework.cs383.chirp.R;

public class RecentChirpsActivity extends AppCompatActivity {

    private Button createChirpsButton;
    private Button editWatchlistButton;
    private RecyclerView chirpsView;
    private ArrayList<Chirp> chirps;
    private ChirpAdapter chirpsAdapter;

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
                Intent intent = EditWatchlistActivity.newIntent(view.getContext());
                startActivity(intent);
            }
        });
        chirpsView = findViewById(R.id.chirps_recycler_view);
        chirpsView.setLayoutManager(new LinearLayoutManager(this));
        chirps = new ArrayList<>();

        updateChirpsList();
    }

    private void updateChirpsList() {
        //TODO actually create things from the server
        for (int i = 0; i < 25; i++) {
            chirps.add(new Chirp(getString(R.string.chirp_content_placeholder),
                    getString(R.string.chirp_date_placeholder),
                    getString(R.string.chirp_author_placeholder),
                    null));
        }

        chirpsAdapter = new ChirpAdapter(chirps);
        chirpsView.setAdapter(chirpsAdapter);
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
            author.setText(chirp.getAuthor());
            date.setText(chirp.getDate());
            if (chirp.getMessage() != null) {
                content.setText(chirp.getMessage());
            }
            else {
                content.setVisibility(View.GONE);
            }
            if (chirp.getImage() != null) {
                picture.setVisibility(View.VISIBLE);
            }
            else {
                picture.setVisibility(View.GONE);
            }
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
