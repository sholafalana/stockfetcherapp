package com.dexertencreatives.stockfetcherapp.network;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dexertencreatives.stockandforexfetcher.Utils.App;

import javax.inject.Inject;

/**
 * Created by USER on 5/12/2019.
 */

public class CoinTrackIntentService extends IntentService {

    private static final String COIN_SERVICE_TAG = CoinTrackIntentService.class.getSimpleName();
    @Inject
    CoinTrackRemoteDataSource mCoinTrackRemoteDataSource;

    public CoinTrackIntentService() {
        super(COIN_SERVICE_TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ((App) getApplication()).getAppComponent().inject(this);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(COIN_SERVICE_TAG, "Service OnHandleIntent Called.");
        mCoinTrackRemoteDataSource.getCoinRealTimeUpdates();
    }
}
