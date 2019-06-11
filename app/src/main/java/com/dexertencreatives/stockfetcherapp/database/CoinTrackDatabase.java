package com.dexertencreatives.stockfetcherapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.dexertencreatives.stockandforexfetcher.model.Coin;

/**
 * Created by USER on 5/12/2019.
 */

@Database(entities = {Coin.class}, version = 1, exportSchema = false)
public abstract class CoinTrackDatabase extends RoomDatabase {
    public abstract CoinTrackDao mCoinTrackDao();
}
