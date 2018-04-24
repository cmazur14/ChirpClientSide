package cjmazur.homework.cs383.chirp.models;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by CJ on 4/14/2018.
 */

public class WatchlistHandler {

    private WatchlistHandler mInstance;

    private ArrayList<UUID> mWatchlist;

    private WatchlistHandler() {
        mWatchlist = getWatchlist();
    }

    public WatchlistHandler getInstance() {
        if (mInstance == null) mInstance = new WatchlistHandler();
        return mInstance;
    }

    public void setWatchlist(ArrayList<UUID> watchlist) {
        mWatchlist = watchlist;
    }

    public ArrayList<UUID> getWatchlist() {
        if (mWatchlist == null) mWatchlist = new ArrayList<>();
        return mWatchlist;
    }

}
