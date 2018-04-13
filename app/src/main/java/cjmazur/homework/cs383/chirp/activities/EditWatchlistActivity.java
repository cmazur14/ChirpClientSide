package cjmazur.homework.cs383.chirp.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cjmazur.homework.cs383.chirp.R;

public class EditWatchlistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_watchlist);
    }

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, EditWatchlistActivity.class);
        return intent;
    }
}
