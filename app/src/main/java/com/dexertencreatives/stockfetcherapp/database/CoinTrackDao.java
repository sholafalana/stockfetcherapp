package com.dexertencreatives.stockfetcherapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.dexertencreatives.stockandforexfetcher.model.Coin;

import java.util.List;

/**
 * Created by USER on 5/12/2019.
 */

@Dao
public interface CoinTrackDao {

    @Query("SELECT * FROM coins")
    LiveData<List<Coin>> getAll();

    @Query("SELECT * FROM coins WHERE shortName = :shortName")
    Coin findByShortName(String shortName);

    @Insert
    void insertAll(List<Coin> coins);

    @Update
    void updateCoin(Coin coin);

    @Query("Delete FROM coins")
    void deleteAll();

}
