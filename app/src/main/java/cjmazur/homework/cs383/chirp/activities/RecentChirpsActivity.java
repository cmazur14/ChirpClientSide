package cjmazur.homework.cs383.chirp.activities;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cjmazur.homework.cs383.chirp.R;

public class RecentChirpsActivity extends AppCompatActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, RecentChirpsActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_chirps);
    }

    private class chirpHolder extends RecyclerView.ViewHolder {
        private TextView author;
        private TextView date;
        private TextView content;
        private ImageView picture;

        public chirpHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.chirp_list_item, parent, false));
            author = itemView.findViewById(R.id.chirp_author);
            date = itemView.findViewById(R.id.chirp_date_created);
            content = itemView.findViewById(R.id.chirp_content);
            picture = itemView.findViewById(R.id.chirp_image);
        }

        public void bind(final String auth, final String da, @Nullable final String cont, @Nullable Image img) {
            author.setText(auth);
            date.setText(da);
            if (cont != null)
                content.setText(cont);
            if (img != null);
                //TODO
        }
    }

    private class ChirpAdapter extends RecyclerView.Adapter<chirpHolder> {
        private ArrayList<String> authors;
        private ArrayList<String> dates;
        private ArrayList<String> contents;
        private ArrayList<Image> pics;

        public ChirpAdapter(ArrayList<String> auths, ArrayList<String> das, ArrayList<String> conts, ArrayList<Image> imgs) {
            authors = auths;
            dates = das;
            contents = conts;
            pics= imgs;
        }

        @NonNull
        @Override
        public chirpHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getParent());
            return new chirpHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull chirpHolder holder, int position) {
            holder.bind(authors.get(position), dates.get(position), contents.get(position), pics.get(position));
        }

        @Override
        public int getItemCount() {
            return authors.size();
        }
    }
}
